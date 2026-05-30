import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from '../../services/usuario.service';
import { RolService } from '../../services/rol.service';
import { ToastService } from '../../services/toast.service';
import { Usuario } from '../../models/usuario';
import { Rol } from '../../models/rol';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './usuarios.html',
  styleUrls: ['./usuarios.scss']
})
export class UsuariosComponent implements OnInit {

  usuarios: Usuario[] = [];
  roles: Rol[] = [];
  mostrarFormulario = false;
  editando = false;
  usuarioActual: Usuario = {
    nombreUsuario: '',
    email: '',
    contrasenaHash: '',
    rol: {} as Rol,
    estado: true
  };

  constructor(
    private usuarioService: UsuarioService,
    private rolService: RolService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.cargarUsuarios();
    this.cargarRoles();
  }

  cargarUsuarios(): void {
    this.usuarioService.obtenerTodos().subscribe({
      next: (usuarios) => {
        this.usuarios = usuarios;
      },
      error: (error) => {
        this.toastService.show('Error al cargar usuarios', 'error');
      }
    });
  }

  cargarRoles(): void {
    this.rolService.obtenerTodos().subscribe({
      next: (roles) => {
        this.roles = roles;
      },
      error: (error) => {
        this.toastService.show('Error al cargar roles', 'error');
      }
    });
  }

  abrirFormulario(): void {
    this.mostrarFormulario = true;
    this.editando = false;
    this.usuarioActual = {
      nombreUsuario: '',
      email: '',
      contrasenaHash: '',
      rol: {} as Rol,
      estado: true
    };
  }

  editar(usuario: Usuario): void {
    this.usuarioActual = { ...usuario };
    this.mostrarFormulario = true;
    this.editando = true;
  }

  guardar(): void {
    if (!this.usuarioActual.nombreUsuario || !this.usuarioActual.email || !this.usuarioActual.rol?.idRol) {
      this.toastService.show('Por favor complete todos los campos', 'error');
      return;
    }

    if (this.editando && this.usuarioActual.idUsuario) {
      this.usuarioService.actualizar(this.usuarioActual.idUsuario, this.usuarioActual).subscribe({
        next: () => {
          this.toastService.show('Usuario actualizado correctamente', 'success');
          this.cargarUsuarios();
          this.mostrarFormulario = false;
        },
        error: () => {
          this.toastService.show('Error al actualizar usuario', 'error');
        }
      });
    } else {
      this.usuarioService.crear(this.usuarioActual).subscribe({
        next: () => {
          this.toastService.show('Usuario creado correctamente', 'success');
          this.cargarUsuarios();
          this.mostrarFormulario = false;
        },
        error: () => {
          this.toastService.show('Error al crear usuario', 'error');
        }
      });
    }
  }

  eliminar(id: number | undefined): void {
    if (!id) return;
    
    if (confirm('¿Está seguro de que desea eliminar este usuario?')) {
      this.usuarioService.eliminar(id).subscribe({
        next: () => {
          this.toastService.show('Usuario eliminado correctamente', 'success');
          this.cargarUsuarios();
        },
        error: () => {
          this.toastService.show('Error al eliminar usuario', 'error');
        }
      });
    }
  }

  cancelar(): void {
    this.mostrarFormulario = false;
  }
}
