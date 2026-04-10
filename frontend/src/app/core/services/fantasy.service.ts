import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  FantasyTeam, FantasyLeague,
  CreateFantasyTeamRequest, AddPlayerRequest, CreateLeagueRequest
} from '../../models/fantasy.model';

@Injectable({ providedIn: 'root' })
export class FantasyService {
  private readonly apiUrl = `${environment.apiUrl}/fantasy`;

  constructor(private http: HttpClient) {}

  createTeam(request: CreateFantasyTeamRequest): Observable<FantasyTeam> {
    return this.http.post<FantasyTeam>(`${this.apiUrl}/teams`, request);
  }

  getMyTeam(): Observable<FantasyTeam> {
    return this.http.get<FantasyTeam>(`${this.apiUrl}/teams/me`);
  }

  getTeam(teamId: number): Observable<FantasyTeam> {
    return this.http.get<FantasyTeam>(`${this.apiUrl}/teams/${teamId}`);
  }

  addPlayer(teamId: number, request: AddPlayerRequest): Observable<FantasyTeam> {
    return this.http.post<FantasyTeam>(`${this.apiUrl}/teams/${teamId}/players`, request);
  }

  removePlayer(teamId: number, playerId: number): Observable<FantasyTeam> {
    return this.http.delete<FantasyTeam>(`${this.apiUrl}/teams/${teamId}/players/${playerId}`);
  }

  getPublicLeagues(): Observable<FantasyLeague[]> {
    return this.http.get<FantasyLeague[]>(`${this.apiUrl}/leagues/public`);
  }

  getLeague(leagueId: number): Observable<FantasyLeague> {
    return this.http.get<FantasyLeague>(`${this.apiUrl}/leagues/${leagueId}`);
  }

  createLeague(request: CreateLeagueRequest): Observable<FantasyLeague> {
    return this.http.post<FantasyLeague>(`${this.apiUrl}/leagues`, request);
  }

  joinLeague(code: string): Observable<FantasyLeague> {
    return this.http.post<FantasyLeague>(`${this.apiUrl}/leagues/join?code=${code}`, {});
  }

  getLeagueRanking(leagueId: number): Observable<FantasyTeam[]> {
    return this.http.get<FantasyTeam[]>(`${this.apiUrl}/leagues/${leagueId}/ranking`);
  }
}
