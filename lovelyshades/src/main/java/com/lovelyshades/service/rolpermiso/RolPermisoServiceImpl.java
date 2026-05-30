package com.lovelyshades.service.rolpermiso;

import com.lovelyshades.dto.RolPermisoDTO;
import com.lovelyshades.model.RolPermiso;
import com.lovelyshades.jpa.repository.RolPermisoRepository;
import com.lovelyshades.jpa.repository.RolRepository;
import com.lovelyshades.jpa.repository.PermisoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolPermisoServiceImpl implements RolPermisoService {

    private final RolPermisoRepository rolPermisoRepository;
    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;

    public RolPermisoServiceImpl(RolPermisoRepository rolPermisoRepository,
                                RolRepository rolRepository,
                                PermisoRepository permisoRepository) {
        this.rolPermisoRepository = rolPermisoRepository;
        this.rolRepository = rolRepository;
        this.permisoRepository = permisoRepository;
    }

    @Override
    public RolPermisoDTO asignarPermisoARol(Integer idRol, Integer idPermiso) {
        var rol = rolRepository.findByIdRol(idRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        var permiso = permisoRepository.findByIdPermiso(idPermiso)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));

        // Verificar si ya existe la asignación
        Optional<RolPermiso> existente = rolPermisoRepository.findByRolIdRolAndPermisoIdPermiso(idRol, idPermiso);
        if (existente.isPresent()) {
            throw new RuntimeException("El permiso ya está asignado a este rol");
        }

        RolPermiso rolPermiso = new RolPermiso(rol, permiso);
        RolPermiso guardado = rolPermisoRepository.save(rolPermiso);
        return convertirADTO(guardado);
    }

    @Override
    public void removerPermisoDeRol(Integer idRol, Integer idPermiso) {
        RolPermiso rolPermiso = rolPermisoRepository.findByRolIdRolAndPermisoIdPermiso(idRol, idPermiso)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));
        rolPermisoRepository.delete(rolPermiso);
    }

    @Override
    public List<RolPermisoDTO> obtenerPermisosDeRol(Integer idRol) {
        return rolPermisoRepository.findByRolIdRol(idRol)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RolPermisoDTO> obtenerRolesDelPermiso(Integer idPermiso) {
        return rolPermisoRepository.findByPermisoIdPermiso(idPermiso)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private RolPermisoDTO convertirADTO(RolPermiso rolPermiso) {
        return new RolPermisoDTO(
                rolPermiso.getIdRolPermiso(),
                rolPermiso.getRol().getIdRol(),
                rolPermiso.getPermiso().getIdPermiso(),
                rolPermiso.getFechaAsignacion()
        );
    }
}
