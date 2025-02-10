package backend.project.api.dto;

public class LoginRequest {
    // Campos da classe
    private String email; // Armazena o email do usuário
    private String senha; // Armazena a senha do usuário

    // Getters e Setters

    // Getter para o campo email
    public String getEmail() {
        return email;
    }

    // Setter para o campo email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter para o campo senha
    public String getSenha() {
        return senha;
    }

    // Setter para o campo senha
    public void setSenha(String senha) {
        this.senha = senha;
    }
}