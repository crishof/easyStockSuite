import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ICategory } from '../model/category.model';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private _http = inject(HttpClient);
  private _urlBase = 'http://localhost:9500/category';

  getCategories(): Observable<ICategory[]> {
    return this._http.get<ICategory[]>(`${this._urlBase}/getAll`);
  }

  getCategory(id: string): Observable<ICategory> {
    return this._http.get<ICategory>(`${this._urlBase}/getById/${id}`);
  }
}
