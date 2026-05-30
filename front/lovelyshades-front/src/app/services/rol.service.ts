import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rol } from '../models/rol';

@Injectable({
  providedIn: 'root'
})
export class RolService {

  private api = 'http://localhost:8080/api/roles';

  constructor(private http: HttpClient) {}

  obtenerTodos(): Observable<Rol[]> {
    return this.http.get<Rol[]>(this.api);
  }

  obtenerPorId(id: number): Observable<Rol> {
    return this.http.get<Rol>(`${this.api}/${id}`);
  }

  obtenerPorNombre(nombre: string): Observable<Rol> {
    return this.http.get<Rol>(`${this.api}/nombre/${nombre}`);
  }

  crear(rol: Rol): Observable<Rol> {
    return this.http.post<Rol>(this.api, rol);
  }

  actualizar(id: number, rol: Rol): Observable<Rol> {
    return this.http.put<Rol>(`${this.api}/${id}`, rol);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }

  cambiarEstado(id: number, estado: boolean): Observable<void> {
    return this.http.put<void>(`${this.api}/${id}/estado?estado=${estado}`, {});
  }
}
