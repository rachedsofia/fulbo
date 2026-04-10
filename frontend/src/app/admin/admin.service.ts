import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AdminDashboard {
  totalUsers: number;
  totalClubs: number;
  totalPlayers: number;
  totalMatches: number;
  totalTournaments: number;
  totalPosts: number;
  liveMatches: number;
}

export interface Club {
  id?: number;
  name: string;
  shortName: string;
  logoUrl: string;
  stadiumName: string;
  city: string;
  foundedYear: number;
}

export interface Player {
  id?: number;
  clubId: number;
  firstName: string;
  lastName: string;
  position: string;
  nationality: string;
  shirtNumber: number;
  photoUrl: string;
  marketValue: number;
}

export interface Match {
  id?: number;
  homeClubId: number;
  awayClubId: number;
  tournamentId: number;
  matchDate: string;
  homeScore: number;
  awayScore: number;
  status: string;
  stadium: string;
  matchday: number;
}

export interface Tournament {
  id?: number;
  name: string;
  season: string;
  type: string;
  startDate: string;
  endDate: string;
}

export interface User {
  id: number;
  username: string;
  email: string;
  displayName: string;
  role: string;
  active: boolean;
  reputation: number;
  createdAt: string;
}

@Injectable({ providedIn: 'root' })
export class AdminService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/admin';

  // Dashboard
  getDashboard(): Observable<AdminDashboard> {
    return this.http.get<AdminDashboard>(`${this.baseUrl}/dashboard`);
  }

  // Players
  getPlayers(): Observable<Player[]> {
    return this.http.get<Player[]>(`${this.baseUrl}/players`);
  }

  createPlayer(player: Player): Observable<Player> {
    return this.http.post<Player>(`${this.baseUrl}/players`, player);
  }

  updatePlayer(id: number, player: Player): Observable<Player> {
    return this.http.put<Player>(`${this.baseUrl}/players/${id}`, player);
  }

  deletePlayer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/players/${id}`);
  }

  // Clubs
  createClub(club: Club): Observable<Club> {
    return this.http.post<Club>(`${this.baseUrl}/clubs`, club);
  }

  updateClub(id: number, club: Club): Observable<Club> {
    return this.http.put<Club>(`${this.baseUrl}/clubs/${id}`, club);
  }

  deleteClub(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/clubs/${id}`);
  }

  // Matches
  getMatches(): Observable<Match[]> {
    return this.http.get<Match[]>(`${this.baseUrl}/matches`);
  }

  createMatch(match: Match): Observable<Match> {
    return this.http.post<Match>(`${this.baseUrl}/matches`, match);
  }

  updateMatch(id: number, match: Match): Observable<Match> {
    return this.http.put<Match>(`${this.baseUrl}/matches/${id}`, match);
  }

  updateMatchScore(id: number, homeScore: number, awayScore: number, status: string): Observable<Match> {
    return this.http.put<Match>(`${this.baseUrl}/matches/${id}/score`, { homeScore, awayScore, status });
  }

  deleteMatch(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/matches/${id}`);
  }

  // Tournaments
  getTournaments(): Observable<Tournament[]> {
    return this.http.get<Tournament[]>(`${this.baseUrl}/tournaments`);
  }

  createTournament(tournament: Tournament): Observable<Tournament> {
    return this.http.post<Tournament>(`${this.baseUrl}/tournaments`, tournament);
  }

  updateTournament(id: number, tournament: Tournament): Observable<Tournament> {
    return this.http.put<Tournament>(`${this.baseUrl}/tournaments/${id}`, tournament);
  }

  deleteTournament(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/tournaments/${id}`);
  }

  // Users
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/users`);
  }

  updateUserRole(id: number, role: string): Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/users/${id}/role`, { role });
  }

  deactivateUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/users/${id}`);
  }

  // Football API Sync
  syncLiveData(): Observable<Record<string, unknown>> {
    return this.http.post<Record<string, unknown>>(`${this.baseUrl}/sync-live-data`, {});
  }

  syncCompetitions(): Observable<Record<string, unknown>> {
    return this.http.post<Record<string, unknown>>(`${this.baseUrl}/sync-competitions`, {});
  }

  getApiStatus(): Observable<Record<string, unknown>> {
    return this.http.get<Record<string, unknown>>(`${this.baseUrl}/football-api/status`);
  }
}
