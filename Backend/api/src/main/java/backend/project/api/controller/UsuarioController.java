package backend.project.api.controller;

import backend.project.api.dto.LoginRequest;
import backend.project.api.dto.LoginResponse;
import backend.project.api.model.Usuario;
import backend.project.api.service.UsuarioService;
import backend.project.api.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Indica que esta classe é um controlador REST
@CrossOrigin(origins = "http://localhost:4200") // Permite requisições CORS do frontend Angular
@RequestMapping("/api/usuarios") // Define o prefixo da URL para todas as rotas deste controlador
public class UsuarioController {

    @Autowired // Injeção de dependência automática do Spring
    private JwtUtil jwtUtil; // Utilidade para manipulação de tokens JWT

    @Autowired // Injeção de dependência automática do Spring
    private UsuarioService usuarioService; // Serviço para lógica de negócios relacionada a usuários

    // Rota para registrar um novo usuário
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioRegistrado = usuarioService.registrarUsuario(usuario); // Chama o serviço para registrar o usuário
        return ResponseEntity.ok(usuarioRegistrado); // Retorna o usuário registrado com status HTTP 200 (OK)
    }

    // Rota para autenticar um usuário e gerar um token JWT
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // Verifica se as credenciais do usuário são válidas
        boolean autenticado = usuarioService.autenticarUsuario(loginRequest.getEmail(), loginRequest.getSenha());
        if (autenticado) {
            // Se autenticado, gera um token JWT para o usuário
            String token = jwtUtil.generateToken(loginRequest.getEmail());
            return ResponseEntity.ok(new LoginResponse(token)); // Retorna o token com status HTTP 200 (OK)
        } else {
            return ResponseEntity.status(401).build(); // Retorna status HTTP 401 (Unauthorized) se a autenticação falhar
        }
    }
}