import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { ModalService, ModalConfig } from '../../services/modal.service';
import { LoginModalComponent } from '../login/login-modal';
import { RegisterModalComponent } from '../register/register-modal';

@Component({
  selector: 'app-modal-container',
  standalone: true,
  imports: [CommonModule, LoginModalComponent, RegisterModalComponent],
  template: `
    <ng-container *ngIf="modalConfig$ | async as modal">
      <app-login-modal *ngIf="modal.isOpen && modal.type === 'login'"></app-login-modal>
      <app-register-modal *ngIf="modal.isOpen && modal.type === 'register'"></app-register-modal>
    </ng-container>
  `
})
export class ModalContainerComponent implements OnInit {
  modalConfig$: Observable<ModalConfig>;

  constructor(private modalService: ModalService) {
    this.modalConfig$ = this.modalService.modal$;
  }

  ngOnInit(): void {}
}
