import { Component, OnInit, inject } from '@angular/core';
import { ICategory } from '../../../model/category.model';
import { CategoryService } from '../../../services/category.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-category-details',
  standalone: true,
  imports: [],
  templateUrl: './category-details.component.html',
  styleUrl: './category-details.component.css'
})
export class CategoryDetailsComponent implements OnInit {
  loading: boolean = true;
  public category?: ICategory;

  private _route = inject(ActivatedRoute);
  private _categoryService = inject(CategoryService);

  ngOnInit(): void {
    this._route.params.subscribe((params) => {
      this._categoryService
        .getCategory(params['id'])
        .subscribe((data: ICategory) => {
          this.category = data;
          this.loading = false;
        });
    });
  }
}
