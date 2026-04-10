import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AdminService, Tournament } from './admin.service';

@Component({
  selector: 'app-admin-tournaments',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="admin-page">
      <div class="page-header">
        <a routerLink="/admin" class="back-link">← Volver al Dashboard</a>
        <h1>Gestión de Torneos</h1>
      </div>

      <div class="toolbar">
        <button (click)="showForm.set(!showForm())" class="btn btn-primary">
          {{ showForm() ? 'Cancelar' : '+ Nuevo Torneo' }}
        </button>
      </div>

      @if (showForm()) {
        <div class="form-card">
          <h3>{{ editingId() ? 'Editar' : 'Nuevo' }} Torneo</h3>
          <div class="form-grid">
            <div class="form-group">
              <label>Nombre</label>
              <input type="text" [(ngModel)]="form.name" placeholder="Liga Profesional">
            </div>
            <div class="form-group">
              <label>Temporada</label>
              <input type="text" [(ngModel)]="form.season" placeholder="2026">
            </div>
            <div class="form-group">
              <label>Tipo</label>
              <select [(ngModel)]="form.type">
                <option value="LEAGUE">Liga</option>
                <option value="CUP">Copa</option>
                <option value="INTERNATIONAL">Internacional</option>
              </select>
            </div>
            <div class="form-group">
              <label>Fecha Inicio</label>
              <input type="date" [(ngModel)]="form.startDate">
            </div>
            <div class="form-group">
              <label>Fecha Fin</label>
              <input type="date" [(ngModel)]="form.endDate">
            </div>
          </div>
          <button (click)="saveTournament()" class="btn btn-success">Guardar</button>
        </div>
      }

      @if (loading()) {
        <div class="loading">Cargando torneos...</div>
      } @else {
        <div class="cards-grid">
          @for (t of tournaments(); track t.id) {
            <div class="tournament-card">
              <div class="card-header">
                <span class="type-badge" [class]="t.type.toLowerCase()">{{ getTypeLabel(t.type) }}</span>
                <span class="card-id">#{{ t.id }}</span>
              </div>
              <h3>{{ t.name }}</h3>
              <div class="card-info">
                <div>Temporada: <strong>{{ t.season }}</strong></div>
                <div>Inicio: {{ t.startDate }}</div>
                <div>Fin: {{ t.endDate }}</div>
              </div>
              <div class="card-actions">
                <button (click)="editTournament(t)" class="btn-sm btn-edit">Editar</button>
                <button (click)="deleteTournament(t.id!)" class="btn-sm btn-delete">Eliminar</button>
              </div>
            </div>
          } @empty {
            <div class="empty">No hay torneos registrados</div>
          }
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
    .btn { padding: 10px 20px; border: none; border-radius: 8px; cursor: pointer; font-weight: 500; }
    .btn-primary { background: #1b5e20; color: white; }
    .btn-success { background: #2e7d32; color: white; margin-top: 12px; }
    .form-card { background: #fff; padding: 20px; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); margin-bottom: 20px; }
    .form-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 12px; }
    .form-group { display: flex; flex-direction: column; gap: 4px; }
    .form-group label { font-size: 13px; font-weight: 600; color: #555; }
    .form-group input, .form-group select { padding: 8px 12px; border: 1px solid #ddd; border-radius: 6px; }
    .cards-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 16px; }
    .tournament-card { background: #fff; border-radius: 12px; padding: 20px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
    .card-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px; }
    .card-id { color: #999; font-size: 13px; }
    .type-badge { padding: 4px 10px; border-radius: 10px; font-size: 12px; font-weight: 600; }
    .league { background: #e8f5e9; color: #2e7d32; }
    .cup { background: #fff3e0; color: #e65100; }
    .international { background: #e3f2fd; color: #1565c0; }
    .card-info { font-size: 14px; color: #555; margin: 8px 0; line-height: 1.6; }
    .card-actions { display: flex; gap: 8px; margin-top: 12px; }
    .btn-sm { padding: 6px 14px; border: none; border-radius: 6px; cursor: pointer; font-size: 13px; }
    .btn-edit { background: #1976d2; color: white; }
    .btn-delete { background: #d32f2f; color: white; }
    .empty { text-align: center; color: #999; padding: 40px; grid-column: 1 / -1; }
    .loading { text-align: center; padding: 40px; color: #666; }
    .error-msg { background: #ffebee; color: #c62828; padding: 12px; border-radius: 8px; margin-top: 12px; }
    .success-msg { background: #e8f5e9; color: #2e7d32; padding: 12px; border-radius: 8px; margin-top: 12px; }
  `]
})
export class AdminTournamentsComponent implements OnInit {
  private adminService = inject(AdminService);
  tournaments = signal<Tournament[]>([]);
  loading = signal(true);
  showForm = signal(false);
  editingId = signal<number | null>(null);
  error = signal('');
  success = signal('');

  form: Tournament = { name: '', season: '2026', type: 'LEAGUE', startDate: '', endDate: '' };

  ngOnInit() { this.loadTournaments(); }

  getTypeLabel(type: string): string {
    const labels: Record<string, string> = { LEAGUE: 'Liga', CUP: 'Copa', INTERNATIONAL: 'Internacional' };
    return labels[type] || type;
  }

  loadTournaments() {
    this.adminService.getTournaments().subscribe({
      next: (data) => { this.tournaments.set(data); this.loading.set(false); },
      error: (err) => { this.error.set('Error: ' + (err.error?.message || err.message)); this.loading.set(false); }
    });
  }

  editTournament(t: Tournament) {
    this.form = { ...t };
    this.editingId.set(t.id!);
    this.showForm.set(true);
  }

  saveTournament() {
    const obs = this.editingId()
      ? this.adminService.updateTournament(this.editingId()!, this.form)
      : this.adminService.createTournament(this.form);
    obs.subscribe({
      next: () => { this.success.set(this.editingId() ? 'Torneo actualizado' : 'Torneo creado'); this.resetForm(); this.loadTournaments(); setTimeout(() => this.success.set(''), 3000); },
      error: (err) => this.error.set('Error: ' + (err.error?.message || err.message))
    });
  }

  deleteTournament(id: number) {
    if (confirm('¿Eliminar este torneo?')) {
      this.adminService.deleteTournament(id).subscribe({
        next: () => { this.success.set('Torneo eliminado'); this.loadTournaments(); setTimeout(() => this.success.set(''), 3000); },
        error: (err) => this.error.set('Error: ' + (err.error?.message || err.message))
      });
    }
  }

  resetForm() {
    this.form = { name: '', season: '2026', type: 'LEAGUE', startDate: '', endDate: '' };
    this.editingId.set(null);
    this.showForm.set(false);
  }
}
