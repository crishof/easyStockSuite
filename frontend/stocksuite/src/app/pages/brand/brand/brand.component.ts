import { CommonModule } from '@angular/common';
import { Component, Inject, OnInit, inject } from '@angular/core';
import { IBrand } from '../../../model/brand.model';
import { BrandService } from '../../../services/brand.service';
import { Router } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { DefaultImageDirective } from '../../../utils/default-image.directive';

@Component({
  selector: 'app-brand',
  standalone: true,
  imports: [CommonModule, FormsModule, DefaultImageDirective],
  templateUrl: './brand.component.html',
  styleUrl: './brand.component.css',
})
export class BrandComponent implements OnInit {
  brandList: IBrand[] = [];
  sortedColumn: keyof IBrand | undefined;
  isAscendingOrder: boolean = true;
  filteredBrandList: IBrand[] = [];
  searchTerm: string = '';

  private _brandService = inject(BrandService);
  private _router = inject(Router);
  private _sanitizer = inject(DomSanitizer);

  ngOnInit(): void {
    this._brandService.getBrands().subscribe((data: IBrand[]) => {
      this.brandList = data;

      this.filteredBrandList = this.brandList;
    });
  }

  toBrandDetails(id: string): void {
    this._router.navigate(['/brand', id]);
  }

  toNewBrand(): void {
    this._router.navigate(['/brand/create']);
  }

  getImageUrl(image: any): any {
    const base64Image = image.content;
    return this._sanitizer.bypassSecurityTrustResourceUrl(
      'data:image/jpeg;base64,' + base64Image
    );
  }

  arrayBufferToBase64(buffer: ArrayBuffer): string {
    if (typeof window !== 'undefined') {
      let binary = '';
      const bytes = new Uint8Array(buffer);
      for (let i = 0; i < bytes.byteLength; i++) {
        binary += String.fromCharCode(bytes[i]);
      }
      return window.btoa(binary);
    }
    return '';
  }

  sortColumn(column: keyof IBrand) {
    if (this.sortedColumn === column) {
      this.isAscendingOrder = !this.isAscendingOrder;
    } else {
      this.sortedColumn = column;
      this.isAscendingOrder = true;
    }

    if (this.sortedColumn) {
      this.filteredBrandList.sort((a, b) => {
        const orderFactor = this.isAscendingOrder ? 1 : -1;

        const aValue = a[this.sortedColumn!] as string | number | undefined;
        const bValue = b[this.sortedColumn!] as string | number | undefined;

        if (aValue !== undefined && bValue !== undefined) {
          if (aValue < bValue) {
            return -1 * orderFactor;
          } else if (aValue > bValue) {
            return 1 * orderFactor;
          } else {
            return 0;
          }
        }

        return 0;
      });
    }
  }

  handleKeyup(event: KeyboardEvent) {
    const searchTerm = (event.target as HTMLInputElement)?.value;
    this.filterBrands(searchTerm);
  }

  filterBrands(searchTerm: string) {
    if (!searchTerm) {
      this.filteredBrandList = this.brandList.slice();
    } else {
      const lowerCaseSearchTerm = searchTerm.toLowerCase();
      this.filteredBrandList = this.brandList.filter(
        (brand) =>
          brand.name.toLowerCase().includes(lowerCaseSearchTerm) ||
          brand.id.toString().includes(lowerCaseSearchTerm)
      );
    }
  }

  searchBrand() {
    console.log('searchBrand Called' + this.searchTerm);
    this.filterBrands(this.searchTerm);
  }
}
