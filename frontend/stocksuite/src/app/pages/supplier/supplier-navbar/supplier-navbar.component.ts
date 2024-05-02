import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-supplier-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './supplier-navbar.component.html',
  styleUrl: './supplier-navbar.component.css'
})
export class SupplierNavbarComponent {

}
