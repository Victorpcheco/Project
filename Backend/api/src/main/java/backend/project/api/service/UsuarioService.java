package backend.project.api.service;

import backend.project.api.model.Usuario;
import backend.project.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // Indica que esta classe é um serviço gerenciado pelo Spring
public class UsuarioService {

    @Autowired // Injeção de dependência automática do Spring
    private UsuarioRepository usuarioRepository; // Repositório para operações de banco de dados relacionadas a usuários

    @Autowired // Injeção de dependência automática do Spring
    private PasswordEncoder passwordEncoder; // Codificador de senhas para segurança

    // Metodo para registrar um novo usuário
    public Usuario registrarUsuario(Usuario usuario) {
        // Codifica a senha do usuário antes de salvar no banco de dados
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        // Salva o usuário no banco de dados e retorna o usuário registrado
        return usuarioRepository.save(usuario);
    }

    // Metodo para buscar um usuário pelo email
    public Optional<Usuario> buscarPorEmail(String email) {
        // Retorna um Optional contendo o usuário, se encontrado
        return usuarioRepository.findByEmail(email);
    }

    // Metodo para autenticar um usuário com base no email e senha
    public boolean autenticarUsuario(String email, String senha) {
        // Busca o usuário pelo email
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        if (usuarioOptional.isPresent()) {
            // Se o usuário for encontrado, verifica se a senha está correta
            Usuario usuario = usuarioOptional.get();
            System.out.println("Usuário encontrado: " + usuario.getEmail());
            boolean senhaCorreta = passwordEncoder.matches(senha, usuario.getSenha());
            System.out.println("Senha correta? " + senhaCorreta);
            return senhaCorreta; // Retorna true se a senha estiver correta, caso contrário, false
        } else {
            // Se o usuário não for encontrado, retorna false
            System.out.println("Usuário não encontrado para o email: " + email);
            return false;
        }
    }
}