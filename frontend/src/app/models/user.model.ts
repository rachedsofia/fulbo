export interface User {
  id: number;
  username: string;
  email: string;
  displayName: string;
  avatarUrl: string;
  bio: string;
  role: string;
  reputation: number;
  followersCount: number;
  followingCount: number;
  createdAt: string;
}

export interface AuthResponse {
  token: string;
  refreshToken: string;
  type: string;
  user: User;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  displayName?: string;
}
