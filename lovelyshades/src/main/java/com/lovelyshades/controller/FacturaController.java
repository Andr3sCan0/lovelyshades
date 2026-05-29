package com.lovelyshades.controller;

import com.lovelyshades.entity.Factura;
import com.lovelyshades.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Facturas", description = "Gestión de facturas")
@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @Operation(
            summary = "Crear factura",
            description = "Registra una nueva factura asociada a un cliente y a un usuario",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "subtotal": 150000,
                                      "impuesto": 28500,
                                      "total": 178500,
                                      "estado": "PAGADA",
                                      "idUsuario": 1,
                                      "cliente": {
                                        "idCliente": 1
                                      }
                                    }
                                    """
                            )
                    )
            )
    )
    @PostMapping
    public Factura crear(
            @Valid
            @org.springframework.web.bind.annotation.RequestBody Factura factura
    ) {
        return facturaService.crear(factura);
    }

    @Operation(
            summary = "Listar facturas",
            description = "Obtiene todas las facturas registradas"
    )
    @GetMapping
    public List<Factura> listar() {
        return facturaService.listar();
    }

    @Operation(
            summary = "Buscar factura por ID",
            description = "Obtiene una factura usando su identificador"
    )
    @GetMapping("/{id}")
    public Factura obtenerPorId(@PathVariable Long id) {
        return facturaService.obtenerPorId(id);
    }

    @Operation(
            summary = "Actualizar factura",
            description = "Actualiza la información de una factura existente",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "subtotal": 150000,
                                      "impuesto": 28500,
                                      "total": 178500,
                                      "estado": "ANULADA",
                                      "idUsuario": 1,
                                      "cliente": {
                                        "idCliente": 1
                                      }
                                    }
                                    """
                            )
                    )
            )
    )
    @PutMapping("/{id}")
    public Factura actualizar(
            @PathVariable Long id,
            @Valid
            @org.springframework.web.bind.annotation.RequestBody Factura factura
    ) {
        return facturaService.actualizar(id, factura);
    }

    @Operation(
            summary = "Eliminar factura",
            description = "Elimina una factura por su identificador"
    )
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        facturaService.eliminar(id);
    }
}