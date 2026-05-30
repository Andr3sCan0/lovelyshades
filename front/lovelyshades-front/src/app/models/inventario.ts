import { Producto } from './producto';
import { Factura } from './factura';

export interface Inventario {
  idInventario?: number;
  idProducto: number;
  producto?: { idProducto: number } | Producto;
  tipoMovimiento: string; // Entrada, Salida, Ajuste, Devolucion
  cantidad: number;
  stockAntes: number;
  stockDespues: number;
  fechaMovimiento?: string;
  idUsuario: number;
  idFactura?: number;
  factura?: { idFactura: number } | Factura;
  idServicio?: number;
  servicio?: any;
  motivo?: string;
  observaciones?: string;
}
