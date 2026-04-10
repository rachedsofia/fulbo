import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AdminService, User } from './admin.service';

@Component({
  selector: 'app-admin-users',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="admin-page">
      <div class="page-header">
        <a routerLink="/admin" class="back-link">← Volver al Dashboard</a>
        <h1>Gestión de Usuarios</h1>
      </div>

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
                <th>Reputación</th>
                <th>Estado</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              @for (user of users(); track user.id) {
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

      @if (error()) { <div class="error-msg">{{ error() }}</div> }
      @if (success()) { <div class="success-msg">{{ success() }}</div> }
    </div>
  `,
  styles: [`
    .admin-page { padding: 24px; max-width: 1200px; margin: 0 auto; }
    .page-header { margin-bottom: 20px; }
    .back-link { color: #1b5e20; text-decoration: none; font-size: 14px; }
    h1 { margin: 8px 0 0; color: #1b5e20; }
    .table-container { overflow-x: auto; }
    table { width: 100%; border-collapse: collapse; background: #fff; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
    th { background: #1b5e20; color: white; padding: 12px; text-align: left; font-size: 13px; }
    td { padding: 10px 12px; border-bottom: 1px solid #eee; font-size: 14px; }
    tr.inactive { opacity: 0.6; }
    .username { font-weight: 600; }
    .reputation { font-weight: 700; color: #ff9800; text-align: center; }
    .role-select { padding: 4px 8px; border: 1px solid #ddd; border-radius: 6px; font-size: 13px; }
    .status-badge { padding: 3px 10px; border-radius: 12px; font-size: 12px; font-weight: 600; }
    .active { background: #e8f5e9; color: #2e7d32; }
    .deactivated { background: #ffebee; color: #c62828; }
    .actions { display: flex; gap: 6px; }
    .btn-sm { padding: 4px 10px; border: none; border-radius: 4px; cursor: pointer; font-size: 12px; }
    .btn-delete { background: #d32f2f; color: white; }
    .count { margin-top: 12px; color: #666; font-size: 14px; }
    .empty { text-align: center; color: #999; padding: 24px; }
    .loading { text-align: center; padding: 40px; color: #666; }
    .error-msg { background: #ffebee; color: #c62828; padding: 12px; border-radius: 8px; margin-top: 12px; }
    .success-msg { background: #e8f5e9; color: #2e7d32; padding: 12px; border-radius: 8px; margin-top: 12px; }
  `]
})
export class AdminUsersComponent implements OnInit {
  private adminService = inject(AdminService);
  users = signal<User[]>([]);
  loading = signal(true);
  error = signal('');
  success = signal('');

  ngOnInit() { this.loadUsers(); }

  loadUsers() {
    this.adminService.getUsers().subscribe({
      next: (data) => { this.users.set(data); this.loading.set(false); },
      error: (err) => { this.error.set('Error: ' + (err.error?.message || err.message)); this.loading.set(false); }
    });
  }

  changeRole(userId: number, role: string) {
    this.adminService.updateUserRole(userId, role).subscribe({
      next: () => { this.success.set('Rol actualizado'); this.loadUsers(); setTimeout(() => this.success.set(''), 3000); },
      error: (err) => this.error.set('Error: ' + (err.error?.message || err.message))
    });
  }

  deactivateUser(id: number) {
    if (confirm('¿Desactivar este usuario?')) {
      this.adminService.deactivateUser(id).subscribe({
        next: () => { this.success.set('Usuario desactivado'); this.loadUsers(); setTimeout(() => this.success.set(''), 3000); },
        error: (err) => this.error.set('Error: ' + (err.error?.message || err.message))
      });
    }
  }
}
