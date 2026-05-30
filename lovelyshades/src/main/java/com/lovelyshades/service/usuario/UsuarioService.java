package com.lovelyshades.service.usuario;

import com.lovelyshades.dto.UsuarioDTO;
import com.lovelyshades.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    UsuarioDTO crearUsuario(Usuario usuario);
    Optional<UsuarioDTO> obtenerUsuarioPorId(Integer idUsuario);
    Optional<UsuarioDTO> obtenerUsuarioPorEmail(String email);
    List<UsuarioDTO> obtenerTodosLosUsuarios();
    UsuarioDTO actualizarUsuario(Integer idUsuario, Usuario usuarioActualizado);
    void eliminarUsuario(Integer idUsuario);
    void cambiarEstadoUsuario(Integer idUsuario, Boolean estado);
}
