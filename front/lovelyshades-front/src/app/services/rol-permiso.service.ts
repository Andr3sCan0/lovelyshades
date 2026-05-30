import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RolPermiso } from '../models/rol-permiso';

@Injectable({
  providedIn: 'root'
})
export class RolPermisoService {

  private api = 'http://localhost:8080/api/rol-permisos';

  constructor(private http: HttpClient) {}

  asignarPermiso(idRol: number, idPermiso: number): Observable<RolPermiso> {
    return this.http.post<RolPermiso>(`${this.api}/rol/${idRol}/permiso/${idPermiso}`, {});
  }

  removerPermiso(idRol: number, idPermiso: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/rol/${idRol}/permiso/${idPermiso}`);
  }

  obtenerPermisosDeRol(idRol: number): Observable<RolPermiso[]> {
    return this.http.get<RolPermiso[]>(`${this.api}/rol/${idRol}`);
  }

  obtenerRolesDelPermiso(idPermiso: number): Observable<RolPermiso[]> {
    return this.http.get<RolPermiso[]>(`${this.api}/permiso/${idPermiso}`);
  }
}
