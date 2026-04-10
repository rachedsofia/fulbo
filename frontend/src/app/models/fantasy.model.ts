export interface FantasyTeam {
  id: number;
  userId: number;
  name: string;
  leagueId?: number;
  totalPoints: number;
  budget: number;
  players: FantasyPlayerSlot[];
  createdAt: string;
}

export interface FantasyPlayerSlot {
  id: number;
  playerId: number;
  clubId?: number;
  isCaptain: boolean;
  position: string;
}

export interface FantasyLeague {
  id: number;
  name: string;
  ownerId: number;
  type: string;
  code?: string;
  maxTeams: number;
  tournamentId?: number;
  createdAt: string;
}

export interface CreateFantasyTeamRequest {
  name: string;
  leagueId?: number;
}

export interface AddPlayerRequest {
  playerId: number;
  position: string;
  captain: boolean;
}

export interface CreateLeagueRequest {
  name: string;
  type: string;
  maxTeams?: number;
  tournamentId?: number;
}
