import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { Component } from '@angular/core';
import { BrowserModule, By } from '@angular/platform-browser';
import { AuthService } from './services/auth/auth.service';
import { MatSidenavModule } from '@angular/material/sidenav';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@Component({
  selector: 'app-header',
  template: `<div></div>`,
})
class MockHeader { }

@Component({
  selector: 'app-drawer',
  template: `<div></div>`,
})
class MockDrawer {}

@Component({
  selector: 'app-footer',
  template: `<div></div>`,
})
class MockFooter {}

describe('AppComponent', () => {
  let fixture: ComponentFixture<AppComponent>;
  let app: AppComponent;
  const mockAuthService = jasmine.createSpyObj<AuthService>(['logout']);
  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AppComponent, MockHeader, MockFooter, MockDrawer],
      imports: [RouterTestingModule, MatSidenavModule, BrowserAnimationsModule],
      providers: [
        {
          provide: AuthService,
          useValue: mockAuthService,
        },
      ],
    });

    fixture = TestBed.createComponent(AppComponent);
    app = fixture.componentInstance;
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'BlogApp'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('BlogApp');
  });
});
