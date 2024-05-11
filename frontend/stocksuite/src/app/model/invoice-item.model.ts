export interface IInvoiceItem {
  id: string;
  brandName: string;
  model: string;
  description: string;
  price: number;
  taxRate: number;
  discount: number;
  quantity: number;
}
