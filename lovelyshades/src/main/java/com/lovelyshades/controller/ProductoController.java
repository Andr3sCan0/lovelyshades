package com.lovelyshades.controller;

import com.lovelyshades.entity.Producto;
import com.lovelyshades.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;

@Tag(name = "Productos", description = "Gestión de productos")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(
        summary = "Crear producto",
        description = "Registra un nuevo producto en el sistema",
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        examples = @ExampleObject(
                                value = """
                                {
                                  "nombre": "Gafas Classic",
                                  "descripcion": "Gafas de sol negras",
                                  "precio": 150000,
                                  "stock": 10,
                                  "estado": true
                                }
                                """
                        )
                )
        )
)
@PostMapping
public Producto crear(@Valid @org.springframework.web.bind.annotation.RequestBody Producto producto) {
    return productoService.crear(producto);
}

    @Operation(
            summary = "Listar productos",
            description = "Obtiene todos los productos registrados"
    )
    @GetMapping
    public List<Producto> listar() {
        return productoService.listar();
    }

    @Operation(
            summary = "Buscar producto por ID",
            description = "Obtiene un producto usando su identificador"
    )
    @GetMapping("/{id}")
    public Producto obtenerPorId(@PathVariable Integer id) {
        return productoService.obtenerPorId(id);
    }

    @Operation(
            summary = "Actualizar producto",
            description = "Actualiza la información de un producto existente"
    )
    @PutMapping("/{id}")
    public Producto actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Producto producto
    ) {
        return productoService.actualizar(id, producto);
    }

    @Operation(
            summary = "Eliminar producto",
            description = "Elimina un producto por su identificador"
    )
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        productoService.eliminar(id);
    }
}