import { Component, OnInit, inject } from '@angular/core';
import { IBrand } from '../../../model/brand.model';
import { BrandService } from '../../../services/brand.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { BrandEditComponent } from '../brand-edit/brand-edit.component';
import { Subject } from 'rxjs';
import { ModalDialogService } from '../../../services/modal-dialog.service';
import { DomSanitizer } from '@angular/platform-browser';
import { ProductService } from '../../../services/product.service';

@Component({
  selector: 'app-brand-details',
  standalone: true,
  templateUrl: './brand-details.component.html',
  styleUrl: './brand-details.component.css',
  imports: [CommonModule, BrandEditComponent],
})
export class BrandDetailsComponent implements OnInit {
  loading: boolean = true;
  public brand?: IBrand;
  editingMode: boolean = false;

  private _route = inject(ActivatedRoute);
  private _brandService = inject(BrandService);
  private _productService = inject(ProductService);
  private _router = inject(Router);
  private _confirmDialogService = inject(ModalDialogService);

  private _sanitizer = inject(DomSanitizer);

  successMessage: string = '';
  errorMessage: string = '';

  productsQuantity: number = 0;

  private brandUpdatedSubject: Subject<IBrand> = new Subject<IBrand>();

  ngOnInit(): void {
    this._route.params.subscribe((params) => {
      this._brandService.getBrand(params['id']).subscribe((data: IBrand) => {
        this.brand = data;
        this.loading = false;
        if (this.brand?.id) {
          this.getBrandProductsQuantity();
        }
      });
    });

    this.brandUpdatedSubject.subscribe((updatedBrand) => {
      if (updatedBrand) {
        this.brand = updatedBrand;
        this.getBrandProductsQuantity();
      }
    });
  }

  getImageUrl(image: any): any {
    const base64Image = image.content;
    return this._sanitizer.bypassSecurityTrustResourceUrl(
      'data:image/jpeg;base64,' + base64Image
    );
  }

  startEditing(): void {
    this.editingMode = true;
  }

  saveChanges(updatedBrand: IBrand): void {
    this.editingMode = false;
    this.successMessage = 'Brand ' + updatedBrand.name + 'saved successfully';
    if (this.brandUpdatedSubject) {
      this.brandUpdatedSubject.next(updatedBrand);
    }
    this.brandUpdatedSubject.next(updatedBrand);
  }

  onSuccessMessageHandler(message: string) {
    this.successMessage = message;
  }

  cancelEditing(): void {
    this.editingMode = false;
  }

  goToList(): void {
    this._router.navigate(['/brand']);
  }

  confirmDelete(id: string): void {
    this._confirmDialogService.openConfirmDialog().subscribe((confirmed) => {
      if (confirmed) {
        this.deleteBrand(id);
      }
    });
  }

  deleteBrand(id: string): void {
    this._brandService.deleteBrand(id).subscribe(
      (response: any) => {
        this.successMessage = response;
        this.errorMessage = '';
      },
      (error) => {
        this.errorMessage = error.error;
        this.successMessage = '';
      }
    );
  }

  getBrandProductsQuantity(): void {
    const brandId = this.brand?.id ?? '';
    this._productService.getBrandProductsQuantity(brandId).subscribe(
      (quantity: number) => {
        this.productsQuantity = quantity;
      },
      (error) => {
        console.error('Error getting products quantity:', error);
      }
    );
  }
}
