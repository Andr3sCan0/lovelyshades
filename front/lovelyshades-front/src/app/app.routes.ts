import { Routes } from '@angular/router';

import { HomeComponent } from './pages/home/home';

import { ClientesComponent } from './pages/clientes/clientes';
import { ProductosComponent } from './pages/productos/productos';
import { ProductosJpaComponent } from './pages/productos-jpa/productos-jpa';

export const routes: Routes = [

 {
    path: '',
    component: HomeComponent
  },

  {
    path: 'clientes',
    component: ClientesComponent
  },
  
  {
    path: 'productos',
    component: ProductosComponent
  },

  {
    path: 'productos-jpa',
    component: ProductosJpaComponent
  }

];