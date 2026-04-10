import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { FantasyService } from '../../core/services/fantasy.service';
import { FantasyLeague } from '../../models/fantasy.model';

@Component({
  selector: 'app-fantasy-league',
  standalone: true,
  imports: [FormsModule, RouterLink],
  template: `
    <div class="leagues-container">
      <h1>Ligas Fantasy</h1>
      <div class="actions-row">
        <div class="join-form">
          <input [(ngModel)]="joinCode" placeholder="Código de liga privada" class="input">
          <button (click)="joinLeague()" [disabled]="!joinCode.trim()" class="btn btn-outline">Unirse</button>
        </div>
        <a routerLink="/fantasy/team" class="btn btn-primary">Mi Equipo</a>
      </div>
      <h2>Ligas Públicas</h2>
      @for (league of leagues(); track league.id) {
        <div class="league-card">
          <div class="league-info">
            <h3>{{ league.name }}</h3>
            <span class="league-type">{{ league.type }}</span>
          </div>
          <div class="league-meta">
            <span>Máx: {{ league.maxTeams }} equipos</span>
            <a [routerLink]="['/fantasy/ranking', league.id]" class="btn btn-sm">Ver Ranking</a>
          </div>
        </div>
      }
      @if (leagues().length === 0 && !loading()) {
        <p class="empty">No hay ligas públicas disponibles.</p>
      }
    </div>
  `,
  styles: [`
    .leagues-container { max-width: 800px; margin: 0 auto; padding: 1.5rem; }
    h1 { color: #00d4ff; margin-bottom: 1rem; }
    h2 { color: white; margin: 1.5rem 0 1rem; }
    .actions-row {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 1.5rem;
      flex-wrap: wrap;
      gap: 1rem;
    }
    .join-form { display: flex; gap: 0.5rem; }
    .input {
      padding: 0.5rem 0.75rem;
      border: 1px solid #333;
      border-radius: 8px;
      background: #0f3460;
      color: white;
    }
    .league-card {
      background: #16213e;
      border-radius: 10px;
      padding: 1rem 1.25rem;
      margin-bottom: 0.75rem;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    .league-info h3 { color: white; margin: 0 0 0.25rem; }
    .league-type { color: #00d4ff; font-size: 0.8rem; }
    .league-meta { display: flex; align-items: center; gap: 1rem; color: #888; font-size: 0.85rem; }
    .btn {
      padding: 0.4rem 1rem;
      border-radius: 6px;
      font-weight: 600;
      cursor: pointer;
      text-decoration: none;
      border: none;
    }
    .btn-primary { background: #00d4ff; color: #1a1a2e; }
    .btn-outline { background: transparent; border: 1px solid #00d4ff; color: #00d4ff; }
    .btn-sm { background: #0f3460; color: #00d4ff; font-size: 0.8rem; padding: 0.3rem 0.75rem; }
    .empty { color: #666; text-align: center; padding: 2rem; }
  `]
})
export class FantasyLeagueComponent implements OnInit {
  leagues = signal<FantasyLeague[]>([]);
  loading = signal(false);
  joinCode = '';

  constructor(private fantasyService: FantasyService) {}

  ngOnInit(): void {
    this.loading.set(true);
    this.fantasyService.getPublicLeagues().subscribe({
      next: (leagues) => {
        this.leagues.set(leagues);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  joinLeague(): void {
    this.fantasyService.joinLeague(this.joinCode).subscribe({
      next: () => {
        this.joinCode = '';
        alert('Te uniste a la liga exitosamente');
      },
      error: (err) => alert(err.error?.message || 'Error al unirse')
    });
  }
}
