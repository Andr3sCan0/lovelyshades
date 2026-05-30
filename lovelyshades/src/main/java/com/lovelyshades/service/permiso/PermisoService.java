package com.lovelyshades.service.permiso;

import com.lovelyshades.dto.PermisoDTO;
import com.lovelyshades.model.Permiso;
import java.util.List;
import java.util.Optional;

public interface PermisoService {
    PermisoDTO crearPermiso(Permiso permiso);
    Optional<PermisoDTO> obtenerPermisoPorId(Integer idPermiso);
    Optional<PermisoDTO> obtenerPermisoPorNombre(String nombrePermiso);
    List<PermisoDTO> obtenerTodosLosPermisos();
    PermisoDTO actualizarPermiso(Integer idPermiso, Permiso permisoActualizado);
    void eliminarPermiso(Integer idPermiso);
    void cambiarEstadoPermiso(Integer idPermiso, Boolean estado);
}
