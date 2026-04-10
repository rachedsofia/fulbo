import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';
import { StatsService } from '../../core/services/stats.service';
import { Match } from '../../models/stats.model';

@Component({
  selector: 'app-match-detail',
  standalone: true,
  imports: [DatePipe],
  template: `
    <div class="match-container">
      @if (match()) {
        <div class="match-card">
          <div class="match-status">{{ match()!.status }}</div>
          <div class="match-score">
            <div class="team">
              <span class="team-name">Local #{{ match()!.homeClubId }}</span>
            </div>
            <div class="score">
              <span>{{ match()!.homeScore ?? '-' }}</span>
              <span class="divider">:</span>
              <span>{{ match()!.awayScore ?? '-' }}</span>
            </div>
            <div class="team">
              <span class="team-name">Visitante #{{ match()!.awayClubId }}</span>
            </div>
          </div>
          <div class="match-info">
            <span>{{ match()!.matchDate | date:'dd/MM/yyyy HH:mm' }}</span>
            <span>{{ match()!.stadium }}</span>
            @if (match()!.matchday) {
              <span>Fecha {{ match()!.matchday }}</span>
            }
          </div>
        </div>
      }
    </div>
  `,
  styles: [`
    .match-container { max-width: 600px; margin: 0 auto; padding: 1.5rem; }
    .match-card {
      background: #16213e;
      border-radius: 12px;
      padding: 2rem;
      text-align: center;
    }
    .match-status {
      color: #00d4ff;
      font-size: 0.85rem;
      font-weight: 600;
      margin-bottom: 1.5rem;
      text-transform: uppercase;
    }
    .match-score {
      display: flex;
      justify-content: center;
      align-items: center;
      gap: 2rem;
      margin-bottom: 1.5rem;
    }
    .team-name { color: white; font-weight: 600; font-size: 1.1rem; }
    .score {
      display: flex;
      align-items: center;
      gap: 0.5rem;
      font-size: 2.5rem;
      font-weight: 700;
      color: #00d4ff;
    }
    .divider { color: #555; }
    .match-info {
      display: flex;
      justify-content: center;
      gap: 1.5rem;
      color: #888;
      font-size: 0.85rem;
    }
  `]
})
export class MatchDetailComponent implements OnInit {
  match = signal<Match | null>(null);

  constructor(
    private route: ActivatedRoute,
    private statsService: StatsService
  ) {}

  ngOnInit(): void {
    const matchId = Number(this.route.snapshot.paramMap.get('id'));
    if (matchId) {
      this.statsService.getMatch(matchId).subscribe({
        next: (match) => this.match.set(match)
      });
    }
  }
}
