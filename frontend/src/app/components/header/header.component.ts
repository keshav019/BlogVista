import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from 'src/app/model/user';
import { AuthService } from 'src/app/services/auth/auth.service';
import { SearchDialogComponent } from '../search-dialog/search-dialog.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent {
  @Input() userData!: User;
  @Input() notifications: string[] = [];
  @Input() mobileView = false;
  @Output() toggleView: EventEmitter<boolean> = new EventEmitter<boolean>();
  searchQuery!: string | null;
  displayRightContent: boolean = true;
  constructor(
    private authService: AuthService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  findResult() {
    if (this.searchQuery == null || this.searchQuery.length == 0) {
      return;
    }
    this.router.navigate(['/search'], {
      queryParams: { query: this.searchQuery },
    });
  }

  toggleMobileNav() {
    this.toggleView.emit();
  }
  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  openDialog() {
    const dialogRef = this.dialog.open(SearchDialogComponent, {
      data: {},
    });

    dialogRef.afterClosed().subscribe((result) => {
      this.searchQuery = result;
      this.findResult();
    });
  }
}
