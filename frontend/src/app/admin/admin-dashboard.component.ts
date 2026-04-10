import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AdminService, AdminDashboard } from './admin.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="admin-dashboard">
      <h1>Panel de Administración</h1>
      <p class="subtitle">Gestión completa de la plataforma Fulbo</p>

      @if (loading()) {
        <div class="loading">Cargando estadísticas...</div>
      } @else if (dashboard()) {
        <div class="stats-grid">
          <div class="stat-card">
            <div class="stat-icon">👥</div>
            <div class="stat-value">{{ dashboard()!.totalUsers }}</div>
            <div class="stat-label">Usuarios</div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">⚽</div>
            <div class="stat-value">{{ dashboard()!.totalClubs }}</div>
            <div class="stat-label">Clubes</div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">🏃</div>
            <div class="stat-value">{{ dashboard()!.totalPlayers }}</div>
            <div class="stat-label">Jugadores</div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">📅</div>
            <div class="stat-value">{{ dashboard()!.totalMatches }}</div>
            <div class="stat-label">Partidos</div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">🏆</div>
            <div class="stat-value">{{ dashboard()!.totalTournaments }}</div>
            <div class="stat-label">Torneos</div>
          </div>
          <div class="stat-card">
            <div class="stat-icon">📝</div>
            <div class="stat-value">{{ dashboard()!.totalPosts }}</div>
            <div class="stat-label">Posts</div>
          </div>
          <div class="stat-card live">
            <div class="stat-icon">🔴</div>
            <div class="stat-value">{{ dashboard()!.liveMatches }}</div>
            <div class="stat-label">En Vivo</div>
          </div>
        </div>
      }

      <div class="quick-actions">
        <h2>Acciones Rápidas</h2>
        <div class="actions-grid">
          <a routerLink="/admin/matches" class="action-card">
            <span class="action-icon">📅</span>
            <span>Gestionar Partidos</span>
          </a>
          <a routerLink="/admin/players" class="action-card">
            <span class="action-icon">🏃</span>
            <span>Gestionar Jugadores</span>
          </a>
          <a routerLink="/admin/tournaments" class="action-card">
            <span class="action-icon">🏆</span>
            <span>Gestionar Torneos</span>
          </a>
          <a routerLink="/admin/users" class="action-card">
            <span class="action-icon">👥</span>
            <span>Gestionar Usuarios</span>
          </a>
          <a routerLink="/admin/sync" class="action-card sync">
            <span class="action-icon">🔄</span>
            <span>Sincronizar Datos en Vivo</span>
          </a>
        </div>
      </div>

      @if (error()) {
        <div class="error-msg">{{ error() }}</div>
      }
    </div>
  `,
  styles: [`
    .admin-dashboard { padding: 24px; max-width: 1200px; margin: 0 auto; }
    h1 { color: #1b5e20; margin-bottom: 4px; font-size: 28px; }
    .subtitle { color: #666; margin-bottom: 24px; }
    .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(150px, 1fr)); gap: 16px; margin-bottom: 32px; }
    .stat-card { background: #fff; border-radius: 12px; padding: 20px; text-align: center; box-shadow: 0 2px 8px rgba(0,0,0,0.1); transition: transform 0.2s; }
    .stat-card:hover { transform: translateY(-4px); }
    .stat-card.live { background: linear-gradient(135deg, #ff5252, #d32f2f); color: white; }
    .stat-icon { font-size: 32px; margin-bottom: 8px; }
    .stat-value { font-size: 36px; font-weight: 700; }
    .stat-label { font-size: 14px; color: #666; margin-top: 4px; }
    .stat-card.live .stat-label { color: rgba(255,255,255,0.8); }
    .quick-actions { margin-top: 16px; }
    h2 { color: #333; margin-bottom: 16px; }
    .actions-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 12px; }
    .action-card { display: flex; align-items: center; gap: 12px; padding: 16px 20px; background: #fff; border-radius: 10px; box-shadow: 0 2px 6px rgba(0,0,0,0.08); text-decoration: none; color: #333; font-weight: 500; transition: all 0.2s; }
    .action-card:hover { background: #e8f5e9; transform: translateX(4px); }
    .action-card.sync { background: #e3f2fd; }
    .action-card.sync:hover { background: #bbdefb; }
    .action-icon { font-size: 24px; }
    .loading { text-align: center; padding: 40px; color: #666; }
    .error-msg { background: #ffebee; color: #c62828; padding: 12px 16px; border-radius: 8px; margin-top: 16px; }
  `]
})
export class AdminDashboardComponent implements OnInit {
  private adminService = inject(AdminService);
  dashboard = signal<AdminDashboard | null>(null);
  loading = signal(true);
  error = signal('');

  ngOnInit() {
    this.adminService.getDashboard().subscribe({
      next: (data) => {
        this.dashboard.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        this.error.set('Error al cargar el dashboard: ' + (err.error?.message || err.message));
        this.loading.set(false);
      }
    });
  }
}
