package backend.project.api.service;

import backend.project.api.model.Usuario;
import backend.project.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private  UsuarioRepository usuarioRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public boolean autenticarUsuario(String email, String senha) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            System.out.println("Usuário encontrado: " + usuario.getEmail());
            boolean senhaCorreta = passwordEncoder.matches(senha, usuario.getSenha());
            System.out.println("Senha correta? " + senhaCorreta);
            return senhaCorreta;
        } else {
            System.out.println("Usuário não encontrado para o email: " + email);
            return false;
        }
    }
}
