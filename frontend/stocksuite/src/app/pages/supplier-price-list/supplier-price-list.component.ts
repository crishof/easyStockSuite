import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { SupplierPriceListService } from '../../services/supplier-price-list.service';
import { IProduct } from '../../model/product.model';
import { FormsModule, NgModel } from '@angular/forms';
import { ISupplierProduct } from '../../model/supplierProduct';
import { SupplierService } from '../../services/supplier.service';
import { BrandService } from '../../services/brand.service';

@Component({
  selector: 'app-supplier-price-list',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, FormsModule],
  templateUrl: './supplier-price-list.component.html',
  styleUrl: './supplier-price-list.component.css',
})
export class SupplierPriceListComponent implements OnInit {
  private _supplierPriceList = inject(SupplierPriceListService);
  private _supplierService = inject(SupplierService);
  private _brandService = inject(BrandService);

  productList: ISupplierProduct[] = [];
  brand: string = '';
  supplierId: string = '';
  searchTerm: string = '';
  selectedProduct: ISupplierProduct | null = null;

  selectedSupplierId: string = '';
  selectedBrand: string = '';
  suppliers: any[] = [];
  brands: string[] = [];

  ngOnInit(): void {
    this.loadSuppliers();
    this.loadBrands();
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
    this.loadBrands();
  }

  loadBrands() {
    if (this.selectedSupplierId) {
      this._supplierPriceList
        .getBrandsBySupplierId(this.selectedSupplierId)
        .subscribe(
          (brands: any[]) => {
            this.brands = brands;
          },
          (error) => {
            console.log('Error loading brand list', error);
          }
        );
    } else {
      this._supplierPriceList.getAllBrands().subscribe(
        (brands: any[]) => {
          this.brands = brands;
        },
        (error) => {
          console.log('Error loading brand list', error);
        }
      );
    }
  }

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
