import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { SupplierPriceListService } from '../../services/supplier-price-list.service';
import { FormsModule } from '@angular/forms';
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

  productList: ISupplierProduct[] = [];
  brand: string = '';
  supplierId: string = '';
  searchTerm: string = '';
  selectedProduct: ISupplierProduct | null = null;

  selectedSupplierId: string = '';
  selectedBrand: string = '';
  suppliers: any[] = [];
  brands: string[] = [];

  selectedProducts: ISupplierProduct[] = [];
  lastSelectedIndex: number | null = null;

  selectAllChecked: boolean = false;

  selectAllProducts(event: any) {
    const isChecked = event.target.checked;
    if (isChecked) {
      this.selectedProducts = [...this.productList];
    } else {
      this.selectedProducts = [];
    }
  }

  toggleSelection(event: any, product: ISupplierProduct) {
    if (event.target.checked) {
      // Agregar el producto a la lista de productos seleccionados
      this.selectedProducts.push(product);
    } else {
      // Eliminar el producto de la lista de productos seleccionados
      const index = this.selectedProducts.indexOf(product);
      if (index !== -1) {
        this.selectedProducts.splice(index, 1);
      }
    }
  }

  isSelected(product: any): boolean {
    return this.selectedProducts.some(
      (selectedProduct) => selectedProduct.id === product.id
    );
  }

  importSelectedProducts(): void {
    this._supplierPriceList.importProducts(this.selectedProducts).subscribe(
      () => {
        console.log('productos importados con exito ');
      },
      (error) => {
        console.log('Error al importar productos', error);
      }
    );
  }

  ngOnInit(): void {
    this.loadSuppliers();
    this.loadBrands();

    console.log(this.brands);
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
      .getAllByFilter(
        this.selectedSupplierId,
        this.selectedBrand,
        this.searchTerm
      )
      .subscribe((data: ISupplierProduct[]) => {
        this.productList = data;
        console.log(this.productList);
      });
  }
}
