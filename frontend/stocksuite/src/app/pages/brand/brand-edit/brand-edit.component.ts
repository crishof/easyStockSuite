import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule, NgClass } from '@angular/common';
import { IBrand } from '../../../model/brand.model';
import { Injectable, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { BrandService } from '../../../services/brand.service';
import { Subject } from 'rxjs';
import { DomSanitizer } from '@angular/platform-browser';

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
    });
  }

  enviar(event: Event) {
    event.preventDefault();

    const updatedBrand: IBrand = {
      id: this.brand?.id || '',
      name: this.brandForm.get('brandName')?.value || '',

      // Agregar otros campos de edicion
    };

    const formData = new FormData();
    formData.append('name', updatedBrand.name);

    const logo: File | null = this.brandForm.get('logo')?.value || null;

    if (logo) {
      formData.append('logo', logo, logo.name);
    }

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
