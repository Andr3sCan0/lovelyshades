package com.lovelyshades.service.auth;

import com.lovelyshades.dto.LoginRequest;
import com.lovelyshades.dto.LoginResponse;
import com.lovelyshades.dto.UsuarioDTO;
import com.lovelyshades.dto.RolDTO;
import com.lovelyshades.model.Usuario;
import com.lovelyshades.model.Rol;
import com.lovelyshades.service.security.JwtService;
import com.lovelyshades.jpa.repository.UsuarioRepository;
import com.lovelyshades.jpa.repository.RolRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public Usuario register(Usuario usuario) {
        // Asignar un rol válido antes de guardar
        usuario.setRol(resolverRol(usuario.getRol()));

        usuario.setContrasenaHash(
                passwordEncoder.encode(usuario.getContrasenaHash())
        );
        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setEstado(true);
        
        return usuarioRepository.save(usuario);
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
        return rolRepository.findByNombreRol("Cliente")
                .orElseThrow(() -> new RuntimeException("Rol por defecto 'Cliente' no encontrado"));
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getEstado()) {
            throw new RuntimeException("Usuario inactivo");
        }

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                usuario.getContrasenaHash()
        );

        if (!matches) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Actualizar último acceso
        usuario.setUltimoAcceso(LocalDateTime.now());
        usuarioRepository.save(usuario);

        // Generar token JWT
        String token = jwtService.generateToken(
                usuario.getEmail(),
                usuario.getRol().getNombreRol()
        );

        // Crear DTO del usuario
        RolDTO rolDTO = new RolDTO(
                usuario.getRol().getIdRol(),
                usuario.getRol().getNombreRol(),
                usuario.getRol().getEstado()
        );

        UsuarioDTO usuarioDTO = new UsuarioDTO(
                usuario.getIdUsuario(),
                usuario.getNombreUsuario(),
                usuario.getEmail(),
                rolDTO,
                usuario.getEstado(),
                usuario.getFechaCreacion(),
                usuario.getUltimoAcceso()
        );

        return new LoginResponse(token, usuarioDTO);
    }
}