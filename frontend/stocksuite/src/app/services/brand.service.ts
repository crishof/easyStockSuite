import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { IBrand } from '../model/brand.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class BrandService {
  private _http = inject(HttpClient);
  private _urlBase = 'http://localhost:9500/brand';

  getBrands(): Observable<IBrand[]> {
    return this._http.get<IBrand[]>(`${this._urlBase}/getAll`);
  }

  getBrand(id: string): Observable<IBrand> {
    return this._http.get<IBrand>(`${this._urlBase}/getById/${id}`);
  }

  updateBrand(id: string, updatedBrand: IBrand): Observable<IBrand> {
    return this._http.put<IBrand>(
      `${this._urlBase}/update/${id}`,
      updatedBrand
    );
  }

  createBrand(brand: IBrand): Observable<IBrand> {
    return this._http.post<IBrand>(`${this._urlBase}/save`, brand);
  }
}
