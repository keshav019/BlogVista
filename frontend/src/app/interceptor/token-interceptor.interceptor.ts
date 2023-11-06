import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class TokenInterceptorInterceptor implements HttpInterceptor {

  constructor() { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const user = localStorage.getItem('user');
    if (user) {
      const userJson = JSON.parse(user);
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${userJson?.token}`
        }
      });
    }

    return next.handle(request);
  }
}
