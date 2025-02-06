import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:3000/api'; // Substitua pela URL do seu backend

  constructor(private http: HttpClient) {}

  login(credentials: { email: string, password: string }): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.apiUrl}/login`, credentials);
  }

  register(user: { email: string, password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user);
  }
}