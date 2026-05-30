import { Producto } from './producto';
import { Factura } from './factura';

export interface DetalleFactura {
  idDetalle?: number;
  idFactura?: number;
  idProducto: number;
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
  factura?: { idFactura: number } | Factura;
  producto?: { idProducto: number } | Producto;
}
