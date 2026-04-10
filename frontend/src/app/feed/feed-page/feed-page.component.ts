import { Component, OnInit, signal } from '@angular/core';
import { PostCardComponent } from '../post-card/post-card.component';
import { CreatePostComponent } from '../create-post/create-post.component';
import { PostService } from '../../core/services/post.service';
import { AuthService } from '../../core/services/auth.service';
import { Post } from '../../models/post.model';

@Component({
  selector: 'app-feed-page',
  standalone: true,
  imports: [PostCardComponent, CreatePostComponent],
  template: `
    <div class="feed-container">
      <h1>Feed</h1>
      @if (authService.isAuthenticated()) {
        <app-create-post (postCreated)="onPostCreated($event)" />
      }
      @for (post of posts(); track post.id) {
        <app-post-card [post]="post" (onLike)="likePost($event)" />
      }
      @if (posts().length === 0 && !loading()) {
        <div class="empty-state">
          <p>No hay publicaciones todavía. ¡Sé el primero en publicar!</p>
        </div>
      }
      @if (loading()) {
        <div class="loading">Cargando...</div>
      }
      @if (!isLastPage()) {
        <button (click)="loadMore()" class="btn btn-outline load-more">Cargar más</button>
      }
    </div>
  `,
  styles: [`
    .feed-container {
      max-width: 680px;
      margin: 0 auto;
      padding: 1.5rem;
    }
    h1 {
      color: #00d4ff;
      margin-bottom: 1.5rem;
    }
    .empty-state {
      text-align: center;
      color: #666;
      padding: 3rem 1rem;
      background: #16213e;
      border-radius: 12px;
    }
    .loading {
      text-align: center;
      color: #00d4ff;
      padding: 2rem;
    }
    .load-more {
      display: block;
      width: 100%;
      padding: 0.75rem;
      margin-top: 1rem;
      background: transparent;
      border: 1px solid #00d4ff;
      color: #00d4ff;
      border-radius: 8px;
      cursor: pointer;
      font-weight: 600;
    }
  `]
})
export class FeedPageComponent implements OnInit {
  posts = signal<Post[]>([]);
  loading = signal(false);
  isLastPage = signal(false);
  private page = 0;

  constructor(
    private postService: PostService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadFeed();
  }

  loadFeed(): void {
    this.loading.set(true);
    this.postService.getFeed(this.page).subscribe({
      next: (res) => {
        this.posts.update(current => [...current, ...res.content]);
        this.isLastPage.set(res.last);
        this.loading.set(false);
      },
      error: () => this.loading.set(false)
    });
  }

  loadMore(): void {
    this.page++;
    this.loadFeed();
  }

  onPostCreated(post: Post): void {
    this.posts.update(current => [post, ...current]);
  }

  likePost(postId: number): void {
    this.postService.likePost(postId).subscribe();
  }
}
