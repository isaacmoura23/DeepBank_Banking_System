package com.Deepbank.SistemaBancario.config;

import com.Deepbank.SistemaBancario.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.equals("/auth/gerarToken")
        || path.startsWith("/swagger-ui")
        || path.startsWith("/v2/api-docs")
        || path.startsWith("/v3/api-docs")
        || path.startsWith("/swagger-resources")
        || path.startsWith("/webjars"))
        {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");

            try {
                var jwtValidador = tokenService.verificarToken(token);

                String email = jwtValidador.getSubject();

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println("Token invalido!");
                return;
            }
        }
        filterChain.doFilter(request, response);

    }
}
