import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { IBrand } from '../model/brand.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class BrandService {
  private _http = inject(HttpClient);
  private _urlBase = 'http://localhost:443/mono-sv/brand';
  //private _urlBase = 'http://localhost:9500/brand';

  getBrands(): Observable<IBrand[]> {
    return this._http.get<IBrand[]>(`${this._urlBase}/getAll`);
  }

  getBrand(id: string): Observable<IBrand> {
    return this._http.get<IBrand>(`${this._urlBase}/getById/${id}`);
  }

  updateBrand(id: string, formData: FormData): Observable<IBrand> {
    return this._http.put<IBrand>(`${this._urlBase}/update/${id}`, formData);
  }

  createBrand(brand: IBrand): Observable<IBrand> {
    return this._http.post<IBrand>(`${this._urlBase}/save`, brand);
  }

  deleteBrand(id: string): Observable<any> {
    return this._http.delete<string>(`${this._urlBase}/delete/${id}`, {
      responseType: 'text' as 'json',
    });
  }

  updateBrandLogo(id: string, logo: File): Observable<IBrand> {
    const formData = new FormData();
    formData.append('logo', logo, logo.name);

    return this._http.put<IBrand>(
      `${this._urlBase}/updateLogo/${id}`,
      formData
    );
  }
}
