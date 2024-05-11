import { CommonModule, NgClass } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { ISupplier } from '../../../model/supplier.model';
import { Router } from '@angular/router';
import { SupplierService } from '../../../services/supplier.service';
import { SupplierNavbarComponent } from '../supplier-navbar/supplier-navbar.component';
import { SupplierDetailsComponent } from '../supplier-details/supplier-details.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-supplier',
  standalone: true,
  imports: [
    CommonModule,
    SupplierNavbarComponent,
    SupplierDetailsComponent,
    FormsModule,
    ReactiveFormsModule,
    NgClass,
  ],
  templateUrl: './supplier.component.html',
  styleUrl: './supplier.component.css',
})
export class SupplierComponent implements OnInit {
  supplierList: ISupplier[] = [];
  private _supplierService = inject(SupplierService);
  private _router = inject(Router);

  private subscription?: Subscription;

  ngOnInit(): void {}

  searchTerm: string = '';
  isFormSubmitted: boolean = false;
  selectedComponent: string = 'supplier';
  selectedSupplier: ISupplier | null = null;
  selectedSupplierId: string = '';

  onKeyUp(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.onFormSubmit();
    }
  }

  onFormSubmit() {
    console.log('FORM SUCCESS');
    this.isFormSubmitted = true;
    if (this.searchTerm.length > 0) {
      this.searchSuppliers();
    } else {
      this.supplierList = [];
    }
  }
  searchSuppliers(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
    this.subscription = this._supplierService
      .getAllByFilter(this.searchTerm)
      .subscribe((data: ISupplier[]) => {
        this.supplierList = data;
        console.log(this.supplierList);
      });
  }

  selectSupplier(supplier: ISupplier): void {
    this.selectedSupplier = supplier;
  }

  navegate(id: string): void {
    this._router.navigate(['/supplier/' + id]);
  }
}
