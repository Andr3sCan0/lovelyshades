package com.lovelyshades.controller;

import com.lovelyshades.entity.DetalleFactura;
import com.lovelyshades.service.DetalleFacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Detalle Factura", description = "Gestión de los productos asociados a una factura")
@RestController
@RequestMapping("/api/detalle-facturas")
public class DetalleFacturaController {

    private final DetalleFacturaService detalleFacturaService;

    public DetalleFacturaController(DetalleFacturaService detalleFacturaService) {
        this.detalleFacturaService = detalleFacturaService;
    }

    @Operation(
            summary = "Crear detalle de factura",
            description = "Registra un producto dentro de una factura",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "cantidad": 1,
                                      "precioUnitario": 150000,
                                      "subtotal": 150000,
                                      "factura": {
                                        "idFactura": 1
                                      },
                                      "producto": {
                                        "idProducto": 1
                                      }
                                    }
                                    """
                            )
                    )
            )
    )
    @PostMapping
    public DetalleFactura crear(
            @Valid
            @org.springframework.web.bind.annotation.RequestBody DetalleFactura detalleFactura
    ) {
        return detalleFacturaService.crear(detalleFactura);
    }

    @Operation(
            summary = "Listar detalles de factura",
            description = "Obtiene todos los detalles registrados"
    )
    @GetMapping
    public List<DetalleFactura> listar() {
        return detalleFacturaService.listar();
    }

    @Operation(
            summary = "Buscar detalle por ID",
            description = "Obtiene un detalle de factura usando su identificador"
    )
    @GetMapping("/{id}")
    public DetalleFactura obtenerPorId(@PathVariable Integer id) {
        return detalleFacturaService.obtenerPorId(id);
    }

    @Operation(
            summary = "Actualizar detalle de factura",
            description = "Actualiza la información de un detalle existente",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "cantidad": 2,
                                      "precioUnitario": 150000,
                                      "subtotal": 300000,
                                      "factura": {
                                        "idFactura": 1
                                      },
                                      "producto": {
                                        "idProducto": 1
                                      }
                                    }
                                    """
                            )
                    )
            )
    )
    @PutMapping("/{id}")
    public DetalleFactura actualizar(
            @PathVariable Integer id,
            @Valid
            @org.springframework.web.bind.annotation.RequestBody DetalleFactura detalleFactura
    ) {
        return detalleFacturaService.actualizar(id, detalleFactura);
    }

    @Operation(
            summary = "Eliminar detalle de factura",
            description = "Elimina un detalle de factura por su identificador"
    )
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        detalleFacturaService.eliminar(id);
    }
}