import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FantasyService } from '../../core/services/fantasy.service';
import { FantasyTeam } from '../../models/fantasy.model';

@Component({
  selector: 'app-fantasy-ranking',
  standalone: true,
  template: `
    <div class="ranking-container">
      <h1>Ranking de Liga</h1>
      <div class="ranking-table">
        <div class="ranking-header">
          <span class="rank">#</span>
          <span class="name">Equipo</span>
          <span class="points">Puntos</span>
        </div>
        @for (team of teams(); track team.id; let i = $index) {
          <div class="ranking-row" [class.top3]="i < 3">
            <span class="rank">{{ i + 1 }}</span>
            <span class="name">{{ team.name }}</span>
            <span class="points">{{ team.totalPoints }}</span>
          </div>
        }
      </div>
      @if (teams().length === 0) {
        <p class="empty">No hay equipos en esta liga.</p>
      }
    </div>
  `,
  styles: [`
    .ranking-container { max-width: 680px; margin: 0 auto; padding: 1.5rem; }
    h1 { color: #00d4ff; margin-bottom: 1.5rem; }
    .ranking-table { background: #16213e; border-radius: 12px; overflow: hidden; }
    .ranking-header, .ranking-row {
      display: grid;
      grid-template-columns: 60px 1fr 100px;
      padding: 0.75rem 1.25rem;
    }
    .ranking-header {
      background: #0f3460;
      color: #00d4ff;
      font-weight: 600;
      font-size: 0.85rem;
    }
    .ranking-row {
      border-bottom: 1px solid #1a1a3e;
      color: #ccc;
    }
    .ranking-row.top3 { color: #ffd700; }
    .rank { font-weight: 700; }
    .points { text-align: right; font-weight: 600; color: #00d4ff; }
    .empty { color: #666; text-align: center; padding: 2rem; }
  `]
})
export class FantasyRankingComponent implements OnInit {
  teams = signal<FantasyTeam[]>([]);

  constructor(
    private route: ActivatedRoute,
    private fantasyService: FantasyService
  ) {}

  ngOnInit(): void {
    const leagueId = Number(this.route.snapshot.paramMap.get('leagueId'));
    if (leagueId) {
      this.fantasyService.getLeagueRanking(leagueId).subscribe({
        next: (teams) => this.teams.set(teams)
      });
    }
  }
}
