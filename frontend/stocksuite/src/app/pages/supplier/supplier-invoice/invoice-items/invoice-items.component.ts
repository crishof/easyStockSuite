import { CommonModule, NgFor } from '@angular/common';
import { Component, Input } from '@angular/core';
import { IProduct } from '../../../../model/product.model';

@Component({
  selector: 'app-invoice-items',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './invoice-items.component.html',
  styleUrl: './invoice-items.component.css',
})
export class InvoiceItemsComponent {
  @Input() invoiceItems: IProduct[] = [];
}
