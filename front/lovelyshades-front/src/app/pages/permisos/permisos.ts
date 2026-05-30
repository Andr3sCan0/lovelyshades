import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PermisoService } from '../../services/permiso.service';
import { ToastService } from '../../services/toast.service';
import { Permiso } from '../../models/permiso';

@Component({
  selector: 'app-permisos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './permisos.html'
})
export class PermisosComponent implements OnInit {

  permisos: Permiso[] = [];
  mostrarFormulario = false;
  editando = false;
  permisoActual: Permiso = {
    nombrePermiso: '',
    descripcion: '',
    estado: true
  };

  constructor(
    private permisoService: PermisoService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.cargarPermisos();
  }

  cargarPermisos(): void {
    this.permisoService.obtenerTodos().subscribe({
      next: (permisos) => {
        this.permisos = permisos;
      },
      error: () => {
        this.toastService.show('Error al cargar permisos', 'error');
      }
    });
  }

  abrirFormulario(): void {
    this.mostrarFormulario = true;
    this.editando = false;
    this.permisoActual = {
      nombrePermiso: '',
      descripcion: '',
      estado: true
    };
  }

  editar(permiso: Permiso): void {
    this.permisoActual = { ...permiso };
    this.mostrarFormulario = true;
    this.editando = true;
  }

  guardar(): void {
    if (!this.permisoActual.nombrePermiso) {
      this.toastService.show('Por favor complete todos los campos', 'error');
      return;
    }

    if (this.editando && this.permisoActual.idPermiso) {
      this.permisoService.actualizar(this.permisoActual.idPermiso, this.permisoActual).subscribe({
        next: () => {
          this.toastService.show('Permiso actualizado correctamente', 'success');
          this.cargarPermisos();
          this.mostrarFormulario = false;
        },
        error: () => {
          this.toastService.show('Error al actualizar permiso', 'error');
        }
      });
    } else {
      this.permisoService.crear(this.permisoActual).subscribe({
        next: () => {
          this.toastService.show('Permiso creado correctamente', 'success');
          this.cargarPermisos();
          this.mostrarFormulario = false;
        },
        error: () => {
          this.toastService.show('Error al crear permiso', 'error');
        }
      });
    }
  }

  eliminar(id: number | undefined): void {
    if (!id) return;
    
    if (confirm('¿Está seguro de que desea eliminar este permiso?')) {
      this.permisoService.eliminar(id).subscribe({
        next: () => {
          this.toastService.show('Permiso eliminado correctamente', 'success');
          this.cargarPermisos();
        },
        error: () => {
          this.toastService.show('Error al eliminar permiso', 'error');
        }
      });
    }
  }

  cancelar(): void {
    this.mostrarFormulario = false;
  }
}
