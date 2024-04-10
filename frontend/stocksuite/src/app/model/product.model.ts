import { IPrice } from './price.model';

export interface IProduct {
  id: string;
  brandName: string;
  code: string;
  model: string;
  description: string;
  categoryName: string;
  supplierId: string;
  hidden: boolean;
  imageId: string[];
  stock: number;
  priceResponse: IPrice;
  dimensionId: string;
}
