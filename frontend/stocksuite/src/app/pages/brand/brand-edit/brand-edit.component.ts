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

  brandForm!: FormGroup;
  brandService = inject(BrandService);

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

    console.log('Id antes de la actualización:', this.brand?.id);
    console.log('Brand antes de la actualización:', this.brand);

    this.brandService.updateBrand(updatedBrand.id, updatedBrand).subscribe(
      (response) => {
        console.log('Brand actualizada:', response);
        this.onSave.emit(response);
      },
      (error) => {
        console.log('Error al actualizar:', error);
      }
    );
  }

  ngOnInit(): void {}

  hasErrors(field: string, typeError: string) {
    return (
      this.brandForm.get(field)?.hasError(typeError) &&
      this.brandForm.get(field)?.touched
    );
  }
}
