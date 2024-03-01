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

  brandForm!: FormGroup;
  _brandService = inject(BrandService);

  private brandUpdatedSubject = new Subject<IBrand>();

  constructor(private formBuilder: FormBuilder) {
    this.brandForm = formBuilder.group({
      brandName: ['', [Validators.required]],
      logo: [null],
    });
  }

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

  updateLogo(): void {
    if (this.selectedFile && this.brand?.id) {
      this._brandService
        .updateBrandLogo(this.brand?.id, this.selectedFile)
        .subscribe(
          (response) => {
            console.log('Logo actualizado: ', response);

            this.brandUpdatedSubject.next(response);

            this.onSave.emit(response);
          },
          (error) => {
            console.log('Error al actualizar el logo:', error);
          }
        );
    } else {
      console.log('Seleccione un archivo y proporcione un ID de marca válido.');
    }
  }

  ngOnInit(): void {
    this.brandForm = this.formBuilder.group({
      brandName: ['', Validators.required],
      logo: [null],
    });
  }

  cancelar(): void {
    this.onCancel.emit();
  }

  hasErrors(field: string, typeError: string) {
    return (
      this.brandForm.get(field)?.hasError(typeError) &&
      this.brandForm.get(field)?.touched
    );
  }
}
