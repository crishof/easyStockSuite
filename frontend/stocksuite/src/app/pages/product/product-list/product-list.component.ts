import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { IProduct } from '../../../model/product.model';
import { IStock } from '../../../model/stock.model';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css',
})
export class ProductListComponent {
  selectedProduct: IProduct | null = null;
  loading: boolean = false;
  @Input() productList: IProduct[] = [];

  getTotalStockQuantity(stocks: IStock[]): number {
    this.loading = true;
    const totalQuantity = stocks.reduce(
      (acc, stock) => acc + stock.quantity,
      0
    );
    this.loading = false;
    return totalQuantity;
  }

  selectProduct(product: IProduct): void {
    this.selectedProduct = product;
  }
}
