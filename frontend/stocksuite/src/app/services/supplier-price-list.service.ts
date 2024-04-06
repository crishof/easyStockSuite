import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { IProduct } from '../model/product.model';
import { Observable } from 'rxjs';
import { ISupplierProduct } from '../model/supplierProduct';

@Injectable({
  providedIn: 'root',
})
export class SupplierPriceListService {
  private _http = inject(HttpClient);

  //TODO: corregir camelCase de URL

  private _urlBase = 'http://localhost:443/supplierpricelist-sv/priceList';
  private _urlSupplier = 'http://localhost:443/supplierpricelist-sv/supplier';
  constructor() {}

  getAllByFilter(
    supplierId: string,
    brand: string,
    filter: string
  ): Observable<ISupplierProduct[]> {
    const params = new HttpParams();
    params.set('supplierId', supplierId);
    params.set('brand', brand);
    params.set('filter', filter);
    return this._http.get<ISupplierProduct[]>(
      `${this._urlBase}/getAllByFilter`,
      {
        params,
      }
    );
  }

  getBrandsBySupplierId(supplierId: string): Observable<String[]> {
    let params = new HttpParams();
    params = params.set('supplierId', supplierId);
    return this._http.get<String[]>(
      `${this._urlSupplier}/getBrandsBySupplier`,
      {
        params: params,
      }
    );
  }

  getAllBrands(): Observable<String[]> {
    return this._http.get<String[]>(`${this._urlSupplier}/getAllBrands`);
  }
}
