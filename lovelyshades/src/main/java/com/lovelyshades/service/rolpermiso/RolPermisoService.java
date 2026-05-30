package com.lovelyshades.service.rolpermiso;

import com.lovelyshades.dto.RolPermisoDTO;

import java.util.List;

public interface RolPermisoService {
    RolPermisoDTO asignarPermisoARol(Integer idRol, Integer idPermiso);
    void removerPermisoDeRol(Integer idRol, Integer idPermiso);
    List<RolPermisoDTO> obtenerPermisosDeRol(Integer idRol);
    List<RolPermisoDTO> obtenerRolesDelPermiso(Integer idPermiso);
}
