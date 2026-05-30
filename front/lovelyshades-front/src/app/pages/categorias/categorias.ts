import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Categoria } from '../../models/categoria';
import { CategoriaService } from '../../services/categoria.service';

@Component({
  selector: 'app-categorias',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './categorias.html',
  styleUrl: './categorias.scss'
})
export class CategoriasComponent implements OnInit {

  categorias: Categoria[] = [];
  categoriasMostradas: Categoria[] = [];

  categoria: Categoria = {
    nombreCategoria: '',
    descripcion: '',
    estado: true
  };

  editando = false;
  searchTerm: string = '';

  constructor(private categoriaService: CategoriaService) {}

  ngOnInit(): void {
    this.listarCategorias();
  }

  listarCategorias(): void {
    this.categoriaService.listar()
      .subscribe(data => {
        this.categorias = data;
        this.aplicarFiltros();
      });
  }

  aplicarFiltros(): void {
    if (!this.searchTerm.trim()) {
      this.categoriasMostradas = this.categorias;
      return;
    }

    this.categoriasMostradas = this.categorias.filter(c => 
      c.nombreCategoria.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      (c.descripcion && c.descripcion.toLowerCase().includes(this.searchTerm.toLowerCase()))
    );
  }

  guardar(): void {
    if (!this.categoria.nombreCategoria.trim()) {
      alert('El nombre de la categoria es obligatorio');
      return;
    }

    if (this.editando) {
      this.categoriaService.actualizar(this.categoria)
        .subscribe(() => {
          this.listarCategorias();
          this.limpiarFormulario();
          alert('Categoria actualizada exitosamente');
        });
    } else {
      this.categoriaService.crear(this.categoria)
        .subscribe(() => {
          this.listarCategorias();
          this.limpiarFormulario();
          alert('Categoria creada exitosamente');
        });
    }
  }

  editar(categoria: Categoria): void {
    this.categoria = { ...categoria };
    this.editando = true;
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  eliminar(id: number | undefined): void {
    if (!id) return;
    
    if (confirm('¿Está seguro de que desea eliminar esta categoria?')) {
      this.categoriaService.eliminar(id)
        .subscribe(() => {
          this.listarCategorias();
          alert('Categoria eliminada exitosamente');
        });
    }
  }

  limpiarFormulario(): void {
    this.categoria = {
      nombreCategoria: '',
      descripcion: '',
      estado: true
    };
    this.editando = false;
  }

  onBusquedaChange(): void {
    this.aplicarFiltros();
  }
}
