import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-product-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './product-navbar.component.html',
  styleUrl: './product-navbar.component.css',
})
export class ProductNavbarComponent {}
