import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class AdminAuthService {
  private base = '/api/admin/auth';
  private loggedIn$ = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient) {
    // On service init, check session
    this.me().subscribe({
      next: () => this.loggedIn$.next(true),
      error: () => this.loggedIn$.next(false)
    });
  }

  login(email: string, password: string): Observable<any> {
    const body = new HttpParams().set('email', email).set('password', password);
    const headers = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' });
    return this.http.post(`${this.base}/login`, body.toString(), {
      headers,
      withCredentials: true,
      observe: 'response'
    }).pipe(
      tap(() => this.loggedIn$.next(true))
    );
  }

  signup(payload: any): Observable<any> {
    return this.http.post(`${this.base}/signup`, payload, { withCredentials: true });
  }

  logout(): Observable<any> {
    return this.http.post(`${this.base}/logout`, {}, { withCredentials: true, observe: 'response' })
      .pipe(tap(() => this.loggedIn$.next(false)));
  }

  me(): Observable<any> {
    return this.http.get(`${this.base}/me`, { withCredentials: true });
  }

  isLoggedIn(): Observable<boolean> {
    return this.loggedIn$.asObservable();
  }

  getProfile(): Observable<any> {
    return this.http.get(`${this.base}/profile`, { withCredentials: true });
  }

  updateProfile(payload: any): Observable<any> {
    return this.http.put(`${this.base}/profile`, payload, { withCredentials: true });
  }
}
