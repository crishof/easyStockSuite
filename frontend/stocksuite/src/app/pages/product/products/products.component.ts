import { Component, OnInit, inject } from '@angular/core';
import { ProductService } from '../../../services/product.service';
import { CommonModule } from '@angular/common';
import { IProduct } from '../../../model/product.model';
import { Router } from '@angular/router';
import { BrandService } from '../../../services/brand.service';
import { Observable } from 'rxjs';
import { IBrand } from '../../../model/brand.model';
import { map, tap } from 'rxjs/operators';

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
  private _brandService = inject(BrandService);
  private _router = inject(Router);

  ngOnInit(): void {
    this._productService.getProducts().subscribe((data: IProduct[]) => {
      this.productList = data;
    });
  }

  brandName: string = '';

  getBrandName(brandId: string): Observable<string> {
    return this._brandService.getBrand(brandId).pipe(
      map((brand) => brand.name)
    );
  }

  navegate(id: string): void {
    this._router.navigate(['/products', id]);
  }
}
