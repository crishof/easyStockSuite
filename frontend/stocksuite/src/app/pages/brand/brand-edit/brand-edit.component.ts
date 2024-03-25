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

  brandForm!: FormGroup;
  _brandService = inject(BrandService);
  route = inject(ActivatedRoute)
  router = inject(Router);

  brandId: string;
  imageFile : File | null = null;


  private brandUpdatedSubject = new Subject<IBrand>();

  constructor(private formBuilder: FormBuilder) {
    this.brandForm = formBuilder.group({
      brandName: ['', [Validators.required]],
      image: [null],
    });
    this.brandId = this.route.snapshot.paramMap.get('id')?? '';
  }

  updateBrand(event: Event) {
    event.preventDefault();
  
    if (this.brandForm.invalid) {
      // Aquí puedes manejar la visualización de mensajes de validación al usuario
      return;
    }
  
    // Crear un nuevo objeto FormData
    const formData = new FormData();
    
    // Agregar el nombre de la marca al FormData
    formData.append('name', this.brandForm.get('brandName')?.value);
  
    // Verificar si this.imageFile no es nulo y agregarlo al FormData si existe
    if (this.imageFile) {
      formData.append('file', this.imageFile, this.imageFile.name);
    }
  
    this._brandService.updateBrand(this.brandId, formData).subscribe(
      (response) => {
        console.log('Brand updated successfully', response);
        // Redirigir al usuario a la página de detalles de la marca actualizada u otras acciones
        this.router.navigate(['/brands', this.brandId]);
      },
      (error) => {
        console.error('Error updating Brand', error);
        // Aquí puedes manejar el error, mostrar un mensaje al usuario, etc.
      }
    );
  }

  // Método para manejar la selección de archivo de imagen
  onFileSelected(event: any) {
    if (event.target.files && event.target.files.length > 0) {
      this.imageFile = event.target.files[0];
    }
  }

  // Método para verificar si hay errores en un campo específico del formulario
  /*
  hasErrors(field: string, typeError: string): boolean {
    return this.brandForm.get(field)?.hasError(typeError) && this.brandForm.get(field)?.touched;
  }
*/

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

  /*
  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0] as File;
  }
*/
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

  ngOnInit(): void {
    this.brandForm = this.formBuilder.group({
      brandName: ['', Validators.required],
      image: [null],
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
