import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-product-search',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-search.component.html',
  styleUrl: './product-search.component.css',
})
export class ProductSearchComponent {
  searchTerm: string = '';
  isFormSubmitted: boolean = false;
  @Output() search = new EventEmitter<string>();
  @Output() searchStock = new EventEmitter<string>();

  onSearch() {
    this.search.emit(this.searchTerm);
  }
  searchWithStock() {
    this.searchStock.emit(this.searchTerm);
  }

  onKeyUp(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.onSearch();
    }
  }
}
