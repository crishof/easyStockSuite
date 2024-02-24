import { Component, OnInit, inject } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { CommonModule } from '@angular/common';
import { IProduct } from '../../model/product.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css',
})
export class ProductsComponent implements OnInit {
  productList: IProduct[] = [];
  private _productService = inject(ProductService);
  private _router = inject(Router);

  ngOnInit(): void {
    this._productService.getProducts().subscribe((data: IProduct[]) => {
      this.productList = data;
    });
  }

  navegate(id: string): void {
    this._router.navigate(['/products', id]);
  }
}
