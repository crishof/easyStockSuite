// Importación de módulos necesarios desde Angular
import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { IBrand } from '../../../model/brand.model'; // Importación de la interfaz IBrand para definir el tipo de las marcas
import { BrandService } from '../../../services/brand.service'; // Importación del servicio BrandService para interactuar con las marcas
import { Router } from '@angular/router'; // Importación del servicio Router para la navegación entre páginas

@Component({
  selector: 'app-brand', // Selector del componente, utilizado en el HTML para identificar el lugar donde se mostrará
  standalone: true, // Configuración para indicar que el componente es independiente y no necesita otros módulos
  imports: [CommonModule], // Importación del módulo CommonModule necesario para algunas directivas de Angular
  templateUrl: './brand.component.html', // Ruta al archivo HTML que define la estructura del componente
  styleUrl: './brand.component.css', // Ruta al archivo CSS que define los estilos del componente
})
export class BrandComponent implements OnInit {
  brandList: IBrand[] = []; // Lista que almacenará las marcas obtenidas del servicio

  private _brandService = inject(BrandService); // Inyección del servicio BrandService para obtener las marcas
  private _router = inject(Router); // Inyección del servicio Router para la navegación entre páginas

  ngOnInit(): void {
    // Al inicializar el componente, se llama al servicio para obtener la lista de marcas
    this._brandService.getBrands().subscribe((data: IBrand[]) => {
      this.brandList = data; // Se asigna la lista de marcas obtenida al atributo brandList del componente
    });
  }

  toBrandDetails(id: string): void {
    // Método para navegar a la página de detalles de una marca específica al hacer clic en ella
    this._router.navigate(['/brand', id]); // Se utiliza el servicio Router para realizar la navegación
  }

  toNewBrand(): void {
    this._router.navigate(['/brand/create']);
  }
}
