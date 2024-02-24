import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { IBrand } from '../../model/brand.model';
import { BrandService } from '../../services/brand.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-brand',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './brand.component.html',
  styleUrl: './brand.component.css',
})
export class BrandComponent implements OnInit {
  brandList: IBrand[] = [];
  private _brandService = inject(BrandService);
  private _router = inject(Router);

  ngOnInit(): void {
    this._brandService.getBrands().subscribe((data: IBrand[]) => {
      this.brandList = data;
    });
  }

  navegate(id: string): void {
    this._router.navigate(['/brand', id]);
  }
}
