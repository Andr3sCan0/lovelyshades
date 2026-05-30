export interface Producto {
  idProducto?: number;
  nombre: string;
  descripcion: string;
  precio: number;
  stock: number;
  estado?: boolean;
  valorCosto?: number;
  valorVenta?: number;
  idMarca?: number;
  idCategoria?: number;
  codigoInterno?: string;
  color?: string;
  fechaIngreso?: string; // ISO date string
}