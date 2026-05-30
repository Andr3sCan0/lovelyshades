package com.lovelyshades.service.auth;

import com.lovelyshades.dto.LoginRequest;
import com.lovelyshades.dto.LoginResponse;
import com.lovelyshades.entity.Usuario;

public interface AuthService {

    Usuario register(Usuario usuario);

    LoginResponse login(LoginRequest request);
}
