package com.lovelyshades.service.rol;

import com.lovelyshades.dto.RolDTO;
import com.lovelyshades.entity.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {
    RolDTO crearRol(Rol rol);
    Optional<RolDTO> obtenerRolPorId(Integer idRol);
    Optional<RolDTO> obtenerRolPorNombre(String nombreRol);
    List<RolDTO> obtenerTodosLosRoles();
    RolDTO actualizarRol(Integer idRol, Rol rolActualizado);
    void eliminarRol(Integer idRol);
    void cambiarEstadoRol(Integer idRol, Boolean estado);
}
