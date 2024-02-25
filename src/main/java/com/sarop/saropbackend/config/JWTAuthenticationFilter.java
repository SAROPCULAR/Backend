package com.sarop.saropbackend.config;

import com.sarop.saropbackend.common.TokenClaims;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Value("${application.security.jwt.header.string}")
    public String HEADER_STRING;

    @Value("${application.security.jwt.token.prefix}")
    public String TOKEN_PREFIX;




    private final JWTService jwtService;



    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(HEADER_STRING);
        final String jwtToken;
        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.substring(7);
        jwtService.verifyAndValidate(jwtToken);
        final Claims claims = jwtService.extractAllClaims(jwtToken);
        final String role = claims.get(TokenClaims.ROLE_NAME.getValue(), String.class);


        final Jwt jwt = new Jwt(
                jwtToken,
                Instant.ofEpochSecond(claims.getIssuedAt().getTime()),
                Instant.ofEpochSecond(claims.getExpiration().getTime()),
                Map.of(TokenClaims.ALGORITHM.getValue(), SignatureAlgorithm.HS256.getValue()),
                claims
        );

        UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken
                .authenticated(jwt, null, List.of(new SimpleGrantedAuthority(role)));

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
