import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatNativeDateModule } from '@angular/material/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-supplier-invoice',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatNativeDateModule,
  ],
  templateUrl: './supplier-invoice.component.html',
  styleUrl: './supplier-invoice.component.css',
})
export class SupplierInvoiceComponent implements OnInit {
  dateControl = new FormControl(new Date());

  ngOnInit(): void {
    this.dateControl.setValue(new Date());
  }
}
