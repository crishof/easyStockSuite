import { CommonModule, NgClass } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { SupplierPriceListService } from '../../services/supplier-price-list.service';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ISupplierProduct } from '../../model/supplierProduct';
import { SupplierService } from '../../services/supplier.service';

@Component({
  selector: 'app-supplier-price-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    RouterLink,
    FormsModule,
    ReactiveFormsModule,
    NgClass,
  ],
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

  errorMessage: string = '';
  successMessage: string = '';
  warningMessage: string = '';

  selectedFile: File | null = null;
  updateExistingProducts: boolean = false;

  fileForm!: FormGroup;
  formBuilder = inject(FormBuilder);

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0] as File;
  }

  uploadFile(event: Event) {
    event.preventDefault(); // Evita que el formulario se envíe automáticamente

    if (this.fileForm.valid && this.selectedFile) {
      // Verifica si el formulario es válido
      const supplierId = this.fileForm.get('supplierId')?.value;
      const updateExistingProducts = this.fileForm.get(
        'updateExistingProducts'
      )?.value;

      if (
        this.selectedFile != null &&
        supplierId != null &&
        updateExistingProducts != null
      ) {
        this._supplierPriceList
          .uploadFile(this.selectedFile, supplierId, updateExistingProducts)
          .subscribe(
            (response: any) => {
              this.successMessage = response.message;

              // Manejar la respuesta del backend si es necesario
            },
            (error: any) => {
              this.errorMessage = error;
              // Manejar los errores si es necesario
            }
          );
      } else {
        console.error('Formulario inválido');
        // Puedes mostrar un mensaje de error o tomar otra acción si el formulario no es válido
      }
    }
  }

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
    if (this.selectedProducts.length == 0) {
      this.warningMessage = 'No products selected';
    } else {
      this._supplierPriceList.importProducts(this.selectedProducts).subscribe(
        (response: any) => {
          console.log(response.message);
          this.successMessage = response.message;
          this.selectedProducts = [];
        },
        (error) => {
          console.log('Error saving products', error);
          this.errorMessage = error;
        }
      );
    }
  }

  ngOnInit(): void {
    this.loadSuppliers();
    this.loadBrands();

    this.fileForm = this.formBuilder.group({
      supplierId: ['', Validators.required], // Inicializa los campos del formulario según tus necesidades
      updateExistingProducts: [false],
      file: [''],
    });
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
    this.selectedProducts = [];
    this._supplierPriceList
      .getAllByFilter(
        this.selectedSupplierId,
        this.selectedBrand,
        this.searchTerm
      )
      .subscribe((data: ISupplierProduct[]) => {
        this.productList = data;
      });
  }
}
