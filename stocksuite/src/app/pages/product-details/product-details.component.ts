import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { IProduct } from '../../model/product.model';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../../services/product-service.service';

@Component({
  selector: 'app-product-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-details.component.html',
  styleUrl: './product-details.component.css'
})
export class ProductDetailsComponent implements OnInit{

  loading: boolean = true;
  public product?: IProduct;

  private route = inject(ActivatedRoute);
  private _apiService = inject(ProductService);
private id : string = 'b3ad3ce7-a5ec-42a2-9d8b-88741d6dc74f'

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this._apiService.getProduct(params[this.id]).subscribe((data: IProduct) => {
        this.product = data;
        this.loading = false;
      });
  });
  }


}
