import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product-service.service';
import { IProduct } from '../../model/product.model';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-details.component.html',
  styleUrl: './product-details.component.css',
})
export class ProductDetailsComponent implements OnInit {
  loading: boolean = true;
  public product?: IProduct;

  private _route = inject(ActivatedRoute);
  private _productService = inject(ProductService);

  ngOnInit(): void {
    this._route.params.subscribe((params) => {
      this._productService
        .getProduct(params['id'])
        .subscribe((data: IProduct) => {
          this.product = data;
          this.loading = false;
        });
    });
  }
}
