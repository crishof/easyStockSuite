import { CommonModule, NgClass } from '@angular/common';
import { Component, Input, OnInit, inject } from '@angular/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { ProductSearchComponent } from '../../product/product-search/product-search.component';
import { ProductListComponent } from '../../product/product-list/product-list.component';
import { IProduct } from '../../../model/product.model';
import { Subscription } from 'rxjs';
import { ProductService } from '../../../services/product.service';
import { InvoiceItemsComponent } from './invoice-items/invoice-items.component';
import { ISupplier } from '../../../model/supplier.model';
import { SupplierService } from '../../../services/supplier.service';
import { ISupplierInvoice } from '../../../model/supplier-invoice.model';
import { IInvoiceItem } from '../../../model/invoice-item.model';
import { SupplierInvoiceService } from '../../../services/supplier-invoice.service';
import { error } from 'console';

@Component({
  selector: 'app-supplier-invoice',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatNativeDateModule,
    ProductSearchComponent,
    ProductListComponent,
    InvoiceItemsComponent,
    NgClass,
  ],
  templateUrl: './supplier-invoice.component.html',
  styleUrl: './supplier-invoice.component.css',
})
export class SupplierInvoiceComponent implements OnInit {
  private subscription?: Subscription;
  private _productService = inject(ProductService);
  private _supplierService = inject(SupplierService);
  dateControl = new FormControl(new Date());

  @Input() invoice: ISupplierInvoice | undefined;

  invoiceForm!: FormGroup;
  formBuilder = inject(FormBuilder);

  selectedComponent: string = 'header';

  suppliers: any[] = [];
  supplierList: ISupplier[] = [];
  selectedSupplierId: string = '';

  _supplierInvoice?: ISupplierInvoice;
  _supplierInvoiceService = inject(SupplierInvoiceService);

  productList: IProduct[] = [];
  invoiceItems: IInvoiceItem[] = [];

  selectedProduct: IProduct | null = null;
  isFormSubmitted: boolean = false;
  searchTerm: string = '';

  totalInvoiceUnits: number = 0;

  invoiceItemsFormArray: FormArray<FormGroup> = new FormArray<FormGroup>([]);

  iva21: number = 0.21;
  iva105: number = 0.105;
  iva27: number = 0.27;
  iva0: number = 0;

  ngOnInit(): void {
    this.loadSuppliers();
    this.dateControl.setValue(new Date());

    this.initForm();
  }

  initForm(): void {
    this.invoiceForm = this.formBuilder.group({
      supplierId: '',
      invoiceType: '',
      invoiceDate: new Date(),
      receptionDate: new Date(),
      dueDate: new Date(),
      savedDate: new Date(),
      location: '',
      packingListPrefix: '',
      packingListNumber: '',
      invoicePrefix: '',
      invoiceNumber: '',
      saveStocks: true,
      taxSave: true,
      fixedAsset: false,
      askForPriceUpdate: false,
      observations: '',

      subtotal1: '',
      discount: '',
      interest: '',
      subtotal2: '',

      netoIva21: '',
      netoIva105: '',
      netoIva27: '',
      netoIva0: '',

      iva21: '',
      iva105: '',
      iva27: '',
      impuestoInterno: '',

      rounding: '',
      totalPrice: '',
      withholdingIva: '',
      withholdingSuss: '',
      withholdingIibb: '',
      withholdingIncome: '',
      stateTax: '',
      localTax: '',
    });
  }

  onInvoiceItemChange(updatedItems: IInvoiceItem[]): void {
    this.invoiceItems = updatedItems;
    this.getTotalInvoiceUnits();
  }

  getTotalInvoiceUnits(): void {
    this.totalInvoiceUnits = Number(
      this.invoiceItems.reduce((total, item) => total + item.quantity, 0)
    );
  }

  getSubtotal1(): number {
    const subtotal1 = this.invoiceItems.reduce((total, item) => {
      const discount = item.discountRate ?? 0;
      const discountedPrice = item.price * ((100 - discount) / 100);
      return total + discountedPrice * item.quantity;
    }, 0);

    this.invoiceForm.patchValue({
      subtotal1: subtotal1,
    });

    return subtotal1;
  }

  getSubtotal2(): number {
    const subtotal1 = this.invoiceForm.get('subtotal1')?.value;
    const discount = this.invoiceForm.get('discount')?.value;
    const interest = this.invoiceForm.get('interest')?.value;

    let subtotal2 = subtotal1;

    if (discount !== 0) {
      const discountPercentage = Math.min(Math.max(discount, 0), 100) / 100;
      subtotal2 *= 1 - discountPercentage;
    }

    if (interest !== 0) {
      const interestPercentage = Math.min(Math.max(interest, 0), 100) / 100;
      subtotal2 *= 1 + interestPercentage;
    }

    console.log('Subt2: ' + subtotal2);
    this.invoiceForm.patchValue({
      subtotal2: subtotal2,
    });

    console.log('Subt2FORM: ' + this.invoiceForm.get('subtotal2')?.value);

    return subtotal2;
  }

