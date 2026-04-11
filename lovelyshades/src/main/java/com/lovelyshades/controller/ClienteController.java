package com.lovelyshades.controller;

import com.lovelyshades.model.Cliente;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    // Simulación de base de datos (temporal)
    private List<Cliente> listaClientes = new ArrayList<>();

    // GET - listar todos
    @GetMapping
    public List<Cliente> listar() {
        return listaClientes;
    }

    // GET - obtener por id
    @GetMapping("/{id}")
    public Cliente obtener(@PathVariable int id) {
        return listaClientes.stream()
                .filter(c -> c.getIdCliente() == id)
                .findFirst()
                .orElse(null);
    }

    // POST - crear cliente
    @PostMapping
    public Cliente crear(@RequestBody Cliente cliente) {
        listaClientes.add(cliente);
        return cliente;
    }

    // PUT - actualizar cliente
    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable int id, @RequestBody Cliente cliente) {
        for (int i = 0; i < listaClientes.size(); i++) {
            if (listaClientes.get(i).getIdCliente() == id) {
                listaClientes.set(i, cliente);
                return cliente;
            }
        }
        return null;
    }

    // DELETE - eliminar cliente
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable int id) {
        listaClientes.removeIf(c -> c.getIdCliente() == id);
        return "Cliente eliminado";
    }
}