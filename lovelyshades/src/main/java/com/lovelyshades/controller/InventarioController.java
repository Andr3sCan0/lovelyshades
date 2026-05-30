package com.lovelyshades.controller;

import com.lovelyshades.entity.Inventario;
import com.lovelyshades.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;

@Tag(name = "Inventario", description = "Gestión de movimientos de inventario")
@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @Operation(
        summary = "Crear movimiento de inventario",
        description = "Registra una entrada, salida o ajuste de inventario",
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        examples = @ExampleObject(
                                value = """
                                {
                                  "producto": {
                                    "idProducto": 1
                                  },
                                  "idUsuario": 1,
                                  "factura": {
                                    "idFactura": 1
                                  },
                                  "tipoMovimiento": "SALIDA",
                                  "cantidad": 1,
                                  "observacion": "Venta de producto"
                                }
                                """
                        )
                )
        )
)
@PostMapping
public Inventario crear(
        @Valid
        @org.springframework.web.bind.annotation.RequestBody Inventario inventario
) {
    return inventarioService.crear(inventario);
}

    @Operation(summary = "Listar movimientos de inventario", description = "Obtiene todos los movimientos registrados")
    @GetMapping
    public List<Inventario> listar() {
        return inventarioService.listar();
    }

    @Operation(summary = "Buscar movimiento por ID", description = "Obtiene un movimiento de inventario por su identificador")
    @GetMapping("/{id}")
    public Inventario obtenerPorId(@PathVariable Integer id) {
        return inventarioService.obtenerPorId(id);
    }

    @Operation(summary = "Actualizar movimiento de inventario", description = "Actualiza la información de un movimiento existente")
    @PutMapping("/{id}")
    public Inventario actualizar(@PathVariable Integer id, @Valid @RequestBody Inventario inventario) {
        return inventarioService.actualizar(id, inventario);
    }

    @Operation(summary = "Eliminar movimiento de inventario", description = "Elimina un movimiento de inventario por su identificador")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        inventarioService.eliminar(id);
    }
}