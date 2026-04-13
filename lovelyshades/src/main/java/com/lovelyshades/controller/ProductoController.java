package com.lovelyshades.controller;

import com.lovelyshades.dao.cliente.ClienteDao;
import com.lovelyshades.dao.producto.ProductoDao;
import com.lovelyshades.model.Producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {


    @Autowired
   private ProductoDao productoDao;
    // GET - listar todos
    @GetMapping
    public List<Producto> listar() {
        return productoDao.listarTodos();
    }

    // GET - obtener por id
    @GetMapping("/{id}")
    public Producto obtener(@PathVariable int id) {
        return productoDao.buscarPorId(id).orElse(null);
    }

    // POST - crear producto
    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        productoDao.guardar(producto);
        return producto;
    }

    // PUT - actualizar producto
    @PutMapping
    public Producto actualizar(@RequestBody Producto producto) {
        productoDao.actualizar(producto);
        return null;
    }

    // DELETE - eliminar producto
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        productoDao.eliminar(id);
        return "Producto eliminado";
    }

    // GET - Alarmas de bajo stock (stock < 10)
    @GetMapping("/bajo-stock")
    public List<Producto> obtenerBajoStock() {
        return productoDao.listarConStockBajo(10);
    }
}
