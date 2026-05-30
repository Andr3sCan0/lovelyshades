import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Permiso } from '../models/permiso';

@Injectable({
  providedIn: 'root'
})
export class PermisoService {

  private api = 'http://localhost:8080/api/permisos';

  constructor(private http: HttpClient) {}

  obtenerTodos(): Observable<Permiso[]> {
    return this.http.get<Permiso[]>(this.api);
  }

  obtenerPorId(id: number): Observable<Permiso> {
    return this.http.get<Permiso>(`${this.api}/${id}`);
  }

  obtenerPorNombre(nombre: string): Observable<Permiso> {
    return this.http.get<Permiso>(`${this.api}/nombre/${nombre}`);
  }

  crear(permiso: Permiso): Observable<Permiso> {
    return this.http.post<Permiso>(this.api, permiso);
  }

  actualizar(id: number, permiso: Permiso): Observable<Permiso> {
    return this.http.put<Permiso>(`${this.api}/${id}`, permiso);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  cambiarEstado(id: number, estado: boolean): Observable<void> {
    return this.http.put<void>(`${this.api}/${id}/estado?estado=${estado}`, {});
  }
}
