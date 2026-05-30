import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../models/usuario';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrls: ['./register.scss']
})
export class RegisterComponent {
  nombre = '';
  email = '';
  password = '';
  confirmPassword = '';

  constructor(
    private authService: AuthService,
    private toastService: ToastService,
    private router: Router
  ) {}

  register(): void {
    if (this.password !== this.confirmPassword) {
      alert('Las contraseñas no coinciden');
      return;
    }

    const body: RegisterRequest = {
      nombreUsuario: this.nombre,
      email: this.email,
      contrasenaHash: this.password
    };

    this.authService.register(body).subscribe({
      next: (response) => {
        this.authService.guardarUsuario(response);
        this.toastService.show('Registro exitoso.', 'success');
        this.router.navigate(['/']);
      },
      error: () => {
        this.toastService.show('Hubo un error al registrar el usuario. Verifica los datos e intenta de nuevo.', 'error');
      }
    });
  }
}
