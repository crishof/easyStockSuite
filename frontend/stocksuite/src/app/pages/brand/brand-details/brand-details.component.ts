import { Component, OnInit, inject } from '@angular/core';
import { IBrand } from '../../../model/brand.model';
import { BrandService } from '../../../services/brand.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-brand-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './brand-details.component.html',
  styleUrl: './brand-details.component.css',
})
export class BrandDetailsComponent implements OnInit {
  loading: boolean = true;
  public brand?: IBrand;

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
}
