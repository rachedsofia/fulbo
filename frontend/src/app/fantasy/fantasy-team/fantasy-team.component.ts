import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FantasyService } from '../../core/services/fantasy.service';
import { FantasyTeam } from '../../models/fantasy.model';

@Component({
  selector: 'app-fantasy-team',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="fantasy-container">
      <h1>Mi Equipo Fantasy</h1>
      @if (team()) {
        <div class="team-card">
          <div class="team-header">
            <h2>{{ team()!.name }}</h2>
            <div class="team-stats">
              <span class="stat">Puntos: <strong>{{ team()!.totalPoints }}</strong></span>
              <span class="stat">Presupuesto: <strong>{{ (team()!.budget / 1000000).toFixed(1) }}M</strong></span>
            </div>
          </div>
          <div class="players-grid">
            @for (player of team()!.players; track player.id) {
              <div class="player-slot" [class.captain]="player.isCaptain">
                <span class="position">{{ player.position }}</span>
                <span class="player-id">Jugador #{{ player.playerId }}</span>
                @if (player.isCaptain) {
                  <span class="captain-badge">C</span>
                }
              </div>
            }
            @for (_ of emptySlots(); track $index) {
              <div class="player-slot empty">
                <span class="position">Vacío</span>
                <span class="player-id">Sin jugador</span>
              </div>
            }
          </div>
        </div>
      } @else if (!loading()) {
        <div class="create-team">
          <h3>Creá tu equipo fantasy</h3>
          <div class="form-group">
            <input [(ngModel)]="teamName" placeholder="Nombre del equipo" class="input">
          </div>
          <button (click)="createTeam()" [disabled]="!teamName.trim()" class="btn btn-primary">
            Crear Equipo
          </button>
        </div>
      }
      @if (loading()) {
        <div class="loading">Cargando...</div>
      }
    </div>
  `,
  styles: [`
    .fantasy-container { max-width: 800px; margin: 0 auto; padding: 1.5rem; }
    h1 { color: #00d4ff; margin-bottom: 1.5rem; }
    .team-card {
      background: #16213e;
      border-radius: 12px;
      padding: 1.5rem;
    }
    .team-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 1.5rem;
      flex-wrap: wrap;
      gap: 1rem;
    }
    .team-header h2 { color: white; margin: 0; }
    .team-stats { display: flex; gap: 1.5rem; }
    .stat { color: #aaa; }
    .stat strong { color: #00d4ff; }
    .players-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(140px, 1fr));
      gap: 0.75rem;
    }
    .player-slot {
      background: #0f3460;
      border-radius: 8px;
      padding: 1rem;
      text-align: center;
      border: 2px solid transparent;
      position: relative;
    }
    .player-slot.captain { border-color: #ffd700; }
    .player-slot.empty {
      border: 2px dashed #333;
      background: transparent;
      color: #555;
    }
    .position { display: block; color: #00d4ff; font-size: 0.8rem; font-weight: 600; }
    .player-id { display: block; color: #ccc; margin-top: 0.3rem; font-size: 0.85rem; }
    .captain-badge {
      position: absolute;
      top: -8px;
      right: -8px;
      background: #ffd700;
      color: #1a1a2e;
      width: 22px;
      height: 22px;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      font-weight: 700;
      font-size: 0.75rem;
    }
    .create-team {
      background: #16213e;
      border-radius: 12px;
      padding: 2rem;
      text-align: center;
    }
    .create-team h3 { color: white; margin-bottom: 1rem; }
    .input {
      width: 100%;
      max-width: 300px;
      padding: 0.7rem;
      border: 1px solid #333;
      border-radius: 8px;
      background: #0f3460;
      color: white;
      font-size: 1rem;
      margin-bottom: 1rem;
      box-sizing: border-box;
    }
    .btn {
      padding: 0.6rem 1.5rem;
      border: none;
      border-radius: 8px;
      font-weight: 600;
      cursor: pointer;
    }
    .btn-primary { background: #00d4ff; color: #1a1a2e; }
    .btn-primary:disabled { opacity: 0.5; }
    .loading { text-align: center; color: #00d4ff; padding: 2rem; }
  `]
})
export class FantasyTeamComponent implements OnInit {
  team = signal<FantasyTeam | null>(null);
  loading = signal(false);
  teamName = '';

  constructor(private fantasyService: FantasyService) {}

  ngOnInit(): void {
    this.loadTeam();
  }

  emptySlots(): number[] {
    const count = 11 - (this.team()?.players?.length || 0);
    return count > 0 ? Array(count).fill(0) : [];
  }

  loadTeam(): void {
    this.loading.set(true);
    this.fantasyService.getMyTeam().subscribe({
      next: (team) => {
        this.team.set(team);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  createTeam(): void {
    this.loading.set(true);
    this.fantasyService.createTeam({ name: this.teamName }).subscribe({
      next: (team) => {
        this.team.set(team);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }
}
