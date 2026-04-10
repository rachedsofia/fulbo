import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AdminService } from './admin.service';

@Component({
  selector: 'app-admin-sync',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="admin-page">
      <div class="page-header">
        <a routerLink="/admin" class="back-link">← Volver al Dashboard</a>
        <h1>Sincronización de Datos en Vivo</h1>
        <p class="subtitle">Integración con football-data.org API</p>
      </div>

      <div class="status-card">
        <h3>Estado de la API</h3>
        @if (apiStatus()) {
          <div class="status-grid">
            <div class="status-item">
              <span class="label">API Configurada:</span>
              <span class="value" [class.yes]="apiStatus()!['apiConfigured']" [class.no]="!apiStatus()!['apiConfigured']">
                {{ apiStatus()!['apiConfigured'] ? 'Sí' : 'No' }}
              </span>
            </div>
            <div class="status-item">
              <span class="label">URL Base:</span>
              <span class="value">{{ apiStatus()!['baseUrl'] }}</span>
            </div>
            <div class="status-item">
              <span class="label">Última Sincronización:</span>
              <span class="value">{{ apiStatus()!['lastSyncTime'] }}</span>
            </div>
            <div class="status-item">
              <span class="label">Total Sincronizaciones:</span>
              <span class="value">{{ apiStatus()!['totalSyncs'] }}</span>
            </div>
            <div class="status-item">
              <span class="label">Límite:</span>
              <span class="value">{{ apiStatus()!['rateLimitInfo'] }}</span>
            </div>
          </div>
        } @else {
          <div class="loading">Cargando estado...</div>
        }
      </div>

      <div class="actions-row">
        <button (click)="syncLiveMatches()" [disabled]="syncing()" class="btn btn-live">
          {{ syncing() ? 'Sincronizando...' : '🔴 Sincronizar Partidos en Vivo' }}
        </button>
        <button (click)="syncCompetitions()" [disabled]="syncing()" class="btn btn-comp">
          {{ syncing() ? 'Sincronizando...' : '🏆 Sincronizar Competiciones' }}
        </button>
      </div>

      @if (syncResult()) {
        <div class="result-card">
          <h3>Resultado de Sincronización</h3>
          <div class="result-header">
            <span class="result-status" [class]="getStatusClass()">
              {{ syncResult()!['status'] }}
            </span>
            <span class="result-time">{{ syncResult()!['timestamp'] }}</span>
          </div>

          @if (syncResult()!['message']) {
            <div class="result-message">{{ syncResult()!['message'] }}</div>
          }

          @if (syncResult()!['matchesFound'] !== undefined) {
            <div class="result-stat">Partidos encontrados: <strong>{{ syncResult()!['matchesFound'] }}</strong></div>
          }

          @if (syncResult()!['demo_data']) {
            <h4>Datos de Demostración</h4>
            <div class="demo-matches">
              @for (match of getDemoMatches(); track $index) {
                <div class="demo-match">
                  <span class="match-status" [class]="match['status']?.toString()?.toLowerCase() || ''">{{ match['status'] }}</span>
                  <span class="match-teams">{{ match['homeTeam'] }} vs {{ match['awayTeam'] }}</span>
                  <span class="match-score">{{ getMatchScore(match) }}</span>
                  @if (match['minute']) {
                    <span class="match-minute">{{ match['minute'] }}'</span>
                  }
                </div>
              }
            </div>
          }

          @if (syncResult()!['competition']) {
            <h4>Competición</h4>
            <pre class="json-output">{{ syncResult()!['competition'] | json }}</pre>
          }

          @if (syncResult()!['competitions']) {
            <h4>Competiciones</h4>
            <div class="competitions-list">
              @for (comp of getCompetitions(); track $index) {
                <div class="comp-item">
                  <strong>{{ comp['name'] }}</strong> ({{ comp['code'] }}) - {{ comp['area'] }} - {{ comp['currentSeason'] }}
                </div>
              }
            </div>
          }
        </div>
      }

      @if (error()) { <div class="error-msg">{{ error() }}</div> }
    </div>
  `,
  styles: [`
    .admin-page { padding: 24px; max-width: 1000px; margin: 0 auto; }
    .page-header { margin-bottom: 24px; }
    .back-link { color: #1b5e20; text-decoration: none; font-size: 14px; }
    h1 { margin: 8px 0 4px; color: #1b5e20; }
    .subtitle { color: #666; margin: 0; }
    .status-card { background: #fff; padding: 20px; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); margin-bottom: 20px; }
    .status-grid { display: grid; gap: 10px; }
    .status-item { display: flex; gap: 8px; font-size: 14px; }
    .label { font-weight: 600; color: #555; min-width: 180px; }
    .value { color: #333; }
    .value.yes { color: #2e7d32; font-weight: 600; }
    .value.no { color: #c62828; font-weight: 600; }
    .actions-row { display: flex; gap: 12px; margin-bottom: 20px; flex-wrap: wrap; }
    .btn { padding: 14px 24px; border: none; border-radius: 10px; cursor: pointer; font-weight: 600; font-size: 15px; transition: all 0.2s; }
    .btn:disabled { opacity: 0.6; cursor: not-allowed; }
    .btn-live { background: linear-gradient(135deg, #ff5252, #d32f2f); color: white; }
    .btn-live:hover:not(:disabled) { transform: translateY(-2px); box-shadow: 0 4px 12px rgba(211,47,47,0.4); }
    .btn-comp { background: linear-gradient(135deg, #1b5e20, #2e7d32); color: white; }
    .btn-comp:hover:not(:disabled) { transform: translateY(-2px); box-shadow: 0 4px 12px rgba(46,125,50,0.4); }
    .result-card { background: #fff; padding: 20px; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
    .result-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 12px; }
    .result-status { padding: 4px 12px; border-radius: 8px; font-weight: 600; font-size: 13px; }
    .status-ok { background: #e8f5e9; color: #2e7d32; }
    .status-error { background: #ffebee; color: #c62828; }
    .status-nokey { background: #fff3e0; color: #e65100; }
    .result-time { color: #999; font-size: 13px; }
    .result-message { background: #f5f5f5; padding: 10px 14px; border-radius: 8px; font-size: 14px; margin-bottom: 12px; }
    .result-stat { font-size: 15px; margin: 8px 0; }
    h4 { margin: 16px 0 8px; color: #333; }
    .demo-matches { display: flex; flex-direction: column; gap: 8px; }
    .demo-match { display: flex; align-items: center; gap: 12px; padding: 10px 14px; background: #f5f5f5; border-radius: 8px; }
    .match-status { padding: 3px 8px; border-radius: 6px; font-size: 11px; font-weight: 700; }
    .live { background: #ff5252; color: white; }
    .halftime { background: #ff9800; color: white; }
    .match-teams { flex: 1; font-weight: 500; }
    .match-score { font-weight: 700; font-size: 16px; }
    .match-minute { color: #ff5252; font-weight: 600; }
    .competitions-list { display: flex; flex-direction: column; gap: 6px; }
    .comp-item { padding: 8px 12px; background: #f5f5f5; border-radius: 6px; font-size: 14px; }
    .json-output { background: #263238; color: #eeffff; padding: 14px; border-radius: 8px; font-size: 13px; overflow-x: auto; max-height: 300px; }
    .loading { text-align: center; padding: 20px; color: #666; }
    .error-msg { background: #ffebee; color: #c62828; padding: 12px; border-radius: 8px; margin-top: 12px; }
  `]
})
export class AdminSyncComponent implements OnInit {
  private adminService = inject(AdminService);
  apiStatus = signal<Record<string, unknown> | null>(null);
  syncResult = signal<Record<string, unknown> | null>(null);
  syncing = signal(false);
  error = signal('');

  ngOnInit() {
    this.adminService.getApiStatus().subscribe({
      next: (data) => this.apiStatus.set(data),
      error: (err) => this.error.set('Error al obtener estado: ' + (err.error?.message || err.message))
    });
  }

  getStatusClass(): string {
    const status = this.syncResult()?.['status'];
    if (status === 'OK') return 'status-ok';
    if (status === 'ERROR') return 'status-error';
    return 'status-nokey';
  }

  getMatchScore(match: Record<string, unknown>): string {
    const score = match['score'] as Record<string, unknown> | undefined;
    if (!score) return '-';
    return score['home'] + ' - ' + score['away'];
  }

  getDemoMatches(): Record<string, unknown>[] {
    const demoData = this.syncResult()?.['demo_data'];
    return Array.isArray(demoData) ? demoData : [];
  }

  getCompetitions(): Record<string, unknown>[] {
    const comps = this.syncResult()?.['competitions'];
    return Array.isArray(comps) ? comps : [];
  }

  syncLiveMatches() {
    this.syncing.set(true);
    this.error.set('');
    this.adminService.syncLiveData().subscribe({
      next: (data) => { this.syncResult.set(data); this.syncing.set(false); this.refreshStatus(); },
      error: (err) => { this.error.set('Error: ' + (err.error?.message || err.message)); this.syncing.set(false); }
    });
  }

  syncCompetitions() {
    this.syncing.set(true);
    this.error.set('');
    this.adminService.syncCompetitions().subscribe({
      next: (data) => { this.syncResult.set(data); this.syncing.set(false); this.refreshStatus(); },
      error: (err) => { this.error.set('Error: ' + (err.error?.message || err.message)); this.syncing.set(false); }
    });
  }

  private refreshStatus() {
    this.adminService.getApiStatus().subscribe({
      next: (data) => this.apiStatus.set(data)
    });
  }
}
