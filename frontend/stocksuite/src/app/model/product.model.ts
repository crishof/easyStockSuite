export interface IProduct {

    id: number;
    brandName : string;
    code: string;
    model: string;
    description: string;
    taxRate: number;
    sellingPrice: number;
    stock: number;

}

/* 
        {"id":"4b719a46-7c66-47e3-8914-ecc77928670c","
        brandName":"PHONIC","
        code":"AM105FXU","
        model":"AM105FXU","
        description":"Mixer   |  Analogico  |  6 canales (2 XLR/TRS + 8 TRS) | Salida L&R TRS  | Vivo |  Eq 3b +  Multiefecto","purchasePrice":97749.88,"taxRate":0.105,"
        sellingPrice":156619.75,"
        stock":0}
      */