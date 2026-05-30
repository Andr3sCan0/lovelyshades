import { Routes } from '@angular/router';

import { HomeComponent } from './pages/home/home';
import { ClientesComponent } from './pages/clientes/clientes';
import { ProductosComponent } from './pages/productos/productos';
import { ProductosJpaComponent } from './pages/productos-jpa/productos-jpa';
import { ProfileComponent } from './pages/profile/profile';
import { RolesComponent } from './pages/roles/roles';
import { UsuariosEditarComponent } from './pages/usuarios-editar/usuarios-editar';
import { CategoriasComponent } from './pages/categorias/categorias';
import { MarcasComponent } from './pages/marcas/marcas';
import { FacturasComponent } from './pages/facturas/facturas';
import { InventarioComponent } from './pages/inventario/inventario';
import { authGuard } from './guards/auth.guard';
import { redirectAuthGuard } from './guards/redirect-auth.guard';
import { adminGuard } from './guards/admin.guard';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'login',
    redirectTo: '',
    pathMatch: 'full'
  },
  {
    path: 'registro',
    redirectTo: '',
    pathMatch: 'full'
  },
  {
    path: 'perfil',
    component: ProfileComponent,
    canActivate: [authGuard]
  },
  {
    path: 'clientes',
    component: ClientesComponent,
    canActivate: [authGuard]
  },
  {
    path: 'productos',
    component: ProductosComponent,
    canActivate: [authGuard]
  },
  {
    path: 'productos-jpa',
    component: ProductosJpaComponent,
    canActivate: [authGuard]
  },
  {
    path: 'admin/roles',
    component: RolesComponent,
    canActivate: [authGuard, adminGuard]
  },
  {
    path: 'admin/usuarios-editar',
    component: UsuariosEditarComponent,
    canActivate: [authGuard, adminGuard]
  },
  {
    path: 'admin/categorias',
    component: CategoriasComponent,
    canActivate: [authGuard, adminGuard]
  },
  {
    path: 'admin/marcas',
    component: MarcasComponent,
    canActivate: [authGuard, adminGuard]
  },
  {
    path: 'facturas',
    component: FacturasComponent,
    canActivate: [authGuard]
  },
  {
    path: 'inventario',
    component: InventarioComponent,
    canActivate: [authGuard]
  }
];