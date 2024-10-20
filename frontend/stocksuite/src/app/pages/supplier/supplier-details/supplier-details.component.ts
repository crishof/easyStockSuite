import { Component, Input, OnInit, inject } from '@angular/core';

import { CommonModule } from '@angular/common';
import { SupplierService } from '../../../services/supplier.service';
import { ISupplier } from '../../../model/supplier.model';

@Component({
  selector: 'app-supplier-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './supplier-details.component.html',
  styleUrl: './supplier-details.component.css',
})
export class SupplierDetailsComponent implements OnInit {
  @Input() supplier: ISupplier | null = null;
  selectedSupplier: ISupplier | null = null;
  readonly _supplierService = inject(SupplierService);

  ngOnInit(): void {
    this._supplierService.selectedSupplier$.subscribe((supplier) => {
      this.selectedSupplier = supplier;
    });
    console.log('Supplier: ' + this.selectedSupplier?.name);
  }
}
