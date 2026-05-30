import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { ModalService } from '../../services/modal.service';
import { ToastService } from '../../services/toast.service';
import { RegisterRequest } from '../../models/usuario';

@Component({
  selector: 'app-register-modal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="modal-overlay" (click)="cerrar()">
      <div class="modal-content" (click)="$event.stopPropagation()">
        <div class="modal-header">
          <h2>Crear Cuenta</h2>
          <button class="close-btn" (click)="cerrar()">✕</button>
        </div>

        <form #registerForm="ngForm" (ngSubmit)="register()" class="auth-form">
          <label>
            Nombre
            <input
              type="text"
              [(ngModel)]="nombre"
              name="nombre"
              placeholder="Tu nombre"
              required
              #nombreField="ngModel"
            />
            <div class="field-error" *ngIf="nombreField.invalid && nombreField.touched">
              <span>El nombre es obligatorio.</span>
            </div>
          </label>

          <label>
            Correo
            <input
              type="email"
              [(ngModel)]="email"
              name="email"
              placeholder="correo@ejemplo.com"
              required
              email
              #emailField="ngModel"
            />
            <div class="field-error" *ngIf="emailField.invalid && emailField.touched">
              <span *ngIf="emailField.errors?.['required']">El correo es obligatorio.</span>
              <span *ngIf="emailField.errors?.['email']">Ingresa un correo válido.</span>
            </div>
          </label>

          <label>
            Contraseña
            <input
              type="password"
              [(ngModel)]="password"
              name="password"
              placeholder="••••••••"
              required
              minlength="6"
              #passwordField="ngModel"
            />
            <div class="field-error" *ngIf="passwordField.invalid && passwordField.touched">
              <span *ngIf="passwordField.errors?.['required']">La contraseña es obligatoria.</span>
              <span *ngIf="passwordField.errors?.['minlength']">Debe tener al menos 6 caracteres.</span>
            </div>
          </label>

          <label>
            Confirmar contraseña
            <input
              type="password"
              [(ngModel)]="confirmPassword"
              name="confirmPassword"
              placeholder="Repite la contraseña"
              required
              minlength="6"
              #confirmPasswordField="ngModel"
            />
            <div class="field-error" *ngIf="confirmPasswordField.invalid && confirmPasswordField.touched">
              <span *ngIf="confirmPasswordField.errors?.['required']">Confirma la contraseña.</span>
              <span *ngIf="confirmPasswordField.errors?.['minlength']">Debe tener al menos 6 caracteres.</span>
            </div>
          </label>

          <button type="submit" class="submit-btn" [disabled]="registerForm.invalid || loading || !passwordMatch">
            {{ loading ? 'Registrando...' : 'Crear cuenta' }}
          </button>

          <div class="password-match" *ngIf="confirmPassword && !passwordMatch" style="color: #ef4444; font-size: 0.875rem;">
            Las contraseñas no coinciden
          </div>
        </form>

        <div class="modal-footer">
          <span>¿Ya tienes cuenta?</span>
          <button class="link-btn" (click)="abrirLogin()">Inicia sesión</button>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .modal-overlay {
      position: fixed;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background: rgba(0, 0, 0, 0.5);
      display: flex;
      justify-content: center;
      align-items: center;
      z-index: 1000;
      overflow-y: auto;
    }

    .modal-content {
      background: white;
      border-radius: 12px;
      box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
      width: 90%;
      max-width: 400px;
      padding: 0;
      animation: slideIn 0.3s ease-out;
      margin: 20px 0;
    }

    @keyframes slideIn {
      from {
        transform: translateY(-50px);
        opacity: 0;
      }
      to {
        transform: translateY(0);
        opacity: 1;
      }
    }

    .modal-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 20px;
      border-bottom: 1px solid #eee;
    }

    .modal-header h2 {
      margin: 0;
      font-size: 1.5rem;
      color: #333;
    }

    .close-btn {
      background: none;
      border: none;
      font-size: 1.5rem;
      cursor: pointer;
      color: #999;
      transition: color 0.2s;
    }

    .close-btn:hover {
      color: #333;
    }

    .auth-form {
      padding: 20px;
      display: flex;
      flex-direction: column;
      gap: 15px;
    }

    label {
      display: flex;
      flex-direction: column;
      gap: 8px;
      font-weight: 500;
      color: #333;
    }

    input {
      padding: 10px 12px;
      border: 1px solid #ddd;
      border-radius: 6px;
      font-size: 1rem;
      transition: border-color 0.2s;
    }

    input:focus {
      outline: none;
      border-color: #ec4899;
      box-shadow: 0 0 0 3px rgba(236, 72, 153, 0.1);
    }

    .field-error {
      color: #ef4444;
      font-size: 0.875rem;
    }

    .submit-btn {
      background: #ec4899;
      color: white;
      padding: 12px;
      border: none;
      border-radius: 6px;
      font-size: 1rem;
      font-weight: 600;
      cursor: pointer;
      transition: background 0.2s;
      margin-top: 10px;
    }

    .submit-btn:hover:not(:disabled) {
      background: #db2777;
    }

    .submit-btn:disabled {
      opacity: 0.6;
      cursor: not-allowed;
    }

    .password-match {
      text-align: center;
    }

    .modal-footer {
      padding: 15px 20px;
      border-top: 1px solid #eee;
      text-align: center;
      font-size: 0.9rem;
    }

    .link-btn {
      background: none;
      border: none;
      color: #ec4899;
      cursor: pointer;
      text-decoration: underline;
      font-weight: 600;
      margin-left: 5px;
    }

    .link-btn:hover {
      text-decoration: none;
    }
  `]
})
export class RegisterModalComponent {
  nombre = '';
  email = '';
  password = '';
  confirmPassword = '';
  loading = false;

  get passwordMatch(): boolean {
    return this.password === this.confirmPassword;
  }

  constructor(
    private authService: AuthService,
    private modalService: ModalService,
    private toastService: ToastService
  ) {}

  register(): void {
    if (!this.passwordMatch) {
      this.toastService.show('Las contraseñas no coinciden', 'error');
      return;
    }

    this.loading = true;

    const body: RegisterRequest = {
      nombreUsuario: this.nombre,
      email: this.email,
      contrasenaHash: this.password
    };

    this.authService.register(body).subscribe({
      next: (response) => {
        this.loading = false;
        this.authService.guardarUsuario(response);
        this.toastService.show('Registro exitoso.', 'success');
        this.modalService.closeModal();
      },
      error: () => {
        this.loading = false;
        this.toastService.show('Hubo un error al registrar el usuario. Verifica los datos e intenta de nuevo.', 'error');
      }
    });
  }

  cerrar(): void {
    this.modalService.closeModal();
  }

  abrirLogin(): void {
    this.modalService.openLoginModal();
  }
}
