import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root' // O serviço é fornecido no nível raiz da aplicação
})
export class AuthService {
  // URL base da API do backend (substitua pela URL correta do seu backend)
  private apiUrl = 'http://localhost:8080/api/usuarios';

  // Injeção do HttpClient para fazer requisições HTTP
  constructor(private http: HttpClient) {}

  // Método para realizar o login
  login(credentials: { email: string, senha: string }): Observable<{ token: string }> {
    // Faz uma requisição POST para a rota de login, enviando as credenciais
    return this.http.post<{ token: string }>(`${this.apiUrl}/login`, credentials);
  }

  // Método para realizar o registro de um novo usuário
  register(user: { email: string, senha: string }): Observable<any> {
    // Faz uma requisição POST para a rota de registro, enviando os dados do usuário
    return this.http.post(`${this.apiUrl}/registrar`, user);
  }
}