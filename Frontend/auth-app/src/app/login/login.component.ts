import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms'; // Importa o módulo de formulários do Angular
import { Router, RouterModule } from '@angular/router'; // Importa o módulo de roteamento do Angular
import { AuthService } from '../services/auth.service'; // Importa o serviço de autenticação

@Component({
  selector: 'app-login', // Seletor do componente (usado no HTML)
  standalone: true, // Define o componente como standalone (não precisa ser declarado em um módulo)
  imports: [FormsModule, RouterModule], // Importa os módulos necessários
  templateUrl: './login.component.html', // Caminho para o template HTML do componente
  styleUrls: ['./login.component.scss'] // Caminho para o arquivo de estilos do componente
})
export class LoginComponent {
  // Objeto para armazenar as credenciais do usuário (e-mail e senha)
  credentials = { email: '', senha: '' };

  // Injeção de dependências: AuthService para autenticação e Router para navegação
  constructor(private authService: AuthService, private router: Router) {}

  // Método chamado quando o formulário de login é enviado
  onSubmit() {
    // Chama o método de login do AuthService, passando as credenciais
    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        // Se o login for bem-sucedido, armazena o token no localStorage
        localStorage.setItem('token', response.token);

        // Redireciona o usuário para a página inicial (dashboard)
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        // Se ocorrer um erro, exibe no console
        console.error('Login failed', err);
      }
    });
  }
}