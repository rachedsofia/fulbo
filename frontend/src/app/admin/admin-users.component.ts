import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AdminService, User, UserStats } from './admin.service';

@Component({
  selector: 'app-admin-users',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="admin-page">
      <div class="page-header">
        <a routerLink="/admin" class="back-link">&larr; Volver al Dashboard</a>
        <h1>Gestion de Usuarios</h1>
      </div>

      <div class="toolbar">
        <div class="tab-group">
          <button (click)="activeTab.set('list')" class="tab-btn" [class.active]="activeTab() === 'list'">
            Lista de Usuarios
          </button>
          <button (click)="activeTab.set('stats')" class="tab-btn" [class.active]="activeTab() === 'stats'">
            Estadisticas
          </button>
        </div>
        <input type="text" [(ngModel)]="searchTerm" placeholder="Buscar usuario..." class="search-input">
      </div>

      @if (activeTab() === 'list') {
        @if (loading()) {
          <div class="loading">Cargando usuarios...</div>
        } @else {
          <div class="table-container">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Usuario</th>
                  <th>Email</th>
                  <th>Nombre</th>
                  <th>Rol</th>
                  <th>Reputacion</th>
                  <th>Estado</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                @for (user of filteredUsers(); track user.id) {
                  <tr [class.inactive]="!user.active">
                    <td>{{ user.id }}</td>
                    <td class="username">{{ user.username }}</td>
                    <td>{{ user.email }}</td>
                    <td>{{ user.displayName }}</td>
                    <td>
                      <select [ngModel]="user.role" (ngModelChange)="changeRole(user.id, $event)" class="role-select">
                        <option value="USER">Usuario</option>
                        <option value="ADMIN">Admin</option>
                        <option value="MODERATOR">Moderador</option>
                        <option value="CLUB">Club</option>
                        <option value="JOURNALIST">Periodista</option>
                      </select>
                    </td>
                    <td class="reputation">{{ user.reputation }}</td>
                    <td>
                      <span class="status-badge" [class.active]="user.active" [class.deactivated]="!user.active">
                        {{ user.active ? 'Activo' : 'Inactivo' }}
                      </span>
                    </td>
                    <td class="actions">
                      <button (click)="viewUserStats(user.id)" class="btn-sm btn-stats">Ver Stats</button>
                      @if (user.active) {
                        <button (click)="deactivateUser(user.id)" class="btn-sm btn-delete">Desactivar</button>
                      }
                    </td>
                  </tr>
                } @empty {
                  <tr><td colspan="8" class="empty">No hay usuarios registrados</td></tr>
                }
              </tbody>
            </table>
          </div>
          <div class="count">Total: {{ users().length }} usuarios</div>
        }
      }

      @if (activeTab() === 'stats') {
        @if (loadingStats()) {
          <div class="loading">Cargando estadisticas de usuarios...</div>
        } @else {
          <div class="stats-summary">
            <div class="summary-card">
              <div class="summary-icon">👥</div>
              <div class="summary-value">{{ userStats().length }}</div>
              <div class="summary-label">Total Usuarios</div>
            </div>
            <div class="summary-card">
              <div class="summary-icon">✅</div>
              <div class="summary-value">{{ getActiveCount() }}</div>
              <div class="summary-label">Activos</div>
            </div>
            <div class="summary-card">
              <div class="summary-icon">📝</div>
              <div class="summary-value">{{ getTotalPosts() }}</div>
              <div class="summary-label">Posts Totales</div>
            </div>
            <div class="summary-card">
              <div class="summary-icon">⭐</div>
              <div class="summary-value">{{ getAvgReputation() }}</div>
              <div class="summary-label">Reputacion Promedio</div>
            </div>
          </div>

          <div class="stats-table-container">
            <table class="stats-table">
              <thead>
                <tr>
                  <th>Usuario</th>
                  <th>Email</th>
                  <th>Rol</th>
                  <th>Posts</th>
                  <th>Seguidores</th>
                  <th>Siguiendo</th>
                  <th>Reputacion</th>
                  <th>Estado</th>
                  <th>Registro</th>
                </tr>
              </thead>
              <tbody>
                @for (stat of filteredStats(); track stat.id) {
                  <tr [class.inactive]="!stat.active" (click)="selectUserStat(stat)" [class.selected]="selectedStat()?.id === stat.id">
                    <td class="username-cell">
                      <div class="avatar-small">{{ getInitials(stat.displayName || stat.username) }}</div>
                      <div>
                        <div class="name">{{ stat.displayName || stat.username }}</div>
                        <div class="handle">&#64;{{ stat.username }}</div>
                      </div>
                    </td>
                    <td>{{ stat.email }}</td>
                    <td><span class="role-badge" [class]="'role-' + stat.role.toLowerCase()">{{ getRoleLabel(stat.role) }}</span></td>
                    <td class="number">{{ stat.postsCount }}</td>
                    <td class="number">{{ stat.followersCount }}</td>
                    <td class="number">{{ stat.followingCount }}</td>
                    <td class="reputation">{{ stat.reputation }}</td>
                    <td>
                      <span class="status-badge" [class.active]="stat.active" [class.deactivated]="!stat.active">
                        {{ stat.active ? 'Activo' : 'Inactivo' }}
                      </span>
                    </td>
                    <td class="date">{{ formatDate(stat.createdAt) }}</td>
                  </tr>
                } @empty {
                  <tr><td colspan="9" class="empty">No hay datos de usuarios</td></tr>
                }
              </tbody>
            </table>
          </div>

          @if (selectedStat()) {
            <div class="user-detail-card">
              <div class="detail-header">
                <div class="detail-avatar">{{ getInitials(selectedStat()!.displayName || selectedStat()!.username) }}</div>
                <div class="detail-info">
                  <h3>{{ selectedStat()!.displayName || selectedStat()!.username }}</h3>
                  <p class="detail-handle">&#64;{{ selectedStat()!.username }}</p>
                  <p class="detail-email">{{ selectedStat()!.email }}</p>
                </div>
                <button (click)="selectedStat.set(null)" class="close-btn">&times;</button>
              </div>
              @if (selectedStat()!.bio) {
                <p class="detail-bio">{{ selectedStat()!.bio }}</p>
              }
              <div class="detail-stats-grid">
                <div class="detail-stat">
                  <div class="detail-stat-value">{{ selectedStat()!.postsCount }}</div>
                  <div class="detail-stat-label">Posts</div>
                </div>
                <div class="detail-stat">
                  <div class="detail-stat-value">{{ selectedStat()!.followersCount }}</div>
                  <div class="detail-stat-label">Seguidores</div>
                </div>
                <div class="detail-stat">
                  <div class="detail-stat-value">{{ selectedStat()!.followingCount }}</div>
                  <div class="detail-stat-label">Siguiendo</div>
                </div>
                <div class="detail-stat">
                  <div class="detail-stat-value">{{ selectedStat()!.reputation }}</div>
                  <div class="detail-stat-label">Reputacion</div>
                </div>
              </div>
              <div class="detail-meta">
                <span class="role-badge" [class]="'role-' + selectedStat()!.role.toLowerCase()">{{ getRoleLabel(selectedStat()!.role) }}</span>
                <span class="status-badge" [class.active]="selectedStat()!.active" [class.deactivated]="!selectedStat()!.active">
                  {{ selectedStat()!.active ? 'Activo' : 'Inactivo' }}
                </span>
                <span class="meta-date">Registrado: {{ formatDate(selectedStat()!.createdAt) }}</span>
              </div>
            </div>
          }
        }
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
    .toolbar { display: flex; gap: 12px; margin-bottom: 16px; align-items: center; flex-wrap: wrap; }
    .tab-group { display: flex; gap: 4px; background: #e0e0e0; border-radius: 8px; padding: 3px; }
    .tab-btn { padding: 8px 16px; border: none; border-radius: 6px; cursor: pointer; font-weight: 500; font-size: 14px; background: transparent; color: #555; transition: all 0.2s; }
    .tab-btn.active { background: #1b5e20; color: white; }
    .search-input { padding: 10px 16px; border: 1px solid #ddd; border-radius: 8px; flex: 1; max-width: 300px; font-size: 14px; }
    .table-container, .stats-table-container { overflow-x: auto; }
    table { width: 100%; border-collapse: collapse; background: #fff; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
    th { background: #1b5e20; color: white; padding: 12px; text-align: left; font-size: 13px; }
    td { padding: 10px 12px; border-bottom: 1px solid #eee; font-size: 14px; }
    tr.inactive { opacity: 0.6; }
    tr.selected { background: #e8f5e9; }
    tr:hover { background: #f5f5f5; cursor: pointer; }
    .username { font-weight: 600; }
    .reputation { font-weight: 700; color: #ff9800; text-align: center; }
    .number { font-weight: 600; text-align: center; }
    .date { font-size: 13px; color: #777; white-space: nowrap; }
    .role-select { padding: 4px 8px; border: 1px solid #ddd; border-radius: 6px; font-size: 13px; }
    .status-badge { padding: 3px 10px; border-radius: 12px; font-size: 12px; font-weight: 600; }
    .active { background: #e8f5e9; color: #2e7d32; }
    .deactivated { background: #ffebee; color: #c62828; }
    .role-badge { padding: 3px 10px; border-radius: 12px; font-size: 12px; font-weight: 600; }
    .role-admin { background: #e3f2fd; color: #1565c0; }
    .role-user { background: #f5f5f5; color: #666; }
    .role-moderator { background: #fff3e0; color: #e65100; }
    .role-club { background: #e8f5e9; color: #2e7d32; }
    .role-journalist { background: #fce4ec; color: #c62828; }
    .actions { display: flex; gap: 6px; }
    .btn-sm { padding: 4px 10px; border: none; border-radius: 4px; cursor: pointer; font-size: 12px; }
    .btn-delete { background: #d32f2f; color: white; }
    .btn-stats { background: #1976d2; color: white; }
    .count { margin-top: 12px; color: #666; font-size: 14px; }
    .empty { text-align: center; color: #999; padding: 24px; }
    .loading { text-align: center; padding: 40px; color: #666; }
    .error-msg { background: #ffebee; color: #c62828; padding: 12px; border-radius: 8px; margin-top: 12px; }
    .success-msg { background: #e8f5e9; color: #2e7d32; padding: 12px; border-radius: 8px; margin-top: 12px; }

    /* Stats summary cards */
    .stats-summary { display: grid; grid-template-columns: repeat(auto-fit, minmax(160px, 1fr)); gap: 16px; margin-bottom: 24px; }
    .summary-card { background: #fff; border-radius: 12px; padding: 20px; text-align: center; box-shadow: 0 2px 8px rgba(0,0,0,0.1); transition: transform 0.2s; }
    .summary-card:hover { transform: translateY(-4px); }
    .summary-icon { font-size: 28px; margin-bottom: 6px; }
    .summary-value { font-size: 32px; font-weight: 700; color: #1b5e20; }
    .summary-label { font-size: 13px; color: #666; margin-top: 4px; }

    /* Stats table */
    .username-cell { display: flex; align-items: center; gap: 10px; }
    .avatar-small { width: 36px; height: 36px; border-radius: 50%; background: linear-gradient(135deg, #1b5e20, #4caf50); color: white; display: flex; align-items: center; justify-content: center; font-weight: 700; font-size: 14px; flex-shrink: 0; }
    .name { font-weight: 600; font-size: 14px; }
    .handle { font-size: 12px; color: #888; }

    /* User detail card */
    .user-detail-card { background: #fff; border-radius: 12px; padding: 24px; box-shadow: 0 2px 12px rgba(0,0,0,0.12); margin-top: 20px; }
    .detail-header { display: flex; align-items: center; gap: 16px; margin-bottom: 16px; }
    .detail-avatar { width: 64px; height: 64px; border-radius: 50%; background: linear-gradient(135deg, #1b5e20, #4caf50); color: white; display: flex; align-items: center; justify-content: center; font-weight: 700; font-size: 24px; flex-shrink: 0; }
    .detail-info { flex: 1; }
    .detail-info h3 { margin: 0 0 4px; color: #1b5e20; }
    .detail-handle { margin: 0; color: #888; font-size: 14px; }
    .detail-email { margin: 2px 0 0; color: #555; font-size: 14px; }
    .close-btn { background: none; border: none; font-size: 28px; color: #999; cursor: pointer; padding: 0 8px; }
    .close-btn:hover { color: #333; }
    .detail-bio { background: #f5f5f5; padding: 12px 16px; border-radius: 8px; color: #555; font-style: italic; margin-bottom: 16px; }
    .detail-stats-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 16px; margin-bottom: 16px; }
    .detail-stat { text-align: center; padding: 16px; background: #f5f5f5; border-radius: 10px; }
    .detail-stat-value { font-size: 28px; font-weight: 700; color: #1b5e20; }
    .detail-stat-label { font-size: 13px; color: #666; margin-top: 4px; }
    .detail-meta { display: flex; gap: 10px; align-items: center; flex-wrap: wrap; }
    .meta-date { font-size: 13px; color: #888; }
  `]
})
export class AdminUsersComponent implements OnInit {
  private adminService = inject(AdminService);
  users = signal<User[]>([]);
  userStats = signal<UserStats[]>([]);
  loading = signal(true);
  loadingStats = signal(true);
  activeTab = signal<'list' | 'stats'>('list');
  selectedStat = signal<UserStats | null>(null);
  error = signal('');
  success = signal('');
  searchTerm = '';

  ngOnInit() {
    this.loadUsers();
    this.loadUserStats();
  }

  filteredUsers() {
    if (!this.searchTerm) return this.users();
    const term = this.searchTerm.toLowerCase();
    return this.users().filter(u =>
      u.username.toLowerCase().includes(term) ||
      u.email.toLowerCase().includes(term) ||
      (u.displayName || '').toLowerCase().includes(term)
    );
  }

  filteredStats() {
    if (!this.searchTerm) return this.userStats();
    const term = this.searchTerm.toLowerCase();
    return this.userStats().filter(u =>
      u.username.toLowerCase().includes(term) ||
      u.email.toLowerCase().includes(term) ||
      (u.displayName || '').toLowerCase().includes(term)
    );
  }

  loadUsers() {
    this.adminService.getUsers().subscribe({
      next: (data) => { this.users.set(data); this.loading.set(false); },
      error: (err) => { this.error.set('Error: ' + (err.error?.message || err.message)); this.loading.set(false); }
    });
  }

  loadUserStats() {
    this.adminService.getUserStats().subscribe({
      next: (data) => { this.userStats.set(data); this.loadingStats.set(false); },
      error: (err) => { this.error.set('Error al cargar estadisticas: ' + (err.error?.message || err.message)); this.loadingStats.set(false); }
    });
  }

  viewUserStats(userId: number) {
    const stat = this.userStats().find(s => s.id === userId);
    if (stat) {
      this.selectedStat.set(stat);
      this.activeTab.set('stats');
    }
  }

  selectUserStat(stat: UserStats) {
    this.selectedStat.set(this.selectedStat()?.id === stat.id ? null : stat);
  }

  changeRole(userId: number, role: string) {
    this.adminService.updateUserRole(userId, role).subscribe({
      next: () => { this.success.set('Rol actualizado'); this.loadUsers(); this.loadUserStats(); setTimeout(() => this.success.set(''), 3000); },
      error: (err) => this.error.set('Error: ' + (err.error?.message || err.message))
    });
  }

  deactivateUser(id: number) {
    if (confirm('Desactivar este usuario?')) {
      this.adminService.deactivateUser(id).subscribe({
        next: () => { this.success.set('Usuario desactivado'); this.loadUsers(); this.loadUserStats(); setTimeout(() => this.success.set(''), 3000); },
        error: (err) => this.error.set('Error: ' + (err.error?.message || err.message))
      });
    }
  }

  getInitials(name: string): string {
    if (!name) return '?';
    return name.split(' ').map(n => n[0]).join('').toUpperCase().substring(0, 2);
  }

  getRoleLabel(role: string): string {
    const labels: Record<string, string> = { USER: 'Usuario', ADMIN: 'Admin', MODERATOR: 'Moderador', CLUB: 'Club', JOURNALIST: 'Periodista' };
    return labels[role] || role;
  }

  formatDate(dateStr: string): string {
    if (!dateStr) return '-';
    try {
      const date = new Date(dateStr);
      return date.toLocaleDateString('es-AR', { year: 'numeric', month: 'short', day: 'numeric' });
    } catch {
      return dateStr;
    }
  }

  getActiveCount(): number {
    return this.userStats().filter(u => u.active).length;
  }

  getTotalPosts(): number {
    return this.userStats().reduce((sum, u) => sum + u.postsCount, 0);
  }

  getAvgReputation(): number {
    const stats = this.userStats();
    if (stats.length === 0) return 0;
    return Math.round(stats.reduce((sum, u) => sum + u.reputation, 0) / stats.length);
  }
}
