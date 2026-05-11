package com.lovelyshades.controller;

import com.lovelyshades.model.Producto;
import com.lovelyshades.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "CRUD de productos usando SQL nativo (JDBC)")
public class ProductoController {

    // Inyectamos la INTERFAZ, no la clase concreta (SOLID)
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los productos")
    public List<Producto> listar() {
        return productoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    public Producto obtener(@PathVariable int id) {
        return productoService.buscarPorId(id).orElse(null);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto")
    public Producto crear(@RequestBody Producto producto) {
        return productoService.guardar(producto);
    }

    @PutMapping
    @Operation(summary = "Actualizar un producto existente")
    public Producto actualizar(@RequestBody Producto producto) {
        productoService.actualizar(producto);
        return producto;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto por ID")
    public String eliminar(@PathVariable int id) {
        productoService.eliminar(id);
        return "Producto eliminado";
    }

    @GetMapping("/bajo-stock")
    @Operation(summary = "Listar productos con stock menor a 10 unidades")
    public List<Producto> obtenerBajoStock() {
        return productoService.listarConStockBajo(10);
    }
}