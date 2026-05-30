import { Component } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { NgIf } from '@angular/common';
import { AuthService } from './services/auth.service';
import { ToastService } from './services/toast.service';
import { ModalService } from './services/modal.service';
import { ToastComponent } from './toast/toast';
import { ModalContainerComponent } from './pages/modal-container/modal-container';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, NgIf, ToastComponent, ModalContainerComponent],
  templateUrl: './app.html',
  styleUrls: ['./app.scss']
})
export class App {
  constructor(
    public authService: AuthService,
    private toastService: ToastService,
    private router: Router,
    private modalService: ModalService
  ) {}

  logout(): void {
    this.authService.logout();
    this.toastService.show('Sesión cerrada correctamente.', 'info');
    this.router.navigate(['/']);
  }

  openLoginModal(): void {
    this.modalService.openLoginModal();
  }

  openRegisterModal(): void {
    this.modalService.openRegisterModal();
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }
}
