export interface Club {
  id: number;
  name: string;
  shortName: string;
  logoUrl?: string;
  stadiumName: string;
  city: string;
  foundedYear: number;
}

export interface Player {
  id: number;
  clubId?: number;
  firstName: string;
  lastName: string;
  position: string;
  nationality: string;
  shirtNumber: number;
  photoUrl?: string;
  marketValue: number;
}

export interface Match {
  id: number;
  homeClubId: number;
  awayClubId: number;
  tournamentId?: number;
  matchDate: string;
  homeScore?: number;
  awayScore?: number;
  status: string;
  stadium: string;
  matchday?: number;
}

export interface PlayerStats {
  id: number;
  playerId: number;
  matchId: number;
  goals: number;
  assists: number;
  yellowCards: number;
  redCards: number;
  minutesPlayed: number;
  saves: number;
  passes: number;
  shotsOnTarget: number;
  tackles: number;
  rating?: number;
}

export interface Prediction {
  id: number;
  userId: number;
  matchId: number;
  predictedHomeScore: number;
  predictedAwayScore: number;
  points: number;
  resolved: boolean;
  createdAt: string;
}
