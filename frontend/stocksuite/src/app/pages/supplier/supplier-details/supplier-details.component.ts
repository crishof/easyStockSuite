import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

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
  loading: boolean = true;
  public supplier?: ISupplier;

  private _route = inject(ActivatedRoute);
  private _supplierService = inject(SupplierService);

  ngOnInit(): void {
    this._route.params.subscribe((params) => {
      this._supplierService
        .getSupplier(params['id'])
        .subscribe((data: ISupplier) => {
          this.supplier = data;
          this.loading = false;
        });
    });
  }
}
