import { HttpClient } from "@angular/common/http";
import { Producto } from "../models/producto";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class ProductoJpaService {

  private apiUrl =
    'http://localhost:8080/api/jpa/productos';

  constructor(
    private http: HttpClient
  ) {}

  listar() {

    return this.http.get<Producto[]>(
      this.apiUrl
    );
  }

  listarBajoStock() {

    return this.http.get<Producto[]>(
      `${this.apiUrl}/bajo-stock`
    );
  }

  buscarPorNombre(nombre: string) {

    return this.http.get<Producto[]>(
      `${this.apiUrl}/buscar?nombre=${nombre}`
    );
  }

  crear(producto: Producto) {

    return this.http.post<Producto>(
      this.apiUrl,
      producto
    );
  }

  actualizar(producto: Producto) {

    return this.http.put<Producto>(
      `${this.apiUrl}/${producto.idProducto}`,
      producto
    );
  }

  eliminar(id: number) {

    return this.http.delete(
      `${this.apiUrl}/${id}`
    );
  }
}