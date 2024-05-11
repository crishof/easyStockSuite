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

  productList: IProduct[] = [];
  invoiceItems: IInvoiceItem[] = [];

  selectedProduct: IProduct | null = null;
  isFormSubmitted: boolean = false;
  searchTerm: string = '';

  invoiceItemsFormArray: FormArray<FormGroup> = new FormArray<FormGroup>([]);

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
      discount: '',
      totalPrice: '',
    });
  }

  onInvoiceItemChange(updatedItems: IInvoiceItem[]): void {
    this.invoiceItems = updatedItems;
  }

  saveInvoice() {
    const formData = this.invoiceForm.value;
    formData.invoiceItem = this.invoiceItems;

    console.log(formData);
  }

  selectProduct(product: IProduct): void {
    const invoiceItem: IInvoiceItem = {
      id: product.id,
      brandName: product.brandName,
      model: product.model,
      description: product.description,
      price: product.priceResponse.purchasePrice,
      taxRate: product.priceResponse.taxRate,
      discount: product.priceResponse.discount,
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
