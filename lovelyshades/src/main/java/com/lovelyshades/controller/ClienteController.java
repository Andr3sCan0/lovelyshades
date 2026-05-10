package com.lovelyshades.controller;

import com.lovelyshades.dao.cliente.ClienteDao;
import com.lovelyshades.model.Cliente;

import com.lovelyshades.service.cliente.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Operaciones relacionadas con clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

   @Operation(summary = "busca por email")
   @GetMapping("/api/buscaEmail")
   public Cliente buscarPorEmail(@PathVariable String email){
       return clienteService.buscarPorEmail(email).orElse(null);
   }

    // GET - listar todos
    @Operation(summary = "Listar todos los clientes")
    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listarTodos();
    }

    // GET - obtener por id
    @Operation(summary = "Obtener cliente por ID")
    @GetMapping("/{id}")
    public Cliente obtener(@PathVariable Integer id) {
        return clienteService.buscarPorId(id).orElse(null);
    }

    // POST - crear cliente
    @PostMapping
    public Cliente crear(@RequestBody Cliente cliente) {
        clienteService.guardar(cliente);
        return cliente;
    }

    @Operation(summary = "Crear un cliente")
    @PostMapping(path="/guardarJPA")
    public Cliente guardarJPA(@RequestBody Cliente cliente){
       return clienteService.guardarJpa(cliente);
    }

    // PUT - actualizar cliente
    @Operation(summary = "Actualizar cliente")
    @PutMapping
    public Cliente actualizar(@RequestBody Cliente cliente) {
        clienteService.actualizar(cliente);
        return cliente;
        
    }

    // DELETE - eliminar cliente
    @Operation(summary = "Eliminar cliente")
    @DeleteMapping("/{id}")
    public String eliminar(@Parameter(description = "ID del cliente", example = "1") @PathVariable Integer id) {
        clienteService.eliminar(id);
        return "Cliente eliminado";
    }
}