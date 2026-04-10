import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { StatsService } from '../../core/services/stats.service';
import { Player, Club } from '../../models/stats.model';

@Component({
  selector: 'app-players',
  standalone: true,
  template: `
    <div class="players-container">
      @if (club()) {
        <h1>{{ club()!.name }} - Plantel</h1>
      }
      <div class="players-table">
        <div class="table-header">
          <span class="num">#</span>
          <span class="name">Jugador</span>
          <span class="pos">Pos</span>
          <span class="nat">Nac</span>
          <span class="val">Valor</span>
        </div>
        @for (player of players(); track player.id) {
          <div class="table-row">
            <span class="num">{{ player.shirtNumber }}</span>
            <span class="name">{{ player.firstName }} {{ player.lastName }}</span>
            <span class="pos">{{ player.position }}</span>
            <span class="nat">{{ player.nationality }}</span>
            <span class="val">{{ (player.marketValue / 1000000).toFixed(1) }}M</span>
          </div>
        }
      </div>
      @if (players().length === 0) {
        <p class="empty">No hay jugadores cargados.</p>
      }
    </div>
  `,
  styles: [`
    .players-container { max-width: 800px; margin: 0 auto; padding: 1.5rem; }
    h1 { color: #00d4ff; margin-bottom: 1.5rem; }
    .players-table { background: #16213e; border-radius: 12px; overflow: hidden; }
    .table-header, .table-row {
      display: grid;
      grid-template-columns: 50px 1fr 80px 80px 90px;
      padding: 0.75rem 1.25rem;
    }
    .table-header {
      background: #0f3460;
      color: #00d4ff;
      font-weight: 600;
      font-size: 0.85rem;
    }
    .table-row {
      border-bottom: 1px solid #1a1a3e;
      color: #ccc;
    }
    .num { font-weight: 600; }
    .name { font-weight: 500; }
    .val { color: #00d4ff; text-align: right; }
    .empty { color: #666; text-align: center; padding: 2rem; }
  `]
})
export class PlayersComponent implements OnInit {
  players = signal<Player[]>([]);
  club = signal<Club | null>(null);

  constructor(
    private route: ActivatedRoute,
    private statsService: StatsService
  ) {}

  ngOnInit(): void {
    const clubId = Number(this.route.snapshot.paramMap.get('clubId'));
    if (clubId) {
      this.statsService.getClub(clubId).subscribe({
        next: (club) => this.club.set(club)
      });
      this.statsService.getPlayersByClub(clubId).subscribe({
        next: (players) => this.players.set(players)
      });
    }
  }
}
