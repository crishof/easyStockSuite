import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { ISupplier } from '../../../model/supplier.model';
import { Router } from '@angular/router';
import { SupplierService } from '../../../services/supplier.service';

@Component({
  selector: 'app-supplier',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './supplier.component.html',
  styleUrl: './supplier.component.css',
})
export class SupplierComponent implements OnInit {
  supplierList: ISupplier[] = [];
  private _supplierService = inject(SupplierService);
  private _router = inject(Router);

  ngOnInit(): void {
    this._supplierService.getSuppliers().subscribe((data: ISupplier[]) => {
      this.supplierList = data;
    });
  }

  navegate(id: string): void {
    this._router.navigate(['/supplier/' + id]);
  }
}
