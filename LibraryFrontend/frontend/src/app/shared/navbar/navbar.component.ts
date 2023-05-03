import { Component } from '@angular/core';
import { AuthService } from 'src/app/core/service/_services/auth.service';
import { StorageService } from 'src/app/core/service/_services/storage.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  roles: string[] = [];
  isLoggedIn = false;
  username?: string;

  constructor(private storageService: StorageService, private authService: AuthService) { }

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.roles = user.roles;

      this.username = user.username;
    }
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: res => {
        console.log(res);
        this.storageService.clean();

        window.location.reload();
      },
      error: err => {
        console.log(err);
      }
    });
  }

  isUser(): boolean {
    return this.roles.includes("ROLE_USER");
  }

  isModerator(): boolean {
    return this.roles.includes("ROLE_MODERATOR");
  }

  isAdmin(): boolean {
    return this.roles.includes("ROLE_ADMIN");
  }
}
