import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { IInvoiceItem } from '../../../../model/invoice-item.model';

@Component({
  selector: 'app-invoice-items',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './invoice-items.component.html',
  styleUrl: './invoice-items.component.css',
})
export class InvoiceItemsComponent {
  @Input() invoiceItems: IInvoiceItem[] = [];
}
