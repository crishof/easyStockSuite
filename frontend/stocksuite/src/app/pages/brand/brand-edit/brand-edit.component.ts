import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IBrand } from '../../../model/brand.model';

@Component({
  selector: 'app-brand-edit',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './brand-edit.component.html',
  styleUrl: './brand-edit.component.css',
})
export class BrandEditComponent {
  public brand?: IBrand;
}
