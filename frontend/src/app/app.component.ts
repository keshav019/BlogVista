import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, ViewChild } from '@angular/core';
import { User } from './model/user';
import { Subscription } from 'rxjs';
import { AuthService } from './services/auth/auth.service';
import { MatDrawer } from '@angular/material/sidenav';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'BlogApp';
  @ViewChild('drawer') drawer!: MatDrawer;
  userData!: User;
  mobileView = false;
  notifications: string[] = [];
  dataSubscription!: Subscription;
  constructor(
    private breakpointObserver: BreakpointObserver,
    private authService: AuthService,
    private router: Router
  ) {
    this.breakpointObserver
      .observe([Breakpoints.XSmall])
      .subscribe((result) => {
        this.mobileView = result.matches;
      });
  }

  ngOnInit(): void {
    this.dataSubscription = this.authService.user.subscribe((user) => {
      this.userData = user;
    });
  }
  ngOnDestroy(): void {
    if (this.dataSubscription) {
      this.dataSubscription.unsubscribe();
    }
  }

  toggleView() {
    this.drawer.toggle();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
