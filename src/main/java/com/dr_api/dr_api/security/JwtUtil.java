package com.dr_api.dr_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

/**
 * Utilidad para la creación, validación y extracción de información de JWT.
 */
@Service
public class JwtUtil {

    // Inyecta la clave secreta desde application.properties
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    
    // Inyecta el tiempo de expiración desde application.properties
    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME; // En milisegundos

    // 1. Generar token
    public String generateToken(UserDetails userDetails) {
        // Añadimos el rol como un claim extra
        Map<String, Object> claims = new HashMap<>();
        String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();
        claims.put("role", role); 

        return createToken(claims, userDetails.getUsername());
    }
private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    // Keys.hmacShaKeyFor() devuelve un SecretKey basado en el array de bytes
    return Keys.hmacShaKeyFor(keyBytes);
}
    // 2. Creación del token: Define Subject, Claims, Issued Date, Expiration Date y Signatura.
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // Generalmente el username
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 3. Validar token contra un UserDetails
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        // Debe coincidir el username y el token NO debe haber expirado
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // --- Métodos de Extracción de Claims ---

    // Extraer el nombre de usuario (Subject)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extraer fecha de expiración
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extraer un claim específico
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Obtener todos los claims
private Claims extractAllClaims(String token) {
    return Jwts.parser()
            .verifyWith(getSigningKey()) // FIX: Usa el método moderno
            .build()
            .parseSignedClaims(token)
            .getPayload();
}

    // Verificar si el token ha expirado
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Obtener la clave de firma (Key)
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}