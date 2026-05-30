package com.lovelyshades.controller;

import com.lovelyshades.dto.LoginRequest;
import com.lovelyshades.dto.LoginResponse;
import com.lovelyshades.dto.UsuarioDTO;
import com.lovelyshades.entity.Usuario;
import com.lovelyshades.service.auth.AuthService;
import com.lovelyshades.service.usuario.UsuarioService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    public AuthController(
            AuthService authService,
            UsuarioService usuarioService) {
        this.authService = authService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public UsuarioDTO register(
            @RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
