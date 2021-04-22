package com.sgd.artlink.security.jwt;

import com.sgd.artlink.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

/**
 * Класс который формирует токен
 * */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String BEARER_PREFIX = "Bearer_";

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder()
                        .encodeToString(secret.getBytes());
    }

    public String createToken(String email, Role role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role.getName().name());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        // todo: добавить комментариев
        return Jwts.builder()
                .setClaims(claims) //
                .setIssuedAt(now) //
                .setExpiration(validity) //
                .signWith(SignatureAlgorithm.HS256, secret)//
                .compact();
    }

    public String getEmail(String token) {
        // todo: добавить комментариев
        return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        // todo: добавить комментариев
        String bearerToken = req.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }

        // todo: возможно нужно кидать исключение
        return null;
    }

    public boolean isTokenValid(String token) {
        // todo: добавить комментариев
        try {
            Jws<Claims> claims = Jwts.parser()
                                     .setSigningKey(secret)
                                     .parseClaimsJws(token);
            Date now = new Date();

            // токен протух - false
            // токен валидный - true
            return !claims.getBody()
                            .getExpiration()
                            .before(now);
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }
}
