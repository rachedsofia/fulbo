import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/feed', pathMatch: 'full' },
  {
    path: 'auth/login',
    loadComponent: () => import('./auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'auth/register',
    loadComponent: () => import('./auth/register/register.component').then(m => m.RegisterComponent)
  },
  {
    path: 'feed',
    loadComponent: () => import('./feed/feed-page/feed-page.component').then(m => m.FeedPageComponent)
  },
  {
    path: 'fantasy',
    children: [
      { path: '', redirectTo: 'leagues', pathMatch: 'full' },
      {
        path: 'team',
        loadComponent: () => import('./fantasy/fantasy-team/fantasy-team.component').then(m => m.FantasyTeamComponent),
        canActivate: [authGuard]
      },
      {
        path: 'leagues',
        loadComponent: () => import('./fantasy/fantasy-league/fantasy-league.component').then(m => m.FantasyLeagueComponent)
      },
      {
        path: 'ranking/:leagueId',
        loadComponent: () => import('./fantasy/fantasy-ranking/fantasy-ranking.component').then(m => m.FantasyRankingComponent)
      }
    ]
  },
  {
    path: 'stats',
    children: [
      { path: '', redirectTo: 'clubs', pathMatch: 'full' },
      {
        path: 'clubs',
        loadComponent: () => import('./stats/clubs/clubs.component').then(m => m.ClubsComponent)
      },
      {
        path: 'clubs/:clubId',
        loadComponent: () => import('./stats/players/players.component').then(m => m.PlayersComponent)
      },
      {
        path: 'matches/:id',
        loadComponent: () => import('./stats/match-detail/match-detail.component').then(m => m.MatchDetailComponent)
      }
    ]
  },
  { path: '**', redirectTo: '/feed' }
];
