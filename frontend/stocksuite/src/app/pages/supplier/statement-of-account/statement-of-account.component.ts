import { CommonModule, NgClass } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SupplierDetailsComponent } from '../supplier-details/supplier-details.component';
import { SupplierNavbarComponent } from '../supplier-navbar/supplier-navbar.component';
import { ITransaction } from '../../../model/transaction.model';
import { SupplierService } from '../../../services/supplier.service';

@Component({
  selector: 'app-statement-of-account',
  standalone: true,
  imports: [
    CommonModule,
    SupplierNavbarComponent,
    SupplierDetailsComponent,
    FormsModule,
    ReactiveFormsModule,
    NgClass,
  ],
  templateUrl: './statement-of-account.component.html',
  styleUrl: './statement-of-account.component.css',
})
export class StatementOfAccountComponent implements OnInit {
  transactionList: ITransaction[] = [];
  balance: number = 0;
  private _supplierService = inject(SupplierService);

  ngOnInit(): void {}
}
