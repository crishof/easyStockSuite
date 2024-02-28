import { IImage } from "./image.model";

export interface IBrand {
  id: string;
  name: string;
  logo?: IImage;
}
