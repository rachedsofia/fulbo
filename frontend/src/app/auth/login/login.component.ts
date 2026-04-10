import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterLink],
  template: `
    <div class="auth-container">
      <div class="auth-card">
        <h2>Ingresar a Fulbo</h2>
        <form (ngSubmit)="onSubmit()">
          <div class="form-group">
            <label for="email">Email</label>
            <input id="email" type="email" [(ngModel)]="email" name="email"
                   placeholder="tu&#64;email.com" required>
          </div>
          <div class="form-group">
            <label for="password">Contraseña</label>
            <input id="password" type="password" [(ngModel)]="password" name="password"
                   placeholder="Tu contraseña" required>
          </div>
          @if (error()) {
            <div class="error">{{ error() }}</div>
          }
          <button type="submit" class="btn btn-primary" [disabled]="loading()">
            {{ loading() ? 'Ingresando...' : 'Ingresar' }}
          </button>
        </form>
        <p class="auth-link">
          ¿No tenés cuenta? <a routerLink="/auth/register">Registrate</a>
        </p>
      </div>
    </div>
  `,
  styles: [`
    .auth-container {
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: calc(100vh - 120px);
      padding: 2rem;
    }
    .auth-card {
      background: #16213e;
      border-radius: 12px;
      padding: 2.5rem;
      width: 100%;
      max-width: 420px;
      box-shadow: 0 4px 20px rgba(0,0,0,0.3);
    }
    h2 {
      color: #00d4ff;
      margin-bottom: 1.5rem;
      text-align: center;
    }
    .form-group {
      margin-bottom: 1rem;
    }
    label {
      display: block;
      color: #aaa;
      margin-bottom: 0.3rem;
      font-size: 0.9rem;
    }
    input {
      width: 100%;
      padding: 0.7rem;
      border: 1px solid #333;
      border-radius: 8px;
      background: #0f3460;
      color: white;
      font-size: 1rem;
      box-sizing: border-box;
    }
    input:focus {
      outline: none;
      border-color: #00d4ff;
    }
    .error {
      color: #ff6b6b;
      font-size: 0.85rem;
      margin-bottom: 0.75rem;
    }
    .btn {
      width: 100%;
      padding: 0.75rem;
      border: none;
      border-radius: 8px;
      font-size: 1rem;
      font-weight: 600;
      cursor: pointer;
    }
    .btn-primary {
      background: #00d4ff;
      color: #1a1a2e;
    }
    .btn-primary:disabled {
      opacity: 0.6;
    }
    .auth-link {
      text-align: center;
      margin-top: 1rem;
      color: #888;
    }
    .auth-link a {
      color: #00d4ff;
      text-decoration: none;
    }
  `]
})
export class LoginComponent {
  email = '';
  password = '';
  loading = signal(false);
  error = signal('');

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    this.loading.set(true);
    this.error.set('');
    this.authService.login({ email: this.email, password: this.password }).subscribe({
      next: () => {
        this.router.navigate(['/feed']);
      },
      error: (err) => {
        this.loading.set(false);
        this.error.set(err.error?.message || 'Error al ingresar');
      }
    });
  }
}
