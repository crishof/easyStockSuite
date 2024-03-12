import { Component, OnInit, inject } from '@angular/core';
import { ICategory } from '../../../model/category.model';
import { Router } from '@angular/router';
import { CategoryService } from '../../../services/category.service';
import { CommonModule } from '@angular/common';
import { CategoryDetailsComponent } from '../category-details/category-details.component';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [CommonModule, CategoryDetailsComponent],
  templateUrl: './category.component.html',
  styleUrl: './category.component.css',
})
export class CategoryComponent implements OnInit {
  categoryList: ICategory[] = [];
  private _categoryService = inject(CategoryService);
  private _router = inject(Router);

  ngOnInit(): void {
    this.loadCategories();
    this.subscribeToCategoryUpdatedEvent();
  }

  loadCategories(): void {
    this._categoryService.getCategories().subscribe((data: ICategory[]) => {
      this.categoryList = data;

      if (this.categoryList.length > 0) {
        this.selectedCategory = this.categoryList[0];
      }
    });
  }

  subscribeToCategoryUpdatedEvent(): void {
    this._categoryService.getCategoryUpdatedObservable().subscribe(() => {
      console.log('Evento categoryUpdated recibido en CategoryComponent');
      this.loadCategories();
    });
  }

  handleCategoryUpdated(): void {
    this.loadCategories();
  }

  navegate(id: string): void {
    this._router.navigate(['/category', id]);
  }

  selectedCategory: ICategory | null = null;

  showDetails(category: ICategory): void {
    this.selectedCategory = category;
  }
}
