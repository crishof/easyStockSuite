import { Injectable, inject } from '@angular/core';
import { ISupplier } from '../model/supplier.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class SupplierService {
  private _http = inject(HttpClient);
  private _urlBase = 'http://localhost:443/mono-sv/supplier';

  getSuppliers(): Observable<ISupplier[]> {
    return this._http.get<ISupplier[]>(`${this._urlBase}/getAll`);
  }

  getSupplier(id: string): Observable<ISupplier> {
    return this._http.get<ISupplier>(`${this._urlBase}/getById/${id}`);
  }
}
