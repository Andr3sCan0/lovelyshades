package com.lovelyshades.controller;

import com.lovelyshades.entity.Categoria;
import com.lovelyshades.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categorias", description = "Gestion de categorias")
@RestController
@RequestMapping("/api/categorias")
@PreAuthorize("hasRole('ADMIN')")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(
            summary = "Crear categoria",
            description = "Registra una nueva categoria",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "nombreCategoria": "Maquillaje",
                                      "descripcion": "Productos para maquillaje",
                                      "estado": true
                                    }
                                    """
                            )
                    )
            )
    )
    @PostMapping
    public Categoria crear(@Valid @org.springframework.web.bind.annotation.RequestBody Categoria categoria) {
        return categoriaService.crear(categoria);
    }

    @Operation(summary = "Listar categorias", description = "Obtiene todas las categorias registradas")
    @GetMapping
    public List<Categoria> listar() {
        return categoriaService.listar();
    }

    @Operation(summary = "Buscar categoria por ID", description = "Obtiene una categoria usando su identificador")
    @GetMapping("/{id}")
    public Categoria obtenerPorId(@PathVariable Integer id) {
        return categoriaService.obtenerPorId(id);
    }

    @Operation(
            summary = "Actualizar categoria",
            description = "Actualiza la informacion de una categoria existente",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "nombreCategoria": "Maquillaje Premium",
                                      "descripcion": "Productos premium para maquillaje",
                                      "estado": true
                                    }
                                    """
                            )
                    )
            )
    )
    @PutMapping("/{id}")
    public Categoria actualizar(
            @PathVariable Integer id,
            @Valid @org.springframework.web.bind.annotation.RequestBody Categoria categoria
    ) {
        return categoriaService.actualizar(id, categoria);
    }

    @Operation(summary = "Eliminar categoria", description = "Elimina una categoria por su identificador")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        categoriaService.eliminar(id);
    }
}
