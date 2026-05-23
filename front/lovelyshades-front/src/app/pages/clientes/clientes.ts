import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Cliente } from '../../models/cliente';
import { ClienteService } from '../../services/cliente.service';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './clientes.html',
  styleUrls: ['./clientes.scss']
})
export class ClientesComponent implements OnInit {

  clientes: Cliente[] = [];
  clientesMostrados: Cliente[] = [];

  cliente: Cliente = {
    nombre: '',
    email: '',
    telefono: ''
  };

  editando = false;
  searchTerm: string = '';

  constructor(private clienteService: ClienteService) {}

  ngOnInit(): void {
    this.listarClientes();
  }

  listarClientes(): void {
    this.clienteService.listar()
      .subscribe(data => {
        this.clientes = data;
        this.aplicarFiltros();
      });
  }

  aplicarFiltros(): void {
    if (!this.searchTerm.trim()) {
      this.clientesMostrados = this.clientes;
      return;
    }

    this.clientesMostrados = this.clientes.filter(c => {
      const searchLower = this.searchTerm.toLowerCase();
      return c.nombre.toLowerCase().includes(searchLower) ||
             c.email.toLowerCase().includes(searchLower) ||
             c.telefono.includes(this.searchTerm);
    });
  }

  guardar(): void {
    if (
      !this.cliente.nombre.trim() ||
      !this.cliente.email.trim() ||
      !this.cliente.telefono.trim()
    ) {
      alert('Todos los campos son obligatorios');
      return;
    }

    if (this.editando) {
      this.clienteService.actualizar(this.cliente)
        .subscribe(() => {
          this.listarClientes();
          this.limpiarFormulario();
          alert('Cliente actualizado exitosamente');
        });
    } else {
      this.clienteService.crear(this.cliente)
        .subscribe(() => {
          this.listarClientes();
          this.limpiarFormulario();
          alert('Cliente creado exitosamente');
        });
    }
  }

  editar(cliente: Cliente): void {
    this.cliente = { ...cliente };
    this.editando = true;
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  eliminar(id: number | undefined): void {
    if (!id) return;
    
    if (confirm('¿Está seguro de que desea eliminar este cliente?')) {
      this.clienteService.eliminar(id)
        .subscribe(() => {
          this.listarClientes();
          alert('Cliente eliminado exitosamente');
        });
    }
  }

  limpiarFormulario(): void {
    this.cliente = {
      nombre: '',
      email: '',
      telefono: ''
    };
    this.editando = false;
  }

  onBusquedaChange(): void {
    this.aplicarFiltros();
  }
}