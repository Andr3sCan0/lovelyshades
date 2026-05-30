import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { ToastService } from '../../services/toast.service';
import { LoginRequest } from '../../models/usuario';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrls: ['./login.scss']
})
export class LoginComponent {

  email = '';
  password = '';
  loading = false;

  constructor(
    private authService: AuthService,
    private toastService: ToastService,
    private router: Router
  ) {}

  login(): void {
    if (!this.email || !this.password) {
      this.toastService.show('Por favor complete todos los campos.', 'error');
      return;
    }

    this.loading = true;

    const loginRequest: LoginRequest = {
      email: this.email,
      password: this.password
    };

    this.authService.login(loginRequest).subscribe({
      next: (response) => {
        this.loading = false;
        
        if (!response.token || !response.usuario) {
          this.toastService.show('Respuesta inválida del servidor.', 'error');
          return;
        }

        this.authService.guardarToken(response.token, response.usuario);
        this.toastService.show('Inicio de sesión exitoso.', 'success');
        this.router.navigate(['/']);
      },
      error: (error) => {
        this.loading = false;
        const errorMsg = error?.error?.message || 'Credenciales inválidas.';
        this.toastService.show(errorMsg, 'error');
      }
    });
  }
}