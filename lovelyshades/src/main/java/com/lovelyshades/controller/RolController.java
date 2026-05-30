package com.lovelyshades.controller;

import com.lovelyshades.dto.RolDTO;
import com.lovelyshades.model.Rol;
import com.lovelyshades.service.rol.RolService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin("*")
@PreAuthorize("hasRole('ADMIN')")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public List<RolDTO> obtenerTodos() {
        return rolService.obtenerTodosLosRoles();
    }

    @GetMapping("/{id}")
    public RolDTO obtenerPorId(@PathVariable Integer id) {
        return rolService.obtenerRolPorId(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

    @GetMapping("/nombre/{nombre}")
    public RolDTO obtenerPorNombre(@PathVariable String nombre) {
        return rolService.obtenerRolPorNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

    @PostMapping
    public RolDTO crearRol(@RequestBody Rol rol) {
        return rolService.crearRol(rol);
    }

    @PutMapping("/{id}")
    public RolDTO actualizarRol(@PathVariable Integer id, @RequestBody Rol rol) {
        return rolService.actualizarRol(id, rol);
    }

    @DeleteMapping("/{id}")
    public void eliminarRol(@PathVariable Integer id) {
        rolService.eliminarRol(id);
    }

    @PutMapping("/{id}/estado")
    public void cambiarEstado(@PathVariable Integer id, @RequestParam Boolean estado) {
        rolService.cambiarEstadoRol(id, estado);
    }
}
