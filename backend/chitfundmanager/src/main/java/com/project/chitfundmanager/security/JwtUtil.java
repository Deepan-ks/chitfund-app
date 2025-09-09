package com.project.chitfundmanager.security;

import com.project.chitfundmanager.model.AuthUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms:3600000}")
    private long jwtExpirationMs;

    // Create a SecretKey robustly: try Base64 decode; fallback to UTF-8 bytes; if too short, hash to 256-bit
    private SecretKey getSigningKey() {
        try {
            // try base64 decode first
            byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (Exception e) {
            try {
                byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
                if (keyBytes.length < 32) { // need 256-bit min for HS256
                    MessageDigest sha = MessageDigest.getInstance("SHA-256");
                    keyBytes = sha.digest(keyBytes);
                }
                return Keys.hmacShaKeyFor(keyBytes);
            } catch (NoSuchAlgorithmException ex) {
                // very unlikely
                throw new IllegalStateException("Unable to construct JWT signing key", ex);
            }
        }
    }

    public String generateToken(AuthUser user, Map<String, Object> extraClaims) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);
        JwtBuilder b = Jwts.builder()
                .setSubject(user.getMobileNumber())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .addClaims(extraClaims != null ? extraClaims : Map.of())
                .claim("role", user.getRole().name());

        return b.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public String generateToken(AuthUser user) {
        return generateToken(user, null);
    }

    private Claims extractAllClaims(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        final Date exp = extractExpiration(token);
        return exp.before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (JwtException ex) {
            log.debug("JWT validation failed: {}", ex.getMessage());
            return false;
        }
    }
}

