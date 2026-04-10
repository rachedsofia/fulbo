import { Component, Output, EventEmitter, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PostService } from '../../core/services/post.service';
import { Post } from '../../models/post.model';

@Component({
  selector: 'app-create-post',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="create-post">
      <textarea [(ngModel)]="content" placeholder="¿Qué está pasando en el fútbol?" rows="3"></textarea>
      <div class="create-actions">
        <select [(ngModel)]="type">
          <option value="TEXT">Texto</option>
          <option value="IMAGE">Imagen</option>
          <option value="POLL">Encuesta</option>
        </select>
        <button (click)="submit()" [disabled]="!content.trim() || loading()" class="btn btn-primary">
          {{ loading() ? 'Publicando...' : 'Publicar' }}
        </button>
      </div>
    </div>
  `,
  styles: [`
    .create-post {
      background: #16213e;
      border-radius: 12px;
      padding: 1.25rem;
      margin-bottom: 1.5rem;
    }
    textarea {
      width: 100%;
      background: #0f3460;
      border: 1px solid #333;
      border-radius: 8px;
      color: white;
      padding: 0.75rem;
      font-size: 1rem;
      resize: vertical;
      box-sizing: border-box;
    }
    textarea:focus { outline: none; border-color: #00d4ff; }
    .create-actions {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-top: 0.75rem;
    }
    select {
      background: #0f3460;
      color: white;
      border: 1px solid #333;
      border-radius: 6px;
      padding: 0.4rem 0.75rem;
    }
    .btn {
      padding: 0.5rem 1.25rem;
      border: none;
      border-radius: 8px;
      font-weight: 600;
      cursor: pointer;
    }
    .btn-primary { background: #00d4ff; color: #1a1a2e; }
    .btn-primary:disabled { opacity: 0.5; }
  `]
})
export class CreatePostComponent {
  content = '';
  type = 'TEXT';
  loading = signal(false);
  @Output() postCreated = new EventEmitter<Post>();

  constructor(private postService: PostService) {}

  submit(): void {
    this.loading.set(true);
    this.postService.createPost({ content: this.content, type: this.type }).subscribe({
      next: (post) => {
        this.postCreated.emit(post);
        this.content = '';
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }
}
