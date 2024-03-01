import { Component, OnInit, inject } from '@angular/core';
import { IBrand } from '../../../model/brand.model';
import { BrandService } from '../../../services/brand.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { BrandEditComponent } from '../brand-edit/brand-edit.component';
import { Subject } from 'rxjs';
import { ConfirmDialogService } from '../../../services/confirm-dialog.service';
import { DomSanitizer } from '@angular/platform-browser';

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
  private _router = inject(Router);
  private _confirmDialogService = inject(ConfirmDialogService);

  private _sanitizer = inject(DomSanitizer);

  successMessage: string = '';

  private brandUpdatedSubject: Subject<IBrand> = new Subject<IBrand>();

  ngOnInit(): void {
    this._route.params.subscribe((params) => {
      this._brandService.getBrand(params['id']).subscribe((data: IBrand) => {
        this.brand = data;
        this.loading = false;
      });
    });

    this.brandUpdatedSubject.subscribe((updatedBrand) => {
      if (updatedBrand) {
        this.brand = updatedBrand;
      }
    });
  }

  getLogoUrl(logo: any): any {
    const base64Image = logo.content; // Suponiendo que `content` contiene la representación Base64 de la imagen
    return this._sanitizer.bypassSecurityTrustResourceUrl(
      'data:image/jpeg;base64,' + base64Image
    );
  }

  startEditing(): void {
    this.editingMode = true;
  }

  saveChanges(updatedBrand: IBrand): void {
    this.editingMode = false;
    if (this.brandUpdatedSubject) {
      this.brandUpdatedSubject.next(updatedBrand);
    }
    this.brandUpdatedSubject.next(updatedBrand);
  }

  cancelEditing(): void {
    this.editingMode = false;
  }

  goToList(): void {
    this._router.navigate(['/brand']); // Navegar a la lista de marcas
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
      (message: any) => {
        this.successMessage = message;
        //this.goToList();
      },
      (error) => {
        console.error('Error deleting Brand', error);
      }
    );
  }
}
