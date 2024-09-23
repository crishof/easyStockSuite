import { CommonModule, NgClass } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SupplierDetailsComponent } from '../supplier-details/supplier-details.component';
import { SupplierNavbarComponent } from '../supplier-navbar/supplier-navbar.component';
import { ITransaction } from '../../../model/transaction.model';
import { SupplierService } from '../../../services/supplier.service';
import { TransactionService } from '../../../services/transaction.service';

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
  supplierId: string = '0eedd853-402f-46ce-b1df-181b3b65a7c7';
  balance: number = 0;
  private _supplierService = inject(SupplierService);
  private _transactionService = inject(TransactionService);

  ngOnInit(): void {
    this._transactionService
      .getTransactions(this.supplierId)
      .subscribe((data: ITransaction[]) => {
        this.transactionList = data;
      });
  }

  // Definir la función trackByFn para usar en el *ngFor
  trackByFn(index: number, item: ITransaction): any {
    return item.id; // O el campo único que tiene cada transacción, por ejemplo, el id
  }

  calculateBalance(): number[] {
    let balance = 0;
    const balanceList = this.transactionList.map((transaction) => {
      balance +=
        transaction.type === 'invoice'
          ? transaction.amount
          : -transaction.amount;
      return balance;
    });
    return balanceList;
  }
}
