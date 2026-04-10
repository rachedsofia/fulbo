import { Component, Input, Output, EventEmitter } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Post } from '../../models/post.model';

@Component({
  selector: 'app-post-card',
  standalone: true,
  imports: [DatePipe],
  template: `
    <div class="post-card">
      <div class="post-header">
        <div class="avatar">{{ post.username?.charAt(0)?.toUpperCase() || 'U' }}</div>
        <div class="post-meta">
          <span class="username">{{ post.username || 'Usuario' }}</span>
          <span class="date">{{ post.createdAt | date:'dd/MM/yyyy HH:mm' }}</span>
        </div>
      </div>
      <div class="post-content">
        <p>{{ post.content }}</p>
        @if (post.mediaUrl) {
          <img [src]="post.mediaUrl" alt="Media" class="post-media">
        }
      </div>
      <div class="post-actions">
        <button (click)="onLike.emit(post.id)" class="action-btn">
          ❤️ {{ post.likesCount }}
        </button>
        <button class="action-btn">
          💬 {{ post.commentsCount }}
        </button>
        <button class="action-btn">
          🔄 {{ post.sharesCount }}
        </button>
      </div>
    </div>
  `,
  styles: [`
    .post-card {
      background: #16213e;
      border-radius: 12px;
      padding: 1.25rem;
      margin-bottom: 1rem;
      box-shadow: 0 2px 8px rgba(0,0,0,0.2);
    }
    .post-header {
      display: flex;
      align-items: center;
      gap: 0.75rem;
      margin-bottom: 0.75rem;
    }
    .avatar {
      width: 40px;
      height: 40px;
      border-radius: 50%;
      background: #0f3460;
      display: flex;
      align-items: center;
      justify-content: center;
      color: #00d4ff;
      font-weight: 700;
      font-size: 1.1rem;
    }
    .username {
      color: #00d4ff;
      font-weight: 600;
      display: block;
    }
    .date {
      color: #666;
      font-size: 0.8rem;
    }
    .post-content p {
      color: #ddd;
      line-height: 1.5;
      margin: 0 0 0.75rem;
    }
    .post-media {
      max-width: 100%;
      border-radius: 8px;
      margin-top: 0.5rem;
    }
    .post-actions {
      display: flex;
      gap: 1rem;
      border-top: 1px solid #2a2a4a;
      padding-top: 0.75rem;
    }
    .action-btn {
      background: none;
      border: none;
      color: #888;
      cursor: pointer;
      font-size: 0.9rem;
      padding: 0.3rem 0.5rem;
      border-radius: 6px;
      transition: background 0.2s;
    }
    .action-btn:hover {
      background: #0f3460;
      color: #00d4ff;
    }
  `]
})
export class PostCardComponent {
  @Input({ required: true }) post!: Post;
  @Output() onLike = new EventEmitter<number>();
}
