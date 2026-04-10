import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  standalone: true,
  template: `
    <footer class="footer">
      <p>&copy; 2026 Fulbo - La plataforma del fútbol argentino</p>
    </footer>
  `,
  styles: [`
    .footer {
      text-align: center;
      padding: 1rem;
      background: #1a1a2e;
      color: #666;
      font-size: 0.85rem;
      margin-top: auto;
    }
  `]
})
export class FooterComponent {}
