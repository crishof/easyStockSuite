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
}
