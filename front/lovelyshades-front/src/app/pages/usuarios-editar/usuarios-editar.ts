import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from '../../services/usuario.service';
import { RolService } from '../../services/rol.service';
import { ToastService } from '../../services/toast.service';
import { Usuario } from '../../models/usuario';
import { Rol } from '../../models/rol';

@Component({
  selector: 'app-usuarios-editar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './usuarios-editar.html',
  styleUrls: ['./usuarios-editar.scss']
})
export class UsuariosEditarComponent implements OnInit {
  usuarios: Usuario[] = [];
  roles: Rol[] = [];
  mostrarFormulario = false;
  usuarioSeleccionado: Usuario | null = null;
  nuevoRol: number | null = null;

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
      error: () => {
        this.toastService.show('Error al cargar usuarios', 'error');
      }
    });
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

  seleccionarUsuario(usuario: Usuario): void {
    this.usuarioSeleccionado = usuario;
    this.nuevoRol = usuario.rol?.idRol || null;
    this.mostrarFormulario = true;
  }

  guardarCambios(): void {
    if (!this.usuarioSeleccionado || !this.nuevoRol) {
      this.toastService.show('Selecciona un rol válido', 'error');
      return;
    }

    const rolSeleccionado = this.roles.find(r => r.idRol === this.nuevoRol);
    if (!rolSeleccionado) {
      this.toastService.show('Rol no encontrado', 'error');
      return;
    }

    const usuarioActualizado: Usuario = {
      ...this.usuarioSeleccionado,
      rol: rolSeleccionado
    };

    if (this.usuarioSeleccionado.idUsuario) {
      this.usuarioService.actualizar(this.usuarioSeleccionado.idUsuario, usuarioActualizado).subscribe({
        next: () => {
          this.toastService.show('Rol actualizado correctamente', 'success');
          this.cargarUsuarios();
          this.cerrarFormulario();
        },
        error: () => {
          this.toastService.show('Error al actualizar usuario', 'error');
        }
      });
    }
  }

  cerrarFormulario(): void {
    this.mostrarFormulario = false;
    this.usuarioSeleccionado = null;
    this.nuevoRol = null;
  }
}
