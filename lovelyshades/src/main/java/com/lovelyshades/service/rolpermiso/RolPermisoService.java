package com.lovelyshades.service.rolpermiso;

import com.lovelyshades.dto.RolPermisoDTO;
import com.lovelyshades.model.RolPermiso;
import java.util.List;
import java.util.Optional;

public interface RolPermisoService {
    RolPermisoDTO asignarPermisoARol(Integer idRol, Integer idPermiso);
    void removerPermisoDeRol(Integer idRol, Integer idPermiso);
    List<RolPermisoDTO> obtenerPermisosDeRol(Integer idRol);
    List<RolPermisoDTO> obtenerRolesDelPermiso(Integer idPermiso);
}
