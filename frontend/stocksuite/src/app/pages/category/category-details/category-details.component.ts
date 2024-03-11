import {
  Component,
  EventEmitter,
  Input,
  NgModule,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
  inject,
} from '@angular/core';
import { ICategory } from '../../../model/category.model';
import { CategoryService } from '../../../services/category.service';
import { ActivatedRoute } from '@angular/router';
import { FormsModule, NgModel } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-category-details',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './category-details.component.html',
  styleUrl: './category-details.component.css',
})

/*

export class CategoryDetailsComponent implements OnInit {
  loading: boolean = true;
  public category?: ICategory;
  
  private _categoryService = inject(CategoryService);
  private _route = inject(ActivatedRoute);
  
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
  
  */
export class CategoryDetailsComponent implements OnChanges {
  loading: boolean = true;
  public category?: ICategory;

  @Input() selectedCategory: { [key: string]: any } | null = null;

  constructor(private _categoryService: CategoryService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['selectedCategory'] && this.selectedCategory) {
      this.loading = true;

      this._categoryService
        .getCategory(this.selectedCategory['id'])
        .subscribe((data: ICategory) => {
          this.category = data;
          this.loading = false;
        });
    }
  }
}
