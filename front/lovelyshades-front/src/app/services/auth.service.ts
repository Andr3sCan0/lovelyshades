import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginRequest, LoginResponse, RegisterRequest, Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private api = 'http://localhost:8080/api/auth';
  private tokenKey = 'token';
  private userKey = 'user';

  constructor(private http: HttpClient) {}

  login(data: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.api}/login`, data);
  }

  register(data: RegisterRequest): Observable<Usuario> {
    return this.http.post<Usuario>(`${this.api}/register`, data);
  }

  obtenerNombre(): string | null {
    return this.obtenerNombreUsuario();
  }

  private get localStorageAvailable(): boolean {
    return typeof localStorage !== 'undefined';
  }

  guardarToken(token: string, user?: Usuario): void {
    if (!this.localStorageAvailable) {
      return;
    }

    localStorage.setItem(this.tokenKey, token);

    if (user) {
      localStorage.setItem(this.userKey, JSON.stringify(user));
    }
  }

  guardarUsuario(user: Usuario): void {
    if (!this.localStorageAvailable) {
      return;
    }

    localStorage.setItem(this.userKey, JSON.stringify(user));
  }

  obtenerToken(): string | null {
    return this.localStorageAvailable ? localStorage.getItem(this.tokenKey) : null;
  }

  obtenerUsuario(): Usuario | null {
    if (!this.localStorageAvailable) {
      return null;
    }

    const json = localStorage.getItem(this.userKey);
    return json ? JSON.parse(json) : null;
  }

  obtenerNombreUsuario(): string | null {
    const user = this.obtenerUsuario();
    return user?.nombreUsuario ?? user?.email ?? null;
  }

  isLoggedIn(): boolean {
    return !!this.obtenerToken();
  }

  isAdmin(): boolean {
    const roleName = this.obtenerUsuario()?.rol?.nombreRol;
    return !!roleName && roleName.toLowerCase().includes('admin');
  }

  logout(): void {
    if (!this.localStorageAvailable) {
      return;
    }

    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.userKey);
  }
}