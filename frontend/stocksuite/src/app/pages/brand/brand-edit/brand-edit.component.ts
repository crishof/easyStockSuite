import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule, NgClass } from '@angular/common';
import { IBrand } from '../../../model/brand.model';
import { inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { BrandService } from '../../../services/brand.service';
import { Subject } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-brand-edit',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NgClass],
  templateUrl: './brand-edit.component.html',
  styleUrl: './brand-edit.component.css',
})
export class BrandEditComponent implements OnInit {
  @Input() brand: IBrand | undefined;
  @Output() onSave = new EventEmitter<IBrand>();
  @Output() onCancel = new EventEmitter<void>();
  @Output() onSuccessMessage = new EventEmitter<String>();

  brandForm!: FormGroup;
  brandId: string;
  file: File | null = null;

  _brandService = inject(BrandService);
  route = inject(ActivatedRoute);
  _router = inject(Router);
  formBuilder = inject(FormBuilder);

  private brandUpdatedSubject = new Subject<IBrand>();

  constructor() {
    this.brandForm = this.formBuilder.group({
      brandName: ['', Validators.required],
      image: [null],
    });
    this.brandId = '';
  }

  ngOnInit(): void {
    this.brandId = this.route.snapshot.paramMap.get('id') ?? '';
  }

  updateBrand(event: Event) {
    event.preventDefault();

    const brandName = this.brandForm.get('brandName')?.value;

    if (!brandName && !this.file) {
      console.error('No changes made. Please upddate at least one field');
      return;
    }
    if (this.brandForm.invalid) {
      return;
    }

    const formData = new FormData();

    if (brandName) {
      formData.append('brandName', brandName);
    }

    if (this.file) {
      formData.append('file', this.file, this.file.name);
    }

    this._brandService.updateBrand(this.brandId, formData).subscribe(
      (response) => {
        this.onSave.emit(response);
        this.onSuccessMessage.emit('Brand updated successfully');
      },
      (error) => {
        console.error('Error updating Brand', error);
      }
    );
  }

  onFileSelected(event: any) {
    if (event.target.files && event.target.files.length > 0) {
      this.file = event.target.files[0];
    }
  }

  hasErrors(field: string, typeError: string): boolean {
    const formControl = this.brandForm.get(field);
    return formControl
      ? formControl.hasError(typeError) && formControl.touched
      : false;
  }

  cancelar(): void {
    this.onCancel.emit();
  }

  /*
  enviar(event: Event) {
    event.preventDefault();

    const updatedBrand: IBrand = {
      id: this.brand?.id || '',
      name: this.brandForm.get('brandName')?.value || '',
    };

    const formData = new FormData();
    formData.append('name', updatedBrand.name);

    this._brandService.updateBrand(updatedBrand.id, formData).subscribe(
      (response) => {
        console.log('Brand actualizada: ', response);

        this.brandUpdatedSubject.next(response);

        this.onSave.emit(response);
      },
      (error) => {
        console.log('Error al actualizar:', error);
      }
    );
  }

  selectedFile: File | undefined;

  
  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0] as File;
  }
  updateImage(): void {
    if (this.selectedFile && this.brand?.id) {
      this._brandService
      .updateBrandImage(this.brand?.id, this.selectedFile)
      .subscribe(
        (response) => {
          console.log('Image updated: ', response);
          
          this.brandUpdatedSubject.next(response);
          
          this.onSave.emit(response);
        },
        (error) => {
          console.log('Error updating image:', error);
        }
        );
      } else {
        console.log('Seleccione un archivo y proporcione un ID de marca válido.');
      }
    }
    
    
    
    
    */
}
