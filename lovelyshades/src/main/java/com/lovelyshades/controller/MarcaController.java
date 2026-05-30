package com.lovelyshades.controller;

import com.lovelyshades.entity.Marca;
import com.lovelyshades.service.MarcaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Marcas", description = "Gestion de marcas")
@RestController
@RequestMapping("/api/marcas")
@PreAuthorize("hasRole('ADMIN')")
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @Operation(
            summary = "Crear marca",
            description = "Registra una nueva marca",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "nombreMarca": "Clinique",
                                      "estado": true
                                    }
                                    """
                            )
                    )
            )
    )
    @PostMapping
    public Marca crear(@Valid @org.springframework.web.bind.annotation.RequestBody Marca marca) {
        return marcaService.crear(marca);
    }

    @Operation(summary = "Listar marcas", description = "Obtiene todas las marcas registradas")
    @GetMapping
    public List<Marca> listar() {
        return marcaService.listar();
    }

    @Operation(summary = "Buscar marca por ID", description = "Obtiene una marca usando su identificador")
    @GetMapping("/{id}")
    public Marca obtenerPorId(@PathVariable Integer id) {
        return marcaService.obtenerPorId(id);
    }

    @Operation(
            summary = "Actualizar marca",
            description = "Actualiza la informacion de una marca existente",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                    {
                                      "nombreMarca": "Clinique Updated",
                                      "estado": true
                                    }
                                    """
                            )
                    )
            )
    )
    @PutMapping("/{id}")
    public Marca actualizar(
            @PathVariable Integer id,
            @Valid @org.springframework.web.bind.annotation.RequestBody Marca marca
    ) {
        return marcaService.actualizar(id, marca);
    }

    @Operation(summary = "Eliminar marca", description = "Elimina una marca por su identificador")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        marcaService.eliminar(id);
    }
}
