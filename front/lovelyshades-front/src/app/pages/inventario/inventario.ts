import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Inventario } from '../../models/inventario';
import { Producto } from '../../models/producto';
import { InventarioService } from '../../services/inventario.service';
import { ProductoService } from '../../services/producto.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-inventario',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './inventario.html',
  styleUrl: './inventario.scss'
})
export class InventarioComponent implements OnInit {

  inventarios: Inventario[] = [];
  inventariosMostrados: Inventario[] = [];
  productos: Producto[] = [];

  inventario: Inventario = {
    idProducto: 0,
    stockAntes: 0,
    stockDespues: 0,
    tipoMovimiento: '',
    cantidad: 0,
    idUsuario: 0,
    motivo: '',
    observaciones: ''
  };

  editando = false;
  searchTerm: string = '';
  tiposMovimiento = ['ENTRADA', 'SALIDA', 'AJUSTE', 'DEVOLUCION'];
  productoSeleccionado: Producto | null = null;

  constructor(
    private inventarioService: InventarioService,
    private productoService: ProductoService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.listarInventarios();
    this.listarProductos();
  }

  listarInventarios(): void {
    this.inventarioService.listar()
      .subscribe(data => {
        this.inventarios = data.map(inv => ({
          ...inv,
          idProducto: inv.idProducto || (inv.producto as Producto)?.idProducto || 0
        }));
        this.aplicarFiltros();
      });
  }

  listarProductos(): void {
    this.productoService.listar()
      .subscribe(data => {
        this.productos = data;
      });
  }

  aplicarFiltros(): void {
    if (!this.searchTerm.trim()) {
      this.inventariosMostrados = this.inventarios;
      return;
    }

    this.inventariosMostrados = this.inventarios.filter(i => {
      const productoName = this.productos.find(p => p.idProducto === i.idProducto)?.nombre ||
        (i.producto as Producto)?.nombre || '';
      return productoName.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
             i.tipoMovimiento.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
             (i.observaciones && i.observaciones.toLowerCase().includes(this.searchTerm.toLowerCase()));
    });
  }

  seleccionarProducto(evento: any): void {
    const productoId = parseInt(evento.target.value);
    this.inventario.idProducto = productoId;
    const producto = this.productos.find(p => p.idProducto === productoId);
    if (producto) {
      this.productoSeleccionado = producto;
      this.inventario.stockAntes = producto.stock;
      this.calcularStockDespues();
    }
  }

  calcularStockDespues(): void {
    if (this.inventario.tipoMovimiento === 'ENTRADA') {
      this.inventario.stockDespues = this.inventario.stockAntes + this.inventario.cantidad;
    } else if (this.inventario.tipoMovimiento === 'SALIDA') {
      this.inventario.stockDespues = this.inventario.stockAntes - this.inventario.cantidad;
    } else if (this.inventario.tipoMovimiento === 'AJUSTE') {
      this.inventario.stockDespues = this.inventario.cantidad;
    } else if (this.inventario.tipoMovimiento === 'DEVOLUCION') {
      this.inventario.stockDespues = this.inventario.stockAntes + this.inventario.cantidad;
    }
  }

  guardar(): void {
    if (!this.inventario.idProducto || !this.inventario.tipoMovimiento || this.inventario.cantidad <= 0) {
      alert('Completa todos los campos obligatorios');
      return;
    }

    if (this.inventario.tipoMovimiento === 'SALIDA' && this.inventario.cantidad > this.inventario.stockAntes) {
      alert('No puedes retirar más stock del disponible');
      return;
    }

    const usuario = this.authService.obtenerUsuario();
    this.inventario.idUsuario = usuario?.idUsuario || 0;

    const inventarioRequest: Inventario = {
      ...this.inventario,
      producto: { idProducto: this.inventario.idProducto },
      factura: this.inventario.idFactura ? { idFactura: this.inventario.idFactura } : undefined
    };

    if (this.editando) {
      this.inventarioService.actualizar(inventarioRequest)
        .subscribe({
          next: () => {
            this.listarInventarios();
            this.limpiarFormulario();
            alert('Movimiento actualizado exitosamente');
          },
          error: (error) => {
            console.error('Error al actualizar:', error);
            alert('Error al actualizar el movimiento');
          }
        });
    } else {
      this.inventarioService.crear(inventarioRequest)
        .subscribe({
          next: () => {
            this.listarInventarios();
            this.limpiarFormulario();
            alert('Movimiento de inventario registrado exitosamente');
          },
          error: (error) => {
            console.error('Error al crear:', error);
            alert('Error al registrar el movimiento');
          }
        });
    }
  }

  editar(inventario: Inventario): void {
    this.inventario = { ...inventario };
    this.productoSeleccionado = this.productos.find(p => p.idProducto === inventario.idProducto) || null;
    this.editando = true;
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  eliminar(id: number | undefined): void {
    if (id && confirm('¿Deseas eliminar este movimiento?')) {
      this.inventarioService.eliminar(id)
        .subscribe({
          next: () => {
            this.listarInventarios();
            alert('Movimiento eliminado exitosamente');
          },
          error: (error) => {
            console.error('Error al eliminar:', error);
            alert('Error al eliminar el movimiento');
          }
        });
    }
  }

  limpiarFormulario(): void {
    this.inventario = {
      idProducto: 0,
      stockAntes: 0,
      stockDespues: 0,
      tipoMovimiento: '',
      cantidad: 0,
      idUsuario: 0,
      motivo: '',
      observaciones: ''
    };
    this.productoSeleccionado = null;
    this.editando = false;
  }

  getClaseEstado(tipoMovimiento: string): string {
    switch (tipoMovimiento) {
      case 'ENTRADA':
        return 'entrada';
      case 'SALIDA':
        return 'salida';
      case 'AJUSTE':
        return 'ajuste';
      case 'DEVOLUCION':
        return 'devolucion';
      default:
        return '';
    }
  }

  getNombreProducto(idProducto: number): string {
    return this.productos.find(p => p.idProducto === idProducto)?.nombre || 'N/A';
  }
}

