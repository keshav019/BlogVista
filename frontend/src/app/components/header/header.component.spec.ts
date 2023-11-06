import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderComponent } from './header.component';
import { AuthService } from 'src/app/services/auth/auth.service';
import { HttpClientModule } from '@angular/common/http';
import { MatToolbarModule } from '@angular/material/toolbar';
import { User } from '../../model/user';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';
import { By } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
describe('HeaderComponent', () => {
  let userData: User;
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HeaderComponent],
      imports: [
        HttpClientModule,
        MatToolbarModule,
        MatIconModule,
        MatMenuModule,
        MatDividerModule,
        FormsModule,
        RouterTestingModule,
        MatDialogModule,
      ],
      providers: [
        AuthService,
        { provide: MAT_DIALOG_DATA, useValue: {} },
        { provide: MatDialogRef, useValue: {} },
      ],
    });
    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should contain a logo element with the correct routerLink', () => {
    const logoElement = fixture.nativeElement.querySelector('.logo');
    expect(logoElement).toBeTruthy();
    expect(logoElement.getAttribute('routerLink')).toBe('/');
    expect(logoElement.textContent).toEqual('BlogApp');
  });

  it('should display login and register links when userData is not provided', () => {
    component.userData = userData;

    fixture.detectChanges(); // Trigger component initialization

    const loginLink = fixture.nativeElement.querySelector(
      'a[routerLink="/login"]'
    );
    const registerLink = fixture.nativeElement.querySelector(
      'a[routerLink="/register"]'
    );

    expect(loginLink).toBeTruthy();
    expect(registerLink).toBeTruthy();
    expect(registerLink.textContent).toEqual('Register');
    expect(loginLink.textContent).toEqual('Login');
  });

  it('should display profile and notification icons when userData is provided', () => {
    component.userData = {
      firstname: 'John',
      lastname: 'Doe',
      email: 'john@gmail.com',
      token: 'kkkksahhahh',
    };

    fixture.detectChanges();

    const profileButton = fixture.nativeElement.querySelector('button.profile');
    const notificationButton = fixture.nativeElement.querySelector(
      'button.notification'
    );

    expect(profileButton).toBeTruthy();
    expect(notificationButton).toBeTruthy();
    expect(profileButton.textContent).toEqual(' J ');
    expect(notificationButton.textContent).toEqual('notifications');
  });

  it('should navigate to /search with query parameter', () => {
    const spy = spyOn(router, 'navigate'); // Spy on the router.navigate method

    component.searchQuery = 'exampleQuery'; // Set the search query

    component.findResult(); // Call the navigate method

    expect(spy).toHaveBeenCalledWith(['/search'], {
      queryParams: { query: 'exampleQuery' },
    });
  });

  it('should not navigate when searchQuery is null', () => {
    const spy = spyOn(router, 'navigate'); // Spy on the router.navigate method

    component.searchQuery = null; // Set searchQuery to null

    component.findResult(); // Call the navigate method

    expect(spy).not.toHaveBeenCalled(); // Ensure that navigate was not called
  });

  it('should not navigate when searchQuery is empty', () => {
    const spy = spyOn(router, 'navigate'); // Spy on the router.navigate method

    component.searchQuery = ''; // Set searchQuery to an empty string

    component.findResult(); // Call the navigate method

    expect(spy).not.toHaveBeenCalled(); // Ensure that navigate was not called
  });

});
