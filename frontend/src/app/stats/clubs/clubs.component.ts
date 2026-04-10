import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { StatsService } from '../../core/services/stats.service';
import { Club } from '../../models/stats.model';

@Component({
  selector: 'app-clubs',
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="clubs-container">
      <h1>Clubes</h1>
      <div class="clubs-grid">
        @for (club of clubs(); track club.id) {
          <a [routerLink]="['/stats/clubs', club.id]" class="club-card">
            <div class="club-logo">{{ club.shortName }}</div>
            <h3>{{ club.name }}</h3>
            <p class="club-info">{{ club.city }} · {{ club.stadiumName }}</p>
            <p class="club-year">Fundado: {{ club.foundedYear }}</p>
          </a>
        }
      </div>
      @if (clubs().length === 0 && !loading()) {
        <p class="empty">No hay clubes cargados todavía.</p>
      }
    </div>
  `,
  styles: [`
    .clubs-container { max-width: 900px; margin: 0 auto; padding: 1.5rem; }
    h1 { color: #00d4ff; margin-bottom: 1.5rem; }
    .clubs-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
      gap: 1rem;
    }
    .club-card {
      background: #16213e;
      border-radius: 12px;
      padding: 1.25rem;
      text-decoration: none;
      transition: transform 0.2s, box-shadow 0.2s;
      cursor: pointer;
    }
    .club-card:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 16px rgba(0, 212, 255, 0.15);
    }
    .club-logo {
      background: #0f3460;
      color: #00d4ff;
      width: 60px;
      height: 60px;
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-weight: 700;
      font-size: 1.1rem;
      margin-bottom: 0.75rem;
    }
    h3 { color: white; margin: 0 0 0.3rem; }
    .club-info { color: #888; font-size: 0.85rem; margin: 0; }
    .club-year { color: #666; font-size: 0.8rem; margin: 0.25rem 0 0; }
    .empty { color: #666; text-align: center; padding: 3rem; }
  `]
})
export class ClubsComponent implements OnInit {
  clubs = signal<Club[]>([]);
  loading = signal(false);

  constructor(private statsService: StatsService) {}

  ngOnInit(): void {
    this.loading.set(true);
    this.statsService.getAllClubs().subscribe({
      next: (clubs) => {
        this.clubs.set(clubs);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }
}
