package com.lovelyshades.controller;

import com.lovelyshades.dto.PermisoDTO;
import com.lovelyshades.model.Permiso;
import com.lovelyshades.service.permiso.PermisoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permisos")
@CrossOrigin("*")
public class PermisoController {

    private final PermisoService permisoService;

    public PermisoController(PermisoService permisoService) {
        this.permisoService = permisoService;
    }

    @GetMapping
    public List<PermisoDTO> obtenerTodos() {
        return permisoService.obtenerTodosLosPermisos();
    }

    @GetMapping("/{id}")
    public PermisoDTO obtenerPorId(@PathVariable Integer id) {
        return permisoService.obtenerPermisoPorId(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
    }

    @GetMapping("/nombre/{nombre}")
    public PermisoDTO obtenerPorNombre(@PathVariable String nombre) {
        return permisoService.obtenerPermisoPorNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
    }

    @PostMapping
    public PermisoDTO crearPermiso(@RequestBody Permiso permiso) {
        return permisoService.crearPermiso(permiso);
    }

    @PutMapping("/{id}")
    public PermisoDTO actualizarPermiso(@PathVariable Integer id, @RequestBody Permiso permiso) {
        return permisoService.actualizarPermiso(id, permiso);
    }

    @DeleteMapping("/{id}")
    public void eliminarPermiso(@PathVariable Integer id) {
        permisoService.eliminarPermiso(id);
    }

    @PutMapping("/{id}/estado")
    public void cambiarEstado(@PathVariable Integer id, @RequestParam Boolean estado) {
        permisoService.cambiarEstadoPermiso(id, estado);
    }
}
