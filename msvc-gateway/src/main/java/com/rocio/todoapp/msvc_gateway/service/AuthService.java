package com.rocio.todoapp.msvc_gateway.service;

import com.rocio.todoapp.msvc_gateway.client.UserClient;
import com.rocio.todoapp.msvc_gateway.config.JwtUtil;
import com.rocio.todoapp.msvc_gateway.dto.UserResponseDTO;
import com.rocio.todoapp.msvc_gateway.dto.request.LoginRequestDTO;
import com.rocio.todoapp.msvc_gateway.dto.response.LoginResponseDTO;
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
    public LoginResponseDTO authenticate(LoginRequestDTO request) {
        ResponseEntity<LoginResponseDTO> response = usuarioFeignClient.validateCredentials(request);

        // como viene un response entity puedo trabajar con su status code y body
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            // si las credenciales son validas, genero el token
            LoginResponseDTO usuario = response.getBody();
            // genero el token con el email y el rol del usuario
            String token = jwtUtil.generateToken(usuario.email(), usuario.role());

            // retorno el token junto con la info del usuario
            return new LoginResponseDTO(
                    token,
                    usuario.email(),
                    usuario.role(),
                    true,
                    null
            );
        }
        return new LoginResponseDTO(null, null, null, false, "Credenciales inválidas");
    }
}

