package com.lovelyshades.service.permiso;

import com.lovelyshades.dto.PermisoDTO;
import com.lovelyshades.model.Permiso;
import com.lovelyshades.jpa.repository.PermisoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermisoServiceImpl implements PermisoService {

    private final PermisoRepository permisoRepository;

    public PermisoServiceImpl(PermisoRepository permisoRepository) {
        this.permisoRepository = permisoRepository;
    }

    @Override
    public PermisoDTO crearPermiso(Permiso permiso) {
        Permiso permisoGuardado = permisoRepository.save(permiso);
        return convertirADTO(permisoGuardado);
    }

    @Override
    public Optional<PermisoDTO> obtenerPermisoPorId(Integer idPermiso) {
        return permisoRepository.findByIdPermiso(idPermiso)
                .map(this::convertirADTO);
    }

    @Override
    public Optional<PermisoDTO> obtenerPermisoPorNombre(String nombrePermiso) {
        return permisoRepository.findByNombrePermiso(nombrePermiso)
                .map(this::convertirADTO);
    }

    @Override
    public List<PermisoDTO> obtenerTodosLosPermisos() {
        return permisoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public PermisoDTO actualizarPermiso(Integer idPermiso, Permiso permisoActualizado) {
        Permiso permiso = permisoRepository.findByIdPermiso(idPermiso)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));

        permiso.setNombrePermiso(permisoActualizado.getNombrePermiso());
        permiso.setDescripcion(permisoActualizado.getDescripcion());
        permiso.setEstado(permisoActualizado.getEstado());

        Permiso permisoGuardado = permisoRepository.save(permiso);
        return convertirADTO(permisoGuardado);
    }

    @Override
    public void eliminarPermiso(Integer idPermiso) {
        permisoRepository.deleteById(idPermiso);
    }

    @Override
    public void cambiarEstadoPermiso(Integer idPermiso, Boolean estado) {
        Permiso permiso = permisoRepository.findByIdPermiso(idPermiso)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
        permiso.setEstado(estado);
        permisoRepository.save(permiso);
    }

    private PermisoDTO convertirADTO(Permiso permiso) {
        return new PermisoDTO(
                permiso.getIdPermiso(),
                permiso.getNombrePermiso(),
                permiso.getDescripcion(),
                permiso.getEstado()
        );
    }
}
