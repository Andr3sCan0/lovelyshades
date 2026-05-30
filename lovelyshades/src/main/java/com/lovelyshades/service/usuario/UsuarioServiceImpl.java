package com.lovelyshades.service.usuario;

import com.lovelyshades.dto.UsuarioDTO;
import com.lovelyshades.dto.RolDTO;
import com.lovelyshades.model.Rol;
import com.lovelyshades.model.Usuario;
import com.lovelyshades.jpa.repository.RolRepository;
import com.lovelyshades.jpa.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioDTO crearUsuario(Usuario usuario) {
        // Asignar un rol válido antes de guardar
        usuario.setRol(resolverRol(usuario.getRol()));
        usuario.setContrasenaHash(passwordEncoder.encode(usuario.getContrasenaHash()));
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirADTO(usuarioGuardado);
    }

    @Override
    public Optional<UsuarioDTO> obtenerUsuarioPorId(Integer idUsuario) {
        return usuarioRepository.findByIdUsuario(idUsuario)
                .map(this::convertirADTO);
    }

    @Override
    public Optional<UsuarioDTO> obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(this::convertirADTO);
    }

    @Override
    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO actualizarUsuario(Integer idUsuario, Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
        usuario.setEmail(usuarioActualizado.getEmail());
        if (usuarioActualizado.getContrasenaHash() != null && !usuarioActualizado.getContrasenaHash().isEmpty()) {
            usuario.setContrasenaHash(passwordEncoder.encode(usuarioActualizado.getContrasenaHash()));
        }
        usuario.setRol(usuarioActualizado.getRol());
        usuario.setEstado(usuarioActualizado.getEstado());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirADTO(usuarioGuardado);
    }

    private Rol resolverRol(Rol rol) {
        if (rol == null) {
            return obtenerRolPorDefecto();
        }

        if (rol.getIdRol() != null) {
            return rolRepository.findByIdRol(rol.getIdRol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + rol.getIdRol()));
        }

        if (rol.getNombreRol() != null && !rol.getNombreRol().isBlank()) {
            return rolRepository.findByNombreRol(rol.getNombreRol())
                    .orElse(obtenerRolPorDefecto());
        }

        return obtenerRolPorDefecto();
    }

    private Rol obtenerRolPorDefecto() {
        return rolRepository.findByNombreRol("user")
                .orElseThrow(() -> new RuntimeException("Rol por defecto 'Cliente' no encontrado"));
    }

    @Override
    public void eliminarUsuario(Integer idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    @Override
    public void cambiarEstadoUsuario(Integer idUsuario, Boolean estado) {
        Usuario usuario = usuarioRepository.findByIdUsuario(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setEstado(estado);
        usuarioRepository.save(usuario);
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        RolDTO rolDTO = new RolDTO(
                usuario.getRol().getIdRol(),
                usuario.getRol().getNombreRol(),
                usuario.getRol().getEstado()
        );

        return new UsuarioDTO(
                usuario.getIdUsuario(),
                usuario.getNombreUsuario(),
                usuario.getEmail(),
                rolDTO,
                usuario.getEstado(),
                usuario.getFechaCreacion(),
                usuario.getUltimoAcceso()
        );
    }
}
