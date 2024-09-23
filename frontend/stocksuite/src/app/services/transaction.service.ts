import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ITransaction } from '../model/transaction.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TransactionService {
  private _http = inject(HttpClient);

  private _urlBase = 'http://localhost:443/transaction-sv/transaction';

  getTransactions(id: string): Observable<ITransaction[]> {
    return this._http.get<ITransaction[]>(
      `${this._urlBase}/getAllBySupplier/${id}`
    );
  }
}
