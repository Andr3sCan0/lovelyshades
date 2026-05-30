export interface Cliente {
  idCliente?: number;
  nombreCompleto: string;
  tipoIdentificacion: string;
  numeroIdentificacion: string;
  telefono: string;
  email: string;
  direccion: string;
  fechaRegistro?: string;
  estado?: boolean;
}