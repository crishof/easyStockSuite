import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ProductSearchComponent } from '../../product/product-search/product-search.component';
import { ProductListComponent } from '../../product/product-list/product-list.component';
import { IProduct } from '../../../model/product.model';
import { Subscription } from 'rxjs';
import { ProductService } from '../../../services/product.service';
import { InvoiceItemsComponent } from './invoice-items/invoice-items.component';

@Component({
  selector: 'app-supplier-invoice',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatNativeDateModule,
    ProductSearchComponent,
    ProductListComponent,
    InvoiceItemsComponent,
  ],
  templateUrl: './supplier-invoice.component.html',
  styleUrl: './supplier-invoice.component.css',
})
export class SupplierInvoiceComponent implements OnInit {
  private subscription?: Subscription;
  private _productService = inject(ProductService);
  dateControl = new FormControl(new Date());

  selectedComponent: string = 'header';

  invoiceItems: IProduct[] = [];

  searchTerm: string = '';
  isFormSubmitted: boolean = false;
  productList: IProduct[] = [];
  selectedProduct: IProduct | null = null;

  remitoForm: FormGroup = new FormGroup({
    remitoPrefix: new FormControl(''),
    remitoNumber: new FormControl(''),
  });
  comprobanteForm: FormGroup = new FormGroup({
    comprobantePrefix: new FormControl(''),
    comprobanteNumber: new FormControl(''),
  });

  ngOnInit(): void {
    this.dateControl.setValue(new Date());
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
    console.log('searchTerm' + searchTerm);
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

  selectProduct(product: IProduct): void {
    this.selectedProduct = product;
    this.invoiceItems.push(product);
  }
}
