import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Marca } from '../../models/marca';
import { MarcaService } from '../../services/marca.service';

@Component({
  selector: 'app-marcas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './marcas.html',
  styleUrl: './marcas.scss'
})
export class MarcasComponent implements OnInit {

  marcas: Marca[] = [];
  marcasMostradas: Marca[] = [];

  marca: Marca = {
    nombreMarca: '',
    estado: true
  };

  editando = false;
  searchTerm: string = '';

  constructor(private marcaService: MarcaService) {}

  ngOnInit(): void {
    this.listarMarcas();
  }

  listarMarcas(): void {
    this.marcaService.listar()
      .subscribe(data => {
        this.marcas = data;
        this.aplicarFiltros();
      });
  }

  aplicarFiltros(): void {
    if (!this.searchTerm.trim()) {
      this.marcasMostradas = this.marcas;
      return;
    }

    this.marcasMostradas = this.marcas.filter(m => 
      m.nombreMarca.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  guardar(): void {
    if (!this.marca.nombreMarca.trim()) {
      alert('El nombre de la marca es obligatorio');
      return;
    }

    if (this.editando) {
      this.marcaService.actualizar(this.marca)
        .subscribe(() => {
          this.listarMarcas();
          this.limpiarFormulario();
          alert('Marca actualizada exitosamente');
        });
    } else {
      this.marcaService.crear(this.marca)
        .subscribe(() => {
          this.listarMarcas();
          this.limpiarFormulario();
          alert('Marca creada exitosamente');
        });
    }
  }

  editar(marca: Marca): void {
    this.marca = { ...marca };
    this.editando = true;
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  eliminar(id: number | undefined): void {
    if (!id) return;
    
    if (confirm('¿Está seguro de que desea eliminar esta marca?')) {
      this.marcaService.eliminar(id)
        .subscribe(() => {
          this.listarMarcas();
          alert('Marca eliminada exitosamente');
        });
    }
  }

  limpiarFormulario(): void {
    this.marca = {
      nombreMarca: '',
      estado: true
    };
    this.editando = false;
  }

  onBusquedaChange(): void {
    this.aplicarFiltros();
  }
}
