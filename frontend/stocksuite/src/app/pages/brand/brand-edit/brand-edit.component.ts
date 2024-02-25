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
  brandService = inject(BrandService);

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

    this.brandService.updateBrand(updatedBrand.id, updatedBrand).subscribe(
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

  ngOnInit(): void {}

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
