import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { ProductsComponent } from './pages/products/products.component';
import { ProductDetailsComponent } from './pages/product-details/product-details.component';
import { BrandComponent } from './pages/brand/brand.component';
import { CategoryComponent } from './pages/category/category.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'products', component: ProductsComponent },
  { path: 'products/:id', component: ProductDetailsComponent },
  { path: 'brand', component: BrandComponent },
  { path: 'category', component: CategoryComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' },
];
