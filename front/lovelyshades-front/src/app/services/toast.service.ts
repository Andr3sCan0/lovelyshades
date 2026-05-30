import { Injectable, signal } from '@angular/core';

export type ToastType = 'success' | 'error' | 'info';

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  message = signal<string | null>(null);
  type = signal<ToastType>('info');

  show(message: string, type: ToastType = 'success'): void {
    this.message.set(message);
    this.type.set(type);

    setTimeout(() => this.clear(), 3000);
  }

  clear(): void {
    this.message.set(null);
  }
}
