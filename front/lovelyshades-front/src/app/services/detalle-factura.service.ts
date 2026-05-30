import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DetalleFactura } from '../models/detalle-factura';

@Injectable({
  providedIn: 'root'
})
export class DetalleFacturaService {

  private apiUrl = 'http://localhost:8080/api/detalle-facturas';

  constructor(private http: HttpClient) { }

  crear(detalle: DetalleFactura): Observable<DetalleFactura> {
    return this.http.post<DetalleFactura>(this.apiUrl, detalle);
  }

  listar(): Observable<DetalleFactura[]> {
    return this.http.get<DetalleFactura[]>(this.apiUrl);
  }

  obtenerPorId(id: number): Observable<DetalleFactura> {
    return this.http.get<DetalleFactura>(`${this.apiUrl}/${id}`);
  }

  actualizar(detalle: DetalleFactura): Observable<DetalleFactura> {
    return this.http.put<DetalleFactura>(`${this.apiUrl}/${detalle.idDetalle}`, detalle);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
