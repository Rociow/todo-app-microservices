package com.rocio.todoapp.msvc_gateway.service;

import com.rocio.todoapp.msvc_gateway.client.UserClient;
import com.rocio.todoapp.msvc_gateway.config.JwtUtil;
import com.rocio.todoapp.msvc_gateway.dto.UserResponseDTO;
import com.rocio.todoapp.msvc_gateway.dto.request.LoginRequest;
import com.rocio.todoapp.msvc_gateway.dto.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserClient usuarioFeignClient;

    public AuthService(JwtUtil jwtUtil, UserClient usuarioFeignClient) {
        this.jwtUtil = jwtUtil;
        this.usuarioFeignClient = usuarioFeignClient;
    }

    // este metodo se encarga de autenticar al usuario
    public LoginResponse authenticate(LoginRequest request) {
        ResponseEntity<UserResponseDTO> response = usuarioFeignClient.validateCredentials(request);

        // como viene un response entity puedo trabajar con su status code y body
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            // si las credenciales son validas, genero el token
            UserResponseDTO usuario = response.getBody();
            // genero el token con el email y el rol del usuario
            String token = jwtUtil.generateToken(usuario.email(), usuario.role().toString());

            // retorno el token junto con la info del usuario
            return new LoginResponse(
                    token,
                    usuario.email(),
                    usuario.role(),
                    true,
                    null
            );
        }
        return new LoginResponse(null, null, null, false, "Credenciales inválidas");
    }
}

