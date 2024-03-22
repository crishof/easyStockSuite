import { UUID } from 'node:crypto';
import { IImage } from './image.model';

export interface ICategory {
  id: string;
  name: string;
  imageUrl?: string;
}
