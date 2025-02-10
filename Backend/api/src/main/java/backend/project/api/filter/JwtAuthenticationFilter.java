package backend.project.api.filter;

import backend.project.api.util.JwtUtil;
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

@Component // Indica que esta classe é um componente gerenciado pelo Spring
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired // Injeção de dependência automática do Spring
    private JwtUtil jwtUtil; // Utilitário para manipulação de tokens JWT

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Obtém o token do cabeçalho "Authorization" da requisição
        String token = request.getHeader("Authorization");

        // Verifica se o token existe e começa com "Bearer "
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove o prefixo "Bearer " para obter o token JWT puro

            // Valida o token
            if (jwtUtil.validateToken(token)) {
                // Extrai o email (subject) do token
                String email = jwtUtil.getClaims(token).getSubject();

                // Cria uma autenticação para o usuário
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList())
                );
            }
        }

        // Continua a cadeia de filtros
        filterChain.doFilter(request, response);
    }
}