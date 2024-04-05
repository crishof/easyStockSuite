import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { SupplierPriceListService } from '../../services/supplier-price-list.service';
import { IProduct } from '../../model/product.model';
import { FormsModule } from '@angular/forms';
import { ISupplierProduct } from '../../model/supplierProduct';

@Component({
  selector: 'app-supplier-price-list',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, FormsModule],
  templateUrl: './supplier-price-list.component.html',
  styleUrl: './supplier-price-list.component.css',
})
export class SupplierPriceListComponent implements OnInit {
  private _supplierPriceList = inject(SupplierPriceListService);

  productList: ISupplierProduct[] = [];
  brand: string = '';
  supplierId: string = '';
  searchTerm: string = '';
  selectedProduct: ISupplierProduct | null = null;

  ngOnInit(): void {}

  searchProducts(): void {
    this._supplierPriceList
      .getAllByFilter(this.supplierId, this.brand, this.searchTerm)
      .subscribe((data: ISupplierProduct[]) => {
        this.productList = data;
      });
  }

  selectProduct(product: ISupplierProduct): void {
    this.selectedProduct = product;
  }
}
