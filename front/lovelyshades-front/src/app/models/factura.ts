import { Cliente } from './cliente';
import { DetalleFactura } from './detalle-factura';

export interface Factura {
  idFactura?: number;
  numeroFactura?: string;
  fecha?: string;
  subtotal: number;
  descuento?: number;
  impuesto?: number;
  total: number;
  medioPago: string;
  idUsuario: number;
  idCliente?: number;
  cliente?: { idCliente: number } | Cliente;
  estado?: string;
  detalles?: DetalleFactura[];
}
