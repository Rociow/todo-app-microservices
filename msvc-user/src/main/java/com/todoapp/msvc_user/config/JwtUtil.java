package com.todoapp.msvc_user.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    //inyección de valores del archivo applitacion.yml
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:3600000}") // 1 hora por defecto
    private Long expiration;

    //Convierte ese secreto en una clave válida para firmar y validar tokens JWT usando el algoritmo HMAC-SHA256.
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    //Genera un token JWT con el email y rol proporcionados.
    public String generateToken(String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        //Claims = la información que viaja dentro del JWT.
        // Agrega el rol como un claims personalizado en el token JWT
        claims.put("role", role);

        //Construye el token JWT con los claims, sujeto (email), fecha de emisión, fecha de expiración y firma.
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    //Obtiene los claims de un token JWT dado.
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Claims extractClaims(String token) {
        return getClaims(token);
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    //Verifica si un token JWT es válido comprobando su fecha de expiración.
    public boolean isTokenValid(String token) {
        try {
            return !getClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    //Valida un token JWT utilizando el método isTokenValid.
    public boolean validateToken(String token) {
        return isTokenValid(token);
    }
    
    // Validar token (firma + expiración)
    public boolean validateToken(String token, String email) {
        String tokenEmail = extractEmail(token);
        return (tokenEmail.equals(email) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}

