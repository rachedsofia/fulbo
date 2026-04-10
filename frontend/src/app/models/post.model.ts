export interface Post {
  id: number;
  userId: number;
  username?: string;
  content: string;
  type: string;
  mediaUrl?: string;
  clubId?: number;
  tournamentId?: number;
  likesCount: number;
  commentsCount: number;
  sharesCount: number;
  createdAt: string;
}

export interface CreatePostRequest {
  content: string;
  type?: string;
  mediaUrl?: string;
  clubId?: number;
  tournamentId?: number;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
}
