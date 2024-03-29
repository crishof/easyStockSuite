import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { ProductsComponent } from './pages/product/products/products.component';
import { ProductDetailsComponent } from './pages/product/product-details/product-details.component';
import { BrandComponent } from './pages/brand/brand/brand.component';
import { CategoryComponent } from './pages/category/category/category.component';
import { SupplierComponent } from './pages/supplier/supplier/supplier.component';
import { SupplierDetailsComponent } from './pages/supplier/supplier-details/supplier-details.component';
import { CategoryDetailsComponent } from './pages/category/category-details/category-details.component';
import { BrandDetailsComponent } from './pages/brand/brand-details/brand-details.component';
import { BrandEditComponent } from './pages/brand/brand-edit/brand-edit.component';
import { BrandCreateComponent } from './pages/brand/brand-create/brand-create.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'products', component: ProductsComponent },
  { path: 'products/:id', component: ProductDetailsComponent },
  { path: 'brand', component: BrandComponent },
  { path: 'brand/create', component: BrandCreateComponent },
  { path: 'brand/:id', component: BrandDetailsComponent },
  { path: 'brand/:id/edit', component: BrandEditComponent },
  { path: 'category', component: CategoryComponent },
  { path: 'category/:id', component: CategoryDetailsComponent },
  { path: 'supplier', component: SupplierComponent },
  { path: 'supplier/:id', component: SupplierDetailsComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' },
];
