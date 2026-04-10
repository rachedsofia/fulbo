import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Post, CreatePostRequest, PageResponse } from '../../models/post.model';

@Injectable({ providedIn: 'root' })
export class PostService {
  private readonly apiUrl = `${environment.apiUrl}/posts`;

  constructor(private http: HttpClient) {}

  getFeed(page = 0, size = 20): Observable<PageResponse<Post>> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<PageResponse<Post>>(`${this.apiUrl}/feed`, { params });
  }

  getPost(id: number): Observable<Post> {
    return this.http.get<Post>(`${this.apiUrl}/${id}`);
  }

  getPostsByUser(userId: number, page = 0): Observable<PageResponse<Post>> {
    const params = new HttpParams().set('page', page).set('size', 20);
    return this.http.get<PageResponse<Post>>(`${this.apiUrl}/user/${userId}`, { params });
  }

  getPostsByClub(clubId: number, page = 0): Observable<PageResponse<Post>> {
    const params = new HttpParams().set('page', page).set('size', 20);
    return this.http.get<PageResponse<Post>>(`${this.apiUrl}/club/${clubId}`, { params });
  }

  createPost(request: CreatePostRequest): Observable<Post> {
    return this.http.post<Post>(this.apiUrl, request);
  }

  updatePost(id: number, request: CreatePostRequest): Observable<Post> {
    return this.http.put<Post>(`${this.apiUrl}/${id}`, request);
  }

  deletePost(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  likePost(id: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${id}/like`, {});
  }

  unlikePost(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}/like`);
  }
}
