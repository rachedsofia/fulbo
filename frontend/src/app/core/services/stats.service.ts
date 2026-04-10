import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Club, Player, Match, PlayerStats } from '../../models/stats.model';

@Injectable({ providedIn: 'root' })
export class StatsService {
  private readonly apiUrl = `${environment.apiUrl}/stats`;

  constructor(private http: HttpClient) {}

  getAllClubs(): Observable<Club[]> {
    return this.http.get<Club[]>(`${this.apiUrl}/clubs`);
  }

  getClub(id: number): Observable<Club> {
    return this.http.get<Club>(`${this.apiUrl}/clubs/${id}`);
  }

  getPlayersByClub(clubId: number): Observable<Player[]> {
    return this.http.get<Player[]>(`${this.apiUrl}/clubs/${clubId}/players`);
  }

  getPlayer(id: number): Observable<Player> {
    return this.http.get<Player>(`${this.apiUrl}/players/${id}`);
  }

  getMatchesByTournament(tournamentId: number): Observable<Match[]> {
    return this.http.get<Match[]>(`${this.apiUrl}/tournaments/${tournamentId}/matches`);
  }

  getMatch(id: number): Observable<Match> {
    return this.http.get<Match>(`${this.apiUrl}/matches/${id}`);
  }

  getPlayerMatchStats(playerId: number, matchId: number): Observable<PlayerStats> {
    return this.http.get<PlayerStats>(`${this.apiUrl}/players/${playerId}/matches/${matchId}`);
  }

  getTopScorers(tournamentId: number, limit = 10): Observable<Player[]> {
    return this.http.get<Player[]>(`${this.apiUrl}/tournaments/${tournamentId}/top-scorers?limit=${limit}`);
  }
}
