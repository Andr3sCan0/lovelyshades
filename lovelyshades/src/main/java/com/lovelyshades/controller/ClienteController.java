package com.lovelyshades.controller;

import com.lovelyshades.dao.cliente.ClienteDao;
import com.lovelyshades.model.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

   @Autowired
   private ClienteDao clienteDao;
    // GET - listar todos
    @GetMapping
    public List<Cliente> listar() {
        return clienteDao.listarTodos();
    }

    // GET - obtener por id
    @GetMapping("/{id}")
    public Cliente obtener(@PathVariable int id) {
        return clienteDao.buscarPorId(id).orElse(null);
    }

    // POST - crear cliente
    @PostMapping
    public Cliente crear(@RequestBody Cliente cliente) {
        clienteDao.guardar(cliente);
        return cliente;
    }

    // PUT - actualizar cliente
    @PutMapping
    public Cliente actualizar(@RequestBody Cliente cliente) {
        clienteDao.actualizar(cliente);
        return cliente;
        
    }

    // DELETE - eliminar cliente
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        clienteDao.eliminar(id);
        return "Cliente eliminado";
    }
}