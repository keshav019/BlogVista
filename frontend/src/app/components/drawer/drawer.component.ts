import { Component, EventEmitter, Input, Output } from '@angular/core';
import { User } from 'src/app/model/user';

@Component({
  selector: 'app-drawer',
  templateUrl: './drawer.component.html',
  styleUrls: ['./drawer.component.css'],
})
export class DrawerComponent {
  @Input() userData!: User;
  @Input() notifications: string[] = [];
  @Output() logout: EventEmitter<boolean> = new EventEmitter<boolean>();

  logoutHandler() {
    this.logout.emit();
  }
}
