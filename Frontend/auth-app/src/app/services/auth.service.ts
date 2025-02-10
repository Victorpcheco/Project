import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/usuarios'; // Substitua pela URL do seu backend

  constructor(private http: HttpClient) {}

  login(credentials: { email: string, senha: string }): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.apiUrl}/login`, credentials);
  }

  register(user: { email: string, senha: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/registrar`, user);
  }
}