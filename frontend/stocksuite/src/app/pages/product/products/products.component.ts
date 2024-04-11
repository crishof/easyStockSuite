import { Component, OnInit, inject } from '@angular/core';
import { ProductService } from '../../../services/product.service';
import { CommonModule } from '@angular/common';
import { IProduct } from '../../../model/product.model';
import { Router } from '@angular/router';
import { BrandService } from '../../../services/brand.service';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { ProductNavbarComponent } from '../product-navbar/product-navbar.component';
import { ProductDetailsComponent } from '../product-details/product-details.component';
import { ModalDialogService } from '../../../services/modal-dialog.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [
    CommonModule,
    ProductNavbarComponent,
    ProductDetailsComponent,
    FormsModule,
  ],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css',
})
export class ProductsComponent implements OnInit {
  productList: IProduct[] = [];
  private _productService = inject(ProductService);
  private _brandService = inject(BrandService);
  private _router = inject(Router);

  ngOnInit(): void {}

  brandName: string = '';
  searchTerm: string = '';
  isFormSubmitted: boolean = false;

  selectedComponent: string = 'product';
  selectedProduct: IProduct | null = null;

  getBrandName(brandId: string): Observable<string> {
    return this._brandService
      .getBrand(brandId)
      .pipe(map((brand) => brand.name));
  }

  onKeyUp(event: any){
    this.onFormSubmit();
  }

  onFormSubmit(){
    this.isFormSubmitted = true;
    if(this.searchTerm.length >= 3){
      this.searchProducts();
    }else{
      this.productList = [];
    }
  }

  searchProducts(): void {
    this._productService
      .getAllByFilter(this.searchTerm)
      .subscribe((data: IProduct[]) => {
        this.productList = data;
      });
  }

  selectProduct(product: IProduct): void {
    this.selectedProduct = product;
  }

  navegate(id: string): void {
    this._router.navigate(['/products', id]);
  }
}
