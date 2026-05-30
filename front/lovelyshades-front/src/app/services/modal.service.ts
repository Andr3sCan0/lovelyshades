import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface ModalConfig {
  type: 'login' | 'register' | 'custom';
  isOpen: boolean;
  data?: any;
}

@Injectable({
  providedIn: 'root'
})
export class ModalService {
  private modalSubject = new BehaviorSubject<ModalConfig>({
    type: 'login',
    isOpen: false
  });

  public modal$ = this.modalSubject.asObservable();

  openLoginModal(): void {
    this.modalSubject.next({ type: 'login', isOpen: true });
  }

  openRegisterModal(): void {
    this.modalSubject.next({ type: 'register', isOpen: true });
  }

  closeModal(): void {
    this.modalSubject.next({ type: 'login', isOpen: false });
  }

  getCurrentModal(): ModalConfig {
    return this.modalSubject.value;
  }
}
