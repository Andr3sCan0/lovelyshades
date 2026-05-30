import { Rol } from './rol';

export interface Usuario {
  idUsuario?: number;
  nombreUsuario: string;
  contrasenaHash?: string;
  email: string;
  rol?: Rol;
  estado: boolean;
  fechaCreacion?: Date;
  ultimoAcceso?: Date;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  nombreUsuario: string;
  email: string;
  contrasenaHash: string;
  rol?: Rol;
  estado?: boolean;
}

export interface LoginResponse {
  token: string;
  usuario: Usuario;
}