  getNetoIva(iva: number): number {
    return this.calculateNetoIva(iva);
  }

  calculateNetoIva(iva: number): number {
    const discount = parseFloat(this.invoiceForm.get('discount')?.value || '0');
    const interest = parseFloat(this.invoiceForm.get('interest')?.value || '0');
    
    const total = this.invoiceItems.reduce((acc, item) => {
      if (item.taxRate == iva) {
        const ItemDiscount = item.discountRate ?? 0;
        return acc + (item.price * ((100 - item.discountRate) / 100));
      } else {
        return acc;
      }
    }, 0);

    const netoIva = total * ((100 - discount) / 100) * ((100 + interest) / 100);

    switch (iva) {
      case this.iva21:
        this.invoiceForm.patchValue({
          netoIva21: netoIva,
        });
        break;
      case this.iva105:
        this.invoiceForm.patchValue({
          netoIva105: netoIva,
        });
        break;
      case this.iva27:
        this.invoiceForm.patchValue({
          netoIva27: netoIva,
        });
        break;
      case this.iva0:
        this.invoiceForm.patchValue({
          netoIva0: netoIva,
        });
        break;
    }

    return netoIva;
  }

  getIva(iva: number): number {
    return this.calculateIva(iva);
  }

  calculateIva(iva: number): number {
    const netoIva: number = this.getNetoIva(iva);
    const calculatedIva: number =
      (netoIva * ((100 + iva) / 100) - netoIva) * 100;

    switch (iva) {
      case this.iva21:
        this.invoiceForm.patchValue({
          iva21: calculatedIva,
        });
        break;
      case this.iva105:
        this.invoiceForm.patchValue({
          iva105: calculatedIva,
        });
        break;
      case this.iva27:
        this.invoiceForm.patchValue({
          iva27: calculatedIva,
        });
        break;
    }

    return calculatedIva;
  }

  getImpuestoInterno() {}

  getInvoiceTotal() {
    const total: number =
      parseFloat(this.invoiceForm.get('subtotal2')?.value || '0') +
      parseFloat(this.invoiceForm.get('iva21')?.value || '0') +
      parseFloat(this.invoiceForm.get('iva105')?.value || '0') +
      parseFloat(this.invoiceForm.get('iva27')?.value || '0') +
      parseFloat(this.invoiceForm.get('withholdingIva')?.value || '0') +
      parseFloat(this.invoiceForm.get('withholdingSuss')?.value || '0') +
      parseFloat(this.invoiceForm.get('withholdingIibb')?.value || '0') +
      parseFloat(this.invoiceForm.get('withholdingIncome')?.value || '0') +
      parseFloat(this.invoiceForm.get('stateTax')?.value || '0') +
      parseFloat(this.invoiceForm.get('localTax')?.value || '0') +
      parseFloat(this.invoiceForm.get('rounding')?.value || '0');

    this.invoiceForm.patchValue({
      totalPrice: total,
    });
    return total;
  }

  saveInvoice() {
    const formData = this.invoiceForm.value;
    formData.invoiceItemsRequest = this.invoiceItems;

    this._supplierInvoiceService.saveInvoice(formData).subscribe(
      (response) => {
        console.log(response.message);
      },
      (error) => {
        console.error(error.message);
      }
    );
  }

  selectProduct(product: IProduct): void {
    const invoiceItem: IInvoiceItem = {
      id: product.id,
      brandName: product.brandName,
      model: product.model,
      description: product.description,
      price: product.priceResponse.purchasePrice,
      taxRate: product.priceResponse.taxRate,
      discountRate: product.priceResponse.discount,
      quantity: 1,
    };
    this.invoiceItems.push(invoiceItem);
  }

  loadSuppliers() {
    this._supplierService.getSuppliers().subscribe(
      (suppliers: any[]) => {
        this.suppliers = suppliers;
      },
      (error) => {
        console.log('Error loading supplier list', error);
      }
    );
  }

  onSupplierChange(supplierId: any) {
    this.selectedSupplierId = supplierId;
  }

  handleSearch(searchTerm: string): void {
    this.isFormSubmitted = true;
    if (searchTerm.length >= 3) {
      this.searchProducts(searchTerm);
    } else {
      this.productList = [];
    }
  }

  handleSearchWithStock(searchTerm: string): void {
    this.isFormSubmitted = true;
    if (searchTerm.length >= 3) {
      this.searchProductsWithStock(searchTerm);
    } else {
      this.productList = [];
    }
  }

  searchProducts(searchTerm: string): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.subscription = this._productService
      .getAllByFilter(searchTerm)
      .subscribe({
        next: (data: IProduct[]) => {
          this.productList = data;
        },
        error: (err) => {
          console.error('Failed to load products', err);
        },
      });
  }

  searchProductsWithStock(searchTerm: string): void {
    console.log('searchTerm' + searchTerm);
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.subscription = this._productService
      .getAllByFilterAndStock(searchTerm)
      .subscribe({
        next: (data: IProduct[]) => {
          this.productList = data;
        },
        error: (err) => {
          console.error('Failed to load products', err);
        },
      });
  }
}
