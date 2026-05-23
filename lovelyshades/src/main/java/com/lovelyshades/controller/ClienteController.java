package com.lovelyshades.controller;

import com.lovelyshades.model.Cliente;
import com.lovelyshades.service.cliente.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "CRUD de clientes usando SQL nativo (JDBC)")
public class ClienteController {

    // Inyectamos la INTERFAZ, no la clase concreta (SOLID)
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los clientes")
    public List<Cliente> listar() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID")
    public Cliente obtener(@PathVariable int id) {
        return clienteService.buscarPorId(id).orElse(null);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo cliente")
    public Cliente crear(@RequestBody Cliente cliente) {
        return clienteService.guardar(cliente);
    }

    @PutMapping
    @Operation(summary = "Actualizar un cliente existente")
    public Cliente actualizar(@RequestBody Cliente cliente) {
        clienteService.actualizar(cliente);
        return cliente;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cliente por ID")
    public String eliminar(@PathVariable int id) {
        clienteService.eliminar(id);
        return "Cliente eliminado";
    }
}