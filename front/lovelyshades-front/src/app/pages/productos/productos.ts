import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Producto } from '../../models/producto';
import { ProductoService } from '../../services/producto.service';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './productos.html',
  styleUrl: './productos.scss'
})
export class ProductosComponent implements OnInit {

  productos: Producto[] = [];
  productosMostrados: Producto[] = [];

  producto: Producto = {
    nombre: '',
    descripcion: '',
    precio: 0,
    stock: 0
  };

  editando = false;
  
  // Filtros y búsqueda
  searchTerm: string = '';
  precioMin: number = 0;
  precioMax: number = 1000000;
  ordenarPor: string = 'nombre';
  vistaGrilla: boolean = true;

  constructor(
    private productoService: ProductoService
  ) {}

  ngOnInit(): void {
    this.listarProductos();
  }

  listarProductos(): void {
    this.productoService.listar()
      .subscribe(data => {
        this.productos = data;
        this.aplicarFiltros();
      });
  }

  aplicarFiltros(): void {
    let filtrados = this.productos.filter(p => {
      const nombreMatch = p.nombre.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
                         p.descripcion.toLowerCase().includes(this.searchTerm.toLowerCase());
      const precioMatch = p.precio >= this.precioMin && p.precio <= this.precioMax;
      return nombreMatch && precioMatch;
    });

    // Ordenar
    filtrados = this.ordenarProductos(filtrados);
    this.productosMostrados = filtrados;
  }

  ordenarProductos(productos: Producto[]): Producto[] {
    const sorted = [...productos];
    
    switch(this.ordenarPor) {
      case 'nombre':
        sorted.sort((a, b) => a.nombre.localeCompare(b.nombre));
        break;
      case 'precio-asc':
        sorted.sort((a, b) => a.precio - b.precio);
        break;
      case 'precio-desc':
        sorted.sort((a, b) => b.precio - a.precio);
        break;
      case 'stock':
        sorted.sort((a, b) => b.stock - a.stock);
        break;
    }
    
    return sorted;
  }

  guardar(): void {
    if (
      !this.producto.nombre.trim() ||
      !this.producto.descripcion.trim()
    ) {
      alert('Todos los campos son obligatorios');
      return;
    }

    if (this.editando) {
      this.productoService.actualizar(this.producto)
        .subscribe(() => {
          this.listarProductos();
          this.limpiarFormulario();
          alert('Producto actualizado exitosamente');
        });
    } else {
      this.productoService.crear(this.producto)
        .subscribe(() => {
          this.listarProductos();
          this.limpiarFormulario();
          alert('Producto creado exitosamente');
        });
    }
  }

  editar(producto: Producto): void {
    this.producto = { ...producto };
    this.editando = true;
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  eliminar(id: number | undefined): void {
    if (!id) return;
    
    if (confirm('¿Está seguro de que desea eliminar este producto?')) {
      this.productoService.eliminar(id)
        .subscribe(() => {
          this.listarProductos();
          alert('Producto eliminado exitosamente');
        });
    }
  }

  limpiarFormulario(): void {
    this.producto = {
      nombre: '',
      descripcion: '',
      precio: 0,
      stock: 0
    };
    this.editando = false;
  }

  onBusquedaChange(): void {
    this.aplicarFiltros();
  }

  onFiltroChange(): void {
    this.aplicarFiltros();
  }

  toggleVista(): void {
    this.vistaGrilla = !this.vistaGrilla;
  }
}

export { ProductoService };
