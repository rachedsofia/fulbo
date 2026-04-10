import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  template: `
    <nav class="navbar">
      <div class="navbar-brand">
        <a routerLink="/" class="logo">⚽ Fulbo</a>
      </div>
      <div class="navbar-links">
        <a routerLink="/feed" routerLinkActive="active">Feed</a>
        <a routerLink="/fantasy" routerLinkActive="active">Fantasy</a>
        <a routerLink="/stats" routerLinkActive="active">Estadísticas</a>
        <a routerLink="/admin" routerLinkActive="active" class="admin-link">Admin</a>
      </div>
      <div class="navbar-auth">
        @if (authService.isAuthenticated()) {
          <span class="username">{{ authService.user()?.displayName || authService.user()?.username }}</span>
          <button (click)="authService.logout()" class="btn btn-outline">Salir</button>
        } @else {
          <a routerLink="/auth/login" class="btn btn-primary">Ingresar</a>
          <a routerLink="/auth/register" class="btn btn-outline">Registrarse</a>
        }
      </div>
    </nav>
  `,
  styles: [`
    .navbar {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0 1.5rem;
      height: 60px;
      background: #1a1a2e;
      color: white;
      box-shadow: 0 2px 8px rgba(0,0,0,0.3);
    }
    .logo {
      font-size: 1.4rem;
      font-weight: 700;
      color: #00d4ff;
      text-decoration: none;
    }
    .navbar-links {
      display: flex;
      gap: 1.5rem;
    }
    .navbar-links a {
      color: #ccc;
      text-decoration: none;
      font-weight: 500;
      transition: color 0.2s;
    }
    .navbar-links a:hover, .navbar-links a.active {
      color: #00d4ff;
    }
    .navbar-auth {
      display: flex;
      align-items: center;
      gap: 0.75rem;
    }
    .username {
      font-size: 0.9rem;
      color: #aaa;
    }
    .btn {
      padding: 0.4rem 1rem;
      border-radius: 6px;
      font-size: 0.85rem;
      font-weight: 600;
      cursor: pointer;
      text-decoration: none;
      border: none;
    }
    .btn-primary {
      background: #00d4ff;
      color: #1a1a2e;
    }
    .btn-outline {
      background: transparent;
      border: 1px solid #00d4ff;
      color: #00d4ff;
    }
    .admin-link {
      color: #ff9800 !important;
      font-weight: 600;
    }
    .admin-link.active {
      color: #ffb74d !important;
    }
  `]
})
export class HeaderComponent {
  constructor(public authService: AuthService) {}
}
