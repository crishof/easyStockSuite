import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { IProduct } from '../../model/product.model';
import { ProductService } from '../../services/product.service';
import { data } from 'jquery';
import { BrandService } from '../../services/brand.service';
import { IBrand } from '../../model/brand.model';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {}
