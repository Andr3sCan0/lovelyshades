import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { firstValueFrom } from 'rxjs';

import { Factura } from '../../models/factura';
import { DetalleFactura } from '../../models/detalle-factura';
import { Producto } from '../../models/producto';
import { Cliente } from '../../models/cliente';
import { FacturaService } from '../../services/factura.service';
import { ProductoService } from '../../services/producto.service';
import { ClienteService } from '../../services/cliente.service';
import { DetalleFacturaService } from '../../services/detalle-factura.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-facturas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './facturas.html',
  styleUrl: './facturas.scss'
})
export class FacturasComponent implements OnInit {

  facturas: Factura[] = [];
  facturasMostradas: Factura[] = [];
  productos: Producto[] = [];
  clientes: Cliente[] = [];

  factura: Factura = {
    numeroFactura: '',
    subtotal: 0,
    descuento: 0,
    impuesto: 0,
    total: 0,
    medioPago: '',
    idUsuario: 0,
    idCliente: 0,
    estado: 'PAGADA',
    detalles: []
  };

  detalleActual: DetalleFactura = {
    idFactura: 0,
    idProducto: 0,
    cantidad: 1,
    precioUnitario: 0,
    subtotal: 0
  };

  detalles: DetalleFactura[] = [];
  editando = false;
  searchTerm: string = '';
  productoSeleccionado: Producto | null = null;

  constructor(
    private facturaService: FacturaService,
    private productoService: ProductoService,
    private clienteService: ClienteService,
    private detalleFacturaService: DetalleFacturaService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.listarFacturas();
    this.listarProductos();
    this.listarClientes();
    this.generarNumeroFactura();
  }

  listarFacturas(): void {
    this.facturaService.listar()
      .subscribe(data => {
        this.facturas = data;
        this.aplicarFiltros();
      });
  }

  listarProductos(): void {
    this.productoService.listar()
      .subscribe(data => {
        this.productos = data.filter(p => p.estado);
      });
  }

  listarClientes(): void {
    this.clienteService.listar()
      .subscribe(data => {
        this.clientes = data.filter(c => c.estado);
      });
  }

  aplicarFiltros(): void {
    if (!this.searchTerm.trim()) {
      this.facturasMostradas = this.facturas;
      return;
    }

    this.facturasMostradas = this.facturas.filter(f =>
      (f.numeroFactura && f.numeroFactura.toLowerCase().includes(this.searchTerm.toLowerCase()))
    );
  }

  generarNumeroFactura(): void {
    const timestamp = Date.now();
    this.factura.numeroFactura = `FAC-${timestamp}`;
  }

  seleccionarProducto(evento: any): void {
    const productoId = parseInt(evento.target.value);
    const producto = this.productos.find(p => p.idProducto === productoId);
    if (producto) {
      this.productoSeleccionado = producto;
      this.detalleActual.idProducto = productoId;
      this.detalleActual.precioUnitario = producto.valorVenta || producto.precio;
    }
  }

  agregarDetalle(): void {
    if (!this.detalleActual.idProducto || this.detalleActual.cantidad <= 0) {
      alert('Selecciona un producto y una cantidad válida');
      return;
    }

    this.detalleActual.subtotal = this.detalleActual.cantidad * this.detalleActual.precioUnitario;
    this.detalles.push({ ...this.detalleActual });
    this.calcularTotales();
    this.limpiarDetalle();
  }

  eliminarDetalle(index: number): void {
    this.detalles.splice(index, 1);
    this.calcularTotales();
  }

  calcularTotales(): void {
    if (this.detalles.length === 0) {
      this.factura.subtotal = 0;
      this.factura.impuesto = 0;
      this.factura.total = 0;
      return;
    }

    this.factura.subtotal = this.detalles.reduce((sum, d) => sum + d.subtotal, 0);
    this.factura.impuesto = this.factura.subtotal * 0.19; // IVA 19%
    this.factura.total = this.factura.subtotal + (this.factura.impuesto || 0) - (this.factura.descuento || 0);
  }

  limpiarDetalle(): void {
    this.detalleActual = {
      idFactura: 0,
      idProducto: 0,
      cantidad: 1,
      precioUnitario: 0,
      subtotal: 0
    };
    this.productoSeleccionado = null;
  }

  guardar(): void {
    if (!this.factura.idCliente || this.detalles.length === 0) {
      alert('Selecciona un cliente y agrega al menos un producto');
      return;
    }

    if (!this.factura.medioPago) {
      alert('Selecciona un medio de pago');
      return;
    }

    const usuario = this.authService.obtenerUsuario();
    this.factura.idUsuario = usuario?.idUsuario || 0;
    this.factura.estado = 'PAGADA';

    const facturaRequest: Factura = {
      ...this.factura,
      cliente: { idCliente: this.factura.idCliente },
      detalles: undefined
    };

    this.facturaService.crear(facturaRequest)
      .subscribe({
        next: async (facturaCreada) => {
          try {
            const detalleCreaciones = this.detalles.map(detalle => {
              const detalleRequest: DetalleFactura = {
                ...detalle,
                factura: { idFactura: facturaCreada.idFactura! },
                producto: { idProducto: detalle.idProducto }
              };
              return firstValueFrom(this.detalleFacturaService.crear(detalleRequest));
            });

            await Promise.all(detalleCreaciones);
            alert('Factura creada exitosamente');
            this.listarFacturas();
            this.limpiarFormulario();
          } catch (error) {
            console.error('Error al crear detalles de factura:', error);
            alert('Factura creada, pero no se pudieron guardar todos los detalles');
            this.listarFacturas();
          }
        },
        error: (error) => {
          console.error('Error al crear factura:', error);
          alert('Error al crear la factura');
        }
      });
  }

  editar(factura: Factura): void {
    this.factura = {
      ...factura,
      idCliente: factura.idCliente ?? factura.cliente?.idCliente
    };
    this.editando = true;
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  eliminar(id: number | undefined): void {
    if (id && confirm('¿Deseas eliminar esta factura?')) {
      this.facturaService.eliminar(id)
        .subscribe(() => {
          this.listarFacturas();
          alert('Factura eliminada exitosamente');
        });
    }
  }

  limpiarFormulario(): void {
    this.factura = {
      numeroFactura: '',
      subtotal: 0,
      descuento: 0,
      impuesto: 0,
      total: 0,
      medioPago: '',
      idUsuario: 0,
      idCliente: 0,
      estado: 'PAGADA',
      detalles: []
    };
    this.detalles = [];
    this.generarNumeroFactura();
    this.editando = false;
  }

  getNombreProducto(idProducto: number): string {
    return this.productos.find(p => p.idProducto === idProducto)?.nombre || 'N/A';
  }

  getNombreCliente(factura: Factura): string {
    if (factura.cliente && 'nombreCompleto' in factura.cliente && factura.cliente.nombreCompleto) {
      return factura.cliente.nombreCompleto;
    }
    if (factura.idCliente) {
      return this.clientes.find(c => c.idCliente === factura.idCliente)?.nombreCompleto || 'N/A';
    }
    return 'N/A';
  }
}
