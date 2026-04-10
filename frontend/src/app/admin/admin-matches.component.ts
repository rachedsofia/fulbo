import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AdminService, Match } from './admin.service';

@Component({
  selector: 'app-admin-matches',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="admin-page">
      <div class="page-header">
        <a routerLink="/admin" class="back-link">← Volver al Dashboard</a>
        <h1>Gestión de Partidos</h1>
      </div>

      <div class="toolbar">
        <button (click)="showForm.set(!showForm())" class="btn btn-primary">
          {{ showForm() ? 'Cancelar' : '+ Nuevo Partido' }}
        </button>
      </div>

      @if (showForm()) {
        <div class="form-card">
          <h3>{{ editingId() ? 'Editar' : 'Nuevo' }} Partido</h3>
          <div class="form-grid">
            <div class="form-group">
              <label>Club Local (ID)</label>
              <input type="number" [(ngModel)]="form.homeClubId" placeholder="ID del club local">
            </div>
            <div class="form-group">
              <label>Club Visitante (ID)</label>
              <input type="number" [(ngModel)]="form.awayClubId" placeholder="ID del club visitante">
            </div>
            <div class="form-group">
              <label>Torneo (ID)</label>
              <input type="number" [(ngModel)]="form.tournamentId" placeholder="ID del torneo">
            </div>
            <div class="form-group">
              <label>Fecha</label>
              <input type="datetime-local" [(ngModel)]="form.matchDate">
            </div>
            <div class="form-group">
              <label>Goles Local</label>
              <input type="number" [(ngModel)]="form.homeScore" min="0">
            </div>
            <div class="form-group">
              <label>Goles Visitante</label>
              <input type="number" [(ngModel)]="form.awayScore" min="0">
            </div>
            <div class="form-group">
              <label>Estado</label>
              <select [(ngModel)]="form.status">
                <option value="SCHEDULED">Programado</option>
                <option value="LIVE">En Vivo</option>
                <option value="HALFTIME">Entretiempo</option>
                <option value="FINISHED">Finalizado</option>
                <option value="POSTPONED">Pospuesto</option>
                <option value="CANCELLED">Cancelado</option>
              </select>
            </div>
            <div class="form-group">
              <label>Estadio</label>
              <input type="text" [(ngModel)]="form.stadium" placeholder="Nombre del estadio">
            </div>
            <div class="form-group">
              <label>Fecha (jornada)</label>
              <input type="number" [(ngModel)]="form.matchday" min="1">
            </div>
          </div>
          <button (click)="saveMatch()" class="btn btn-success">Guardar</button>
        </div>
      }

      @if (loading()) {
        <div class="loading">Cargando partidos...</div>
      } @else {
        <div class="table-container">
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Local</th>
                <th>Visitante</th>
                <th>Resultado</th>
                <th>Estado</th>
                <th>Estadio</th>
                <th>Fecha</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              @for (match of matches(); track match.id) {
                <tr [class.live]="match.status === 'LIVE'" [class.halftime]="match.status === 'HALFTIME'">
                  <td>{{ match.id }}</td>
                  <td>Club #{{ match.homeClubId }}</td>
                  <td>Club #{{ match.awayClubId }}</td>
                  <td class="score">{{ match.homeScore ?? '-' }} - {{ match.awayScore ?? '-' }}</td>
                  <td><span class="status-badge" [class]="match.status.toLowerCase()">{{ match.status }}</span></td>
                  <td>{{ match.stadium }}</td>
                  <td>{{ match.matchDate | date:'short' }}</td>
                  <td class="actions">
                    <button (click)="editMatch(match)" class="btn-sm btn-edit">Editar</button>
                    <button (click)="deleteMatch(match.id!)" class="btn-sm btn-delete">Eliminar</button>
                  </td>
                </tr>
              } @empty {
                <tr><td colspan="8" class="empty">No hay partidos registrados</td></tr>
              }
            </tbody>
          </table>
        </div>
      }

      @if (error()) { <div class="error-msg">{{ error() }}</div> }
      @if (success()) { <div class="success-msg">{{ success() }}</div> }
    </div>
  `,
  styles: [`
    .admin-page { padding: 24px; max-width: 1200px; margin: 0 auto; }
    .page-header { margin-bottom: 20px; }
    .back-link { color: #1b5e20; text-decoration: none; font-size: 14px; }
    h1 { margin: 8px 0 0; color: #1b5e20; }
    .toolbar { margin-bottom: 16px; }
    .btn { padding: 10px 20px; border: none; border-radius: 8px; cursor: pointer; font-weight: 500; font-size: 14px; }
    .btn-primary { background: #1b5e20; color: white; }
    .btn-success { background: #2e7d32; color: white; margin-top: 12px; }
    .form-card { background: #fff; padding: 20px; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); margin-bottom: 20px; }
    .form-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 12px; }
    .form-group { display: flex; flex-direction: column; gap: 4px; }
    .form-group label { font-size: 13px; font-weight: 600; color: #555; }
    .form-group input, .form-group select { padding: 8px 12px; border: 1px solid #ddd; border-radius: 6px; font-size: 14px; }
    .table-container { overflow-x: auto; }
    table { width: 100%; border-collapse: collapse; background: #fff; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
    th { background: #1b5e20; color: white; padding: 12px; text-align: left; font-size: 13px; }
    td { padding: 10px 12px; border-bottom: 1px solid #eee; font-size: 14px; }
    tr.live { background: #fff3e0; }
    tr.halftime { background: #e3f2fd; }
    .score { font-weight: 700; font-size: 16px; text-align: center; }
    .status-badge { padding: 4px 10px; border-radius: 12px; font-size: 12px; font-weight: 600; }
    .scheduled { background: #e0e0e0; color: #333; }
    .live { background: #ff5252; color: white; }
    .halftime { background: #ff9800; color: white; }
    .finished { background: #4caf50; color: white; }
    .postponed { background: #9e9e9e; color: white; }
    .cancelled { background: #f44336; color: white; }
    .actions { display: flex; gap: 6px; }
    .btn-sm { padding: 4px 10px; border: none; border-radius: 4px; cursor: pointer; font-size: 12px; }
    .btn-edit { background: #1976d2; color: white; }
    .btn-delete { background: #d32f2f; color: white; }
    .empty { text-align: center; color: #999; padding: 24px; }
    .loading { text-align: center; padding: 40px; color: #666; }
    .error-msg { background: #ffebee; color: #c62828; padding: 12px; border-radius: 8px; margin-top: 12px; }
    .success-msg { background: #e8f5e9; color: #2e7d32; padding: 12px; border-radius: 8px; margin-top: 12px; }
  `]
})
export class AdminMatchesComponent implements OnInit {
  private adminService = inject(AdminService);
  matches = signal<Match[]>([]);
  loading = signal(true);
  showForm = signal(false);
  editingId = signal<number | null>(null);
  error = signal('');
  success = signal('');

  form: Match = { homeClubId: 0, awayClubId: 0, tournamentId: 1, matchDate: '', homeScore: 0, awayScore: 0, status: 'SCHEDULED', stadium: '', matchday: 1 };

  ngOnInit() { this.loadMatches(); }

  loadMatches() {
    this.adminService.getMatches().subscribe({
      next: (data) => { this.matches.set(data); this.loading.set(false); },
      error: (err) => { this.error.set('Error: ' + (err.error?.message || err.message)); this.loading.set(false); }
    });
  }

  editMatch(match: Match) {
    this.form = { ...match };
    this.editingId.set(match.id!);
    this.showForm.set(true);
  }

  saveMatch() {
    const obs = this.editingId()
      ? this.adminService.updateMatch(this.editingId()!, this.form)
      : this.adminService.createMatch(this.form);

    obs.subscribe({
      next: () => {
        this.success.set(this.editingId() ? 'Partido actualizado' : 'Partido creado');
        this.resetForm();
        this.loadMatches();
        setTimeout(() => this.success.set(''), 3000);
      },
      error: (err) => this.error.set('Error: ' + (err.error?.message || err.message))
    });
  }

  deleteMatch(id: number) {
    if (confirm('¿Eliminar este partido?')) {
      this.adminService.deleteMatch(id).subscribe({
        next: () => { this.success.set('Partido eliminado'); this.loadMatches(); setTimeout(() => this.success.set(''), 3000); },
        error: (err) => this.error.set('Error: ' + (err.error?.message || err.message))
      });
    }
  }

  resetForm() {
    this.form = { homeClubId: 0, awayClubId: 0, tournamentId: 1, matchDate: '', homeScore: 0, awayScore: 0, status: 'SCHEDULED', stadium: '', matchday: 1 };
    this.editingId.set(null);
    this.showForm.set(false);
  }
}
