import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AdminService, Player } from './admin.service';

@Component({
  selector: 'app-admin-players',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="admin-page">
      <div class="page-header">
        <a routerLink="/admin" class="back-link">← Volver al Dashboard</a>
        <h1>Gestión de Jugadores</h1>
      </div>

      <div class="toolbar">
        <button (click)="showForm.set(!showForm())" class="btn btn-primary">
          {{ showForm() ? 'Cancelar' : '+ Nuevo Jugador' }}
        </button>
        <input type="text" [(ngModel)]="searchTerm" placeholder="Buscar jugador..." class="search-input">
      </div>

      @if (showForm()) {
        <div class="form-card">
          <h3>{{ editingId() ? 'Editar' : 'Nuevo' }} Jugador</h3>
          <div class="form-grid">
            <div class="form-group">
              <label>Nombre</label>
              <input type="text" [(ngModel)]="form.firstName" placeholder="Nombre">
            </div>
            <div class="form-group">
              <label>Apellido</label>
              <input type="text" [(ngModel)]="form.lastName" placeholder="Apellido">
            </div>
            <div class="form-group">
              <label>Club (ID)</label>
              <input type="number" [(ngModel)]="form.clubId">
            </div>
            <div class="form-group">
              <label>Posición</label>
              <select [(ngModel)]="form.position">
                <option value="Portero">Portero</option>
                <option value="Defensor">Defensor</option>
                <option value="Mediocampista">Mediocampista</option>
                <option value="Delantero">Delantero</option>
              </select>
            </div>
            <div class="form-group">
              <label>Nacionalidad</label>
              <input type="text" [(ngModel)]="form.nationality" placeholder="Argentina">
            </div>
            <div class="form-group">
              <label>Dorsal</label>
              <input type="number" [(ngModel)]="form.shirtNumber" min="1" max="99">
            </div>
            <div class="form-group">
              <label>Valor de Mercado (M USD)</label>
              <input type="number" [(ngModel)]="form.marketValue" step="0.1">
            </div>
          </div>
          <button (click)="savePlayer()" class="btn btn-success">Guardar</button>
        </div>
      }

      @if (loading()) {
        <div class="loading">Cargando jugadores...</div>
      } @else {
        <div class="table-container">
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Posición</th>
                <th>Club</th>
                <th>Dorsal</th>
                <th>Nacionalidad</th>
                <th>Valor</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              @for (player of filteredPlayers(); track player.id) {
                <tr>
                  <td>{{ player.id }}</td>
                  <td class="name">{{ player.firstName }} {{ player.lastName }}</td>
                  <td><span class="position-badge" [class]="getPositionClass(player.position)">{{ player.position }}</span></td>
                  <td>Club #{{ player.clubId }}</td>
                  <td class="number">{{ player.shirtNumber }}</td>
                  <td>{{ player.nationality }}</td>
                  <td class="value">{{ player.marketValue | number:'1.1-1' }}M</td>
                  <td class="actions">
                    <button (click)="editPlayer(player)" class="btn-sm btn-edit">Editar</button>
                    <button (click)="deletePlayer(player.id!)" class="btn-sm btn-delete">Eliminar</button>
                  </td>
                </tr>
              } @empty {
                <tr><td colspan="8" class="empty">No hay jugadores registrados</td></tr>
              }
            </tbody>
          </table>
        </div>
        <div class="count">Total: {{ players().length }} jugadores</div>
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
    .toolbar { display: flex; gap: 12px; margin-bottom: 16px; align-items: center; }
    .search-input { padding: 10px 16px; border: 1px solid #ddd; border-radius: 8px; flex: 1; max-width: 300px; }
    .btn { padding: 10px 20px; border: none; border-radius: 8px; cursor: pointer; font-weight: 500; }
    .btn-primary { background: #1b5e20; color: white; }
    .btn-success { background: #2e7d32; color: white; margin-top: 12px; }
    .form-card { background: #fff; padding: 20px; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); margin-bottom: 20px; }
    .form-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 12px; }
    .form-group { display: flex; flex-direction: column; gap: 4px; }
    .form-group label { font-size: 13px; font-weight: 600; color: #555; }
    .form-group input, .form-group select { padding: 8px 12px; border: 1px solid #ddd; border-radius: 6px; }
    table { width: 100%; border-collapse: collapse; background: #fff; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
    th { background: #1b5e20; color: white; padding: 12px; text-align: left; font-size: 13px; }
    td { padding: 10px 12px; border-bottom: 1px solid #eee; font-size: 14px; }
    .name { font-weight: 600; }
    .number { font-weight: 700; text-align: center; }
    .value { font-weight: 600; color: #1b5e20; }
    .position-badge { padding: 3px 8px; border-radius: 10px; font-size: 12px; font-weight: 600; }
    .pos-gk { background: #fff3e0; color: #e65100; }
    .pos-def { background: #e3f2fd; color: #1565c0; }
    .pos-mid { background: #e8f5e9; color: #2e7d32; }
    .pos-fwd { background: #fce4ec; color: #c62828; }
    .actions { display: flex; gap: 6px; }
    .btn-sm { padding: 4px 10px; border: none; border-radius: 4px; cursor: pointer; font-size: 12px; }
    .btn-edit { background: #1976d2; color: white; }
    .btn-delete { background: #d32f2f; color: white; }
    .count { margin-top: 12px; color: #666; font-size: 14px; }
    .empty { text-align: center; color: #999; padding: 24px; }
    .loading { text-align: center; padding: 40px; color: #666; }
    .error-msg { background: #ffebee; color: #c62828; padding: 12px; border-radius: 8px; margin-top: 12px; }
    .success-msg { background: #e8f5e9; color: #2e7d32; padding: 12px; border-radius: 8px; margin-top: 12px; }
    .table-container { overflow-x: auto; }
  `]
})
export class AdminPlayersComponent implements OnInit {
  private adminService = inject(AdminService);
  players = signal<Player[]>([]);
  loading = signal(true);
  showForm = signal(false);
  editingId = signal<number | null>(null);
  error = signal('');
  success = signal('');
  searchTerm = '';

  form: Player = { clubId: 1, firstName: '', lastName: '', position: 'Delantero', nationality: 'Argentina', shirtNumber: 10, photoUrl: '', marketValue: 1.0 };

  ngOnInit() { this.loadPlayers(); }

  filteredPlayers() {
    if (!this.searchTerm) return this.players();
    const term = this.searchTerm.toLowerCase();
    return this.players().filter(p =>
      (p.firstName + ' ' + p.lastName).toLowerCase().includes(term) ||
      p.position.toLowerCase().includes(term) ||
      p.nationality.toLowerCase().includes(term)
    );
  }

  getPositionClass(position: string): string {
    if (position.includes('Portero')) return 'pos-gk';
    if (position.includes('Defensor')) return 'pos-def';
    if (position.includes('Mediocampista')) return 'pos-mid';
    return 'pos-fwd';
  }

  loadPlayers() {
    this.adminService.getPlayers().subscribe({
      next: (data) => { this.players.set(data); this.loading.set(false); },
      error: (err) => { this.error.set('Error: ' + (err.error?.message || err.message)); this.loading.set(false); }
    });
  }

  editPlayer(player: Player) {
    this.form = { ...player };
    this.editingId.set(player.id!);
    this.showForm.set(true);
  }

  savePlayer() {
    const obs = this.editingId()
      ? this.adminService.updatePlayer(this.editingId()!, this.form)
      : this.adminService.createPlayer(this.form);
    obs.subscribe({
      next: () => { this.success.set(this.editingId() ? 'Jugador actualizado' : 'Jugador creado'); this.resetForm(); this.loadPlayers(); setTimeout(() => this.success.set(''), 3000); },
      error: (err) => this.error.set('Error: ' + (err.error?.message || err.message))
    });
  }

  deletePlayer(id: number) {
    if (confirm('¿Eliminar este jugador?')) {
      this.adminService.deletePlayer(id).subscribe({
        next: () => { this.success.set('Jugador eliminado'); this.loadPlayers(); setTimeout(() => this.success.set(''), 3000); },
        error: (err) => this.error.set('Error: ' + (err.error?.message || err.message))
      });
    }
  }

  resetForm() {
    this.form = { clubId: 1, firstName: '', lastName: '', position: 'Delantero', nationality: 'Argentina', shirtNumber: 10, photoUrl: '', marketValue: 1.0 };
    this.editingId.set(null);
    this.showForm.set(false);
  }
}
