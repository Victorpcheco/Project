package backend.project.api.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component // Indica que esta classe é um componente gerenciado pelo Spring
public class JwtUtil {

    @Value("${jwt.secret}") // Injeta o valor da propriedade jwt.secret do arquivo de configuração
    private String secret;

    @Value("${jwt.expiration}") // Injeta o valor da propriedade jwt.expiration do arquivo de configuração
    private Long expiration;

    private SecretKey key; // Chave secreta usada para assinar e verificar tokens JWT

    @PostConstruct // Metodo executado após a injeção de dependências para inicializar a chave secreta
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Metodo para gerar um token JWT
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Define o assunto (subject) do token (neste caso, o email do usuário)
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Define a data de expiração do token
                .signWith(key, SignatureAlgorithm.HS512) // Assina o token com a chave secreta e o algoritmo HS512
                .compact(); // Constrói o token e o converte em uma string
    }

    // Metodo para validar um token JWT
    public boolean validateToken(String token) {
        Claims claims = getClaims(token); // Extrai as reivindicações (claims) do token
        if (claims != null) {
            String email = claims.getSubject(); // Obtém o email (subject) do token
            Date expirationDate = claims.getExpiration(); // Obtém a data de expiração do token
            Date now = new Date(System.currentTimeMillis()); // Obtém a data atual
            return email != null && expirationDate != null && now.before(expirationDate); // Verifica se o token é válido
        }
        return false; // Retorna false se o token for inválido
    }

    // Metodo para extrair as reivindicações (claims) de um token JWT
    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret.getBytes()) // Define a chave secreta para verificar a assinatura do token
                    .parseClaimsJws(token) // Faz o parsing do token e verifica a assinatura
                    .getBody(); // Retorna as reivindicações (claims) do token
        } catch (Exception e) {
            return null; // Retorna null em caso de erro (token inválido)
        }
    }
}