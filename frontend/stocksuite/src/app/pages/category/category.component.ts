import { Component, OnInit, inject } from '@angular/core';
import { ICategory } from '../../model/category.model';
import { Router } from '@angular/router';
import { CategoryService } from '../../services/category.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './category.component.html',
  styleUrl: './category.component.css',
})
export class CategoryComponent implements OnInit {
  categoryList: ICategory[] = [];
  private _categoryService = inject(CategoryService);
  private _router = inject(Router);

  ngOnInit(): void {
    this._categoryService.getCategories().subscribe((data: ICategory[]) => {
      this.categoryList = data;
    });
  }

  navegate(id: string): void {
    this._router.navigate(['/category', id]);
  }
}
