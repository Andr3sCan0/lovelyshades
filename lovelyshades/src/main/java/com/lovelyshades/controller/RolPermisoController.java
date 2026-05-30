package com.lovelyshades.controller;

import com.lovelyshades.dto.RolPermisoDTO;
import com.lovelyshades.service.rolpermiso.RolPermisoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rol-permisos")
@CrossOrigin("*")
public class RolPermisoController {

    private final RolPermisoService rolPermisoService;

    public RolPermisoController(RolPermisoService rolPermisoService) {
        this.rolPermisoService = rolPermisoService;
    }

    @PostMapping("/rol/{idRol}/permiso/{idPermiso}")
    public RolPermisoDTO asignarPermiso(
            @PathVariable Integer idRol,
            @PathVariable Integer idPermiso) {
        return rolPermisoService.asignarPermisoARol(idRol, idPermiso);
    }

    @DeleteMapping("/rol/{idRol}/permiso/{idPermiso}")
    public void removerPermiso(
            @PathVariable Integer idRol,
            @PathVariable Integer idPermiso) {
        rolPermisoService.removerPermisoDeRol(idRol, idPermiso);
    }

    @GetMapping("/rol/{idRol}")
    public List<RolPermisoDTO> obtenerPermisosDeRol(
            @PathVariable Integer idRol) {
        return rolPermisoService.obtenerPermisosDeRol(idRol);
    }

    @GetMapping("/permiso/{idPermiso}")
    public List<RolPermisoDTO> obtenerRolesDelPermiso(
            @PathVariable Integer idPermiso) {
        return rolPermisoService.obtenerRolesDelPermiso(idPermiso);
    }
}
