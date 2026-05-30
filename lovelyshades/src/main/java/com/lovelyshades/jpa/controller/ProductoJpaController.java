package com.lovelyshades.jpa.controller;

import com.lovelyshades.jpa.entity.ProductoUtilEntity;
import com.lovelyshades.jpa.service.ProductoUtilJpaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/jpa/productos")
@Tag(name = "Productos JPA", description = "CRUD de productos usando Spring Data JPA")
public class ProductoJpaController {

    // Inyectamos la INTERFAZ, no la clase concreta (SOLID)
    private final ProductoUtilJpaService productoJpaService;

    public ProductoJpaController(ProductoUtilJpaService productoJpaService) {
        this.productoJpaService = productoJpaService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los productos")
    public List<ProductoUtilEntity> listar() {
        return productoJpaService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    public ResponseEntity<ProductoUtilEntity> obtener(@PathVariable Integer id) {
        return productoJpaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto")
    public ResponseEntity<ProductoUtilEntity> crear(@RequestBody ProductoUtilEntity producto) {
        ProductoUtilEntity guardado = productoJpaService.guardar(producto);
        return ResponseEntity.ok(guardado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente")
    public ResponseEntity<ProductoUtilEntity> actualizar(@PathVariable Integer id,
                                                         @RequestBody ProductoUtilEntity producto) {
        producto.setIdProducto(id);
        try {
            ProductoUtilEntity actualizado = productoJpaService.actualizar(producto);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        productoJpaService.eliminar(id);
        return ResponseEntity.ok("Producto eliminado");
    }

    @GetMapping("/bajo-stock")
    @Operation(summary = "Listar productos con stock bajo (menos de 10 unidades)")
    public List<ProductoUtilEntity> bajosStock() {
        return productoJpaService.listarConStockBajo(10);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar productos por nombre")
    public List<ProductoUtilEntity> buscarPorNombre(@RequestParam String nombre) {
        return productoJpaService.buscarPorNombre(nombre);
    }
}
