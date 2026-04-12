package com.lovelyshades.controller;

import com.lovelyshades.model.Producto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    // Simulación de base de datos (temporal)
    private List<Producto> listaProductos = new ArrayList<>();

    public ProductoController() {
        // Datos iniciales de prueba (según SQL proporcionado)
        listaProductos.add(new Producto(1, "Labial", "Labial rojo mate", 25000.0, 50));
        listaProductos.add(new Producto(2, "Base", "Base líquida tono medio", 45000.0, 30));
    }

    // GET - listar todos
    @GetMapping
    public List<Producto> listar() {
        return listaProductos;
    }

    // GET - obtener por id
    @GetMapping("/{id}")
    public Producto obtener(@PathVariable int id) {
        return listaProductos.stream()
                .filter(p -> p.getIdProducto() == id)
                .findFirst()
                .orElse(null);
    }

    // POST - crear producto
    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        listaProductos.add(producto);
        return producto;
    }

    // PUT - actualizar producto
    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable int id, @RequestBody Producto producto) {
        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getIdProducto() == id) {
                listaProductos.set(i, producto);
                return producto;
            }
        }
        return null;
    }

    // DELETE - eliminar producto
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        listaProductos.removeIf(p -> p.getIdProducto() == id);
        return "Producto eliminado";
    }

    // GET - Alarmas de bajo stock (stock < 10)
    @GetMapping("/bajo-stock")
    public List<Producto> obtenerBajoStock() {
        return listaProductos.stream()
                .filter(p -> p.getStock() < 10)
                .collect(Collectors.toList());
    }
}
