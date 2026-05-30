import { Permiso } from './permiso';

export interface Rol {
  idRol?: number;
  nombreRol: string;
  estado: boolean;
  fechaCreacion?: Date;
  permisos?: Permiso[];
}
