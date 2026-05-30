import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RolService } from '../../services/rol.service';
import { PermisoService } from '../../services/permiso.service';
import { RolPermisoService } from '../../services/rol-permiso.service';
import { ToastService } from '../../services/toast.service';
import { Rol } from '../../models/rol';
import { Permiso } from '../../models/permiso';

@Component({
  selector: 'app-roles',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './roles.html',
  styleUrls: ['./roles.scss']
})
export class RolesComponent implements OnInit {

  roles: Rol[] = [];
  permisos: Permiso[] = [];
  mostrarFormulario = false;
  mostrarPermisosFormulario = false;
  editando = false;
  rolActual: Rol = {
    nombreRol: '',
    estado: true
  };
  rolSeleccionado: Rol | null = null;
  permisosDelRol: Permiso[] = [];
  permisosDisponibles: Permiso[] = [];

  constructor(
    private rolService: RolService,
    private permisoService: PermisoService,
    private rolPermisoService: RolPermisoService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.cargarRoles();
    this.cargarPermisos();
  }

  cargarRoles(): void {
    this.rolService.obtenerTodos().subscribe({
      next: (roles) => {
        this.roles = roles;
      },
      error: () => {
        this.toastService.show('Error al cargar roles', 'error');
      }
    });
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
    this.rolActual = {
      nombreRol: '',
      estado: true
    };
  }

  editar(rol: Rol): void {
    this.rolActual = { ...rol };
    this.mostrarFormulario = true;
    this.editando = true;
  }

  guardar(): void {
    if (!this.rolActual.nombreRol) {
      this.toastService.show('Por favor complete todos los campos', 'error');
      return;
    }

    if (this.editando && this.rolActual.idRol) {
      this.rolService.actualizar(this.rolActual.idRol, this.rolActual).subscribe({
        next: () => {
          this.toastService.show('Rol actualizado correctamente', 'success');
          this.cargarRoles();
          this.mostrarFormulario = false;
        },
        error: () => {
          this.toastService.show('Error al actualizar rol', 'error');
        }
      });
    } else {
      this.rolService.crear(this.rolActual).subscribe({
        next: () => {
          this.toastService.show('Rol creado correctamente', 'success');
          this.cargarRoles();
          this.mostrarFormulario = false;
        },
        error: () => {
          this.toastService.show('Error al crear rol', 'error');
        }
      });
    }
  }

  eliminar(id: number | undefined): void {
    if (!id) return;
    
    if (confirm('¿Está seguro de que desea eliminar este rol?')) {
      this.rolService.eliminar(id).subscribe({
        next: () => {
          this.toastService.show('Rol eliminado correctamente', 'success');
          this.cargarRoles();
        },
        error: () => {
          this.toastService.show('Error al eliminar rol', 'error');
        }
      });
    }
  }

  abrirGestorPermisos(rol: Rol): void {
    this.rolSeleccionado = rol;
    this.mostrarPermisosFormulario = true;
    this.cargarPermisosDelRol();
  }

  cargarPermisosDelRol(): void {
    if (!this.rolSeleccionado?.idRol) return;

    this.rolPermisoService.obtenerPermisosDeRol(this.rolSeleccionado.idRol).subscribe({
      next: (asignaciones) => {
        const idsAsignados = asignaciones.map(a => a.idPermiso);
        this.permisosDelRol = this.permisos.filter(p => p.idPermiso !== undefined && idsAsignados.includes(p.idPermiso));
        this.permisosDisponibles = this.permisos.filter(p => p.idPermiso !== undefined && !idsAsignados.includes(p.idPermiso));
      },
      error: () => {
        this.toastService.show('Error al cargar permisos del rol', 'error');
      }
    });
  }

  asignarPermiso(permiso: Permiso): void {
    if (!this.rolSeleccionado?.idRol || !permiso.idPermiso) return;

    this.rolPermisoService.asignarPermiso(this.rolSeleccionado.idRol, permiso.idPermiso).subscribe({
      next: () => {
        this.toastService.show('Permiso asignado correctamente', 'success');
        this.cargarPermisosDelRol();
      },
      error: () => {
        this.toastService.show('Error al asignar permiso', 'error');
      }
    });
  }

  removerPermiso(permiso: Permiso): void {
    if (!this.rolSeleccionado?.idRol || !permiso.idPermiso) return;

    this.rolPermisoService.removerPermiso(this.rolSeleccionado.idRol, permiso.idPermiso).subscribe({
      next: () => {
        this.toastService.show('Permiso removido correctamente', 'success');
        this.cargarPermisosDelRol();
      },
      error: () => {
        this.toastService.show('Error al remover permiso', 'error');
      }
    });
  }

  cancelar(): void {
    this.mostrarFormulario = false;
  }

  cerrarGestorPermisos(): void {
    this.mostrarPermisosFormulario = false;
    this.rolSeleccionado = null;
  }
}
