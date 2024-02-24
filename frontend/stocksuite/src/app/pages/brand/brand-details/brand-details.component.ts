import { Component, OnInit, inject } from '@angular/core';
import { IBrand } from '../../../model/brand.model';
import { BrandService } from '../../../services/brand.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { BrandEditComponent } from "../brand-edit/brand-edit.component";

@Component({
    selector: 'app-brand-details',
    standalone: true,
    templateUrl: './brand-details.component.html',
    styleUrl: './brand-details.component.css',
    imports: [CommonModule, BrandEditComponent]
})
export class BrandDetailsComponent implements OnInit {
  loading: boolean = true;
  public brand?: IBrand;
  editingMode: boolean = false;

  private _route = inject(ActivatedRoute);
  private _brandService = inject(BrandService);

  ngOnInit(): void {
    this._route.params.subscribe((params) => {
      this._brandService.getBrand(params['id']).subscribe((data: IBrand) => {
        this.brand = data;
        this.loading = false;
      });
    });
  }

  startEditing():void {
    this.editingMode = true;
  }

  saveChanges(updatedBrand : IBrand): void{
    this.editingMode = false;
  }
}
