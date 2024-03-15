import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { IProduct } from '../model/product.model';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private _http = inject(HttpClient);
  //private _urlBase = 'http://localhost:443/mono-sv/product';
  private _urlBase = 'http://localhost:9500/product';

  getProducts(): Observable<IProduct[]> {
    return this._http.get<IProduct[]>(`${this._urlBase}/getAll`);
  }

  getProduct(id: string): Observable<IProduct> {
    return this._http.get<IProduct>(`${this._urlBase}/getById/${id}`);
  }
}
