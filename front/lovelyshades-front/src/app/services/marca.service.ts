import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Marca } from '../models/marca';

@Injectable({
  providedIn: 'root'
})
export class MarcaService {

  private apiUrl = 'http://localhost:8080/api/marcas';

  constructor(private http: HttpClient) { }

  crear(marca: Marca): Observable<Marca> {
    return this.http.post<Marca>(this.apiUrl, marca);
  }

  listar(): Observable<Marca[]> {
    return this.http.get<Marca[]>(this.apiUrl);
  }

  obtenerPorId(id: number): Observable<Marca> {
    return this.http.get<Marca>(`${this.apiUrl}/${id}`);
  }

  actualizar(marca: Marca): Observable<Marca> {
    return this.http.put<Marca>(`${this.apiUrl}/${marca.idMarca}`, marca);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
