import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { User } from 'src/app/model/user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = `${environment.apiUrl}/api/v1/auth`;

  private userSubject = new BehaviorSubject<any | null>(null);
  user: Observable<any | null> = this.userSubject.asObservable();

  constructor(private http: HttpClient) {
    const user = localStorage.getItem('user');
    if (user) {
      this.userSubject.next(JSON.parse(user));
    }
  }

  register(data: any): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/register`, data);
  }
  verifyEmail(data: any): Observable<String> {
    return this.http.get<String>(
      `${this.baseUrl}/verify?email=${data.email}&otp=${data.otp}`
    );
  }
  login(data: any): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/login`, data);
  }

  forgotPassword(email: string): Observable<String> {
    return this.http.get<String>(`${this.baseUrl}/forgot?&email=${email}`);
  }

  resetPassword(data: any): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/reset`, data);
  }

  update(data: any): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/update`, data);
  }

  setUser(user: any) {
    if (user != null) {
      localStorage.setItem('user', JSON.stringify(user));
      this.userSubject.next(user);
    }
  }

  getUser() {
    const user = localStorage.getItem('user');
    if (user != null) {
      return JSON.parse(user);
    } else {
      return null;
    }
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    this.userSubject.next(null);
  }
}
