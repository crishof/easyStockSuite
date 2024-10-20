import { Injectable, inject } from '@angular/core';
import { ISupplier } from '../model/supplier.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class SupplierService {
  readonly _http = inject(HttpClient);

  readonly _urlBase = 'http://localhost:443/supplier-sv/supplier';

  getAllByFilter(filter: string): Observable<ISupplier[]> {
    const params = new HttpParams().set('filter', filter);
    return this._http.get<ISupplier[]>(`${this._urlBase}/getAllByFilter`, {
      params,
    });
  }
  getSuppliers(): Observable<ISupplier[]> {
    return this._http.get<ISupplier[]>(`${this._urlBase}/getAll`);
  }

  getSupplierByName(name: string): Observable<ISupplier> {
    return this._http.get<ISupplier>(`${this._urlBase}/getByName`);
  }

  getSupplier(id: string): Observable<ISupplier> {
    return this._http.get<ISupplier>(`${this._urlBase}/getById/${id}`);
  }

  readonly supplierSource = new BehaviorSubject<ISupplier | null>(null);
  selectedSupplier$ = this.supplierSource.asObservable();

  setSelectedSupplier(supplier: ISupplier) {
    this.supplierSource.next(supplier);
  }
}
