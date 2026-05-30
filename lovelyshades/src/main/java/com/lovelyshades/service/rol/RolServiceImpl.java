package com.lovelyshades.service.rol;

import com.lovelyshades.dto.RolDTO;
import com.lovelyshades.dto.PermisoDTO;
import com.lovelyshades.model.Rol;
import com.lovelyshades.jpa.repository.RolRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public RolDTO crearRol(Rol rol) {
        Rol rolGuardado = rolRepository.save(rol);
        return convertirADTO(rolGuardado);
    }

    @Override
    public Optional<RolDTO> obtenerRolPorId(Integer idRol) {
        return rolRepository.findByIdRol(idRol)
                .map(this::convertirADTO);
    }

    @Override
    public Optional<RolDTO> obtenerRolPorNombre(String nombreRol) {
        return rolRepository.findByNombreRol(nombreRol)
                .map(this::convertirADTO);
    }

    @Override
    public List<RolDTO> obtenerTodosLosRoles() {
        return rolRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public RolDTO actualizarRol(Integer idRol, Rol rolActualizado) {
        Rol rol = rolRepository.findByIdRol(idRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        rol.setNombreRol(rolActualizado.getNombreRol());
        rol.setEstado(rolActualizado.getEstado());

        Rol rolGuardado = rolRepository.save(rol);
        return convertirADTO(rolGuardado);
    }

    @Override
    public void eliminarRol(Integer idRol) {
        rolRepository.deleteById(idRol);
    }

    @Override
    public void cambiarEstadoRol(Integer idRol, Boolean estado) {
        Rol rol = rolRepository.findByIdRol(idRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        rol.setEstado(estado);
        rolRepository.save(rol);
    }

    private RolDTO convertirADTO(Rol rol) {
        RolDTO rolDTO = new RolDTO(
                rol.getIdRol(),
                rol.getNombreRol(),
                rol.getEstado()
        );

        if (rol.getPermisos() != null && !rol.getPermisos().isEmpty()) {
            var permisos = rol.getPermisos()
                    .stream()
                    .map(p -> new PermisoDTO(
                            p.getIdPermiso(),
                            p.getNombrePermiso(),
                            p.getDescripcion(),
                            p.getEstado()
                    ))
                    .collect(Collectors.toSet());
            rolDTO.setPermisos(permisos);
        }

        return rolDTO;
    }
}
