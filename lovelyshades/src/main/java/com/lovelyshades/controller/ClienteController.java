package com.lovelyshades.controller;

import com.lovelyshades.entity.Cliente;
import com.lovelyshades.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;

@Tag(name = "Clientes", description = "Gestión de clientes")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(
        summary = "Crear cliente",
        description = "Registra un nuevo cliente en el sistema",
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        examples = @ExampleObject(
                                value = """
                                {
                                  "nombreCompleto": "Laura Martinez",
                                  "tipoIdentificacion": "CC",
                                  "numeroIdentificacion": "1234567890",
                                  "telefono": "3001234567",
                                  "email": "laura@gmail.com",
                                  "direccion": "Calle 123",
                                  "estado": true
                                }
                                """
                        )
                )
        )
)
@PostMapping
public Cliente crear(
        @Valid
        @org.springframework.web.bind.annotation.RequestBody Cliente cliente
) {
    return clienteService.crear(cliente);
}

    @Operation(summary = "Listar clientes", description = "Obtiene todos los clientes registrados")
    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listar();
    }

    @Operation(summary = "Buscar cliente por ID", description = "Obtiene un cliente usando su identificador")
    @GetMapping("/{id}")
    public Cliente obtenerPorId(@PathVariable Integer id) {
        return clienteService.obtenerPorId(id);
    }

    @Operation(summary = "Actualizar cliente", description = "Actualiza la información de un cliente existente")
    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable Integer id, @Valid @RequestBody Cliente cliente) {
        return clienteService.actualizar(id, cliente);
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente por su identificador")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
    }
}