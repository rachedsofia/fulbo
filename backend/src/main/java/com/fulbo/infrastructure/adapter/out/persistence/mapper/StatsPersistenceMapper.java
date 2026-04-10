package com.fulbo.infrastructure.adapter.out.persistence.mapper;

import com.fulbo.domain.model.*;
import com.fulbo.infrastructure.adapter.out.persistence.entity.*;
import org.springframework.stereotype.Component;

@Component
public class StatsPersistenceMapper {

    public Club toClubDomain(ClubEntity entity) {
        if (entity == null) return null;
        return new Club(entity.getId(), entity.getName(), entity.getShortName(),
                entity.getLogoUrl(), entity.getStadiumName(), entity.getCity(), entity.getFoundedYear());
    }

    public Player toPlayerDomain(PlayerEntity entity) {
        if (entity == null) return null;
        return new Player(entity.getId(), entity.getClubId(), entity.getFirstName(),
                entity.getLastName(), entity.getPosition(), entity.getNationality(),
                entity.getShirtNumber(), entity.getPhotoUrl(), entity.getMarketValue());
    }

    public Match toMatchDomain(MatchEntity entity) {
        if (entity == null) return null;
        return new Match(entity.getId(), entity.getHomeClubId(), entity.getAwayClubId(),
                entity.getTournamentId(), entity.getMatchDate(), entity.getHomeScore(),
                entity.getAwayScore(), Match.MatchStatus.valueOf(entity.getStatus().name()),
                entity.getStadium(), entity.getMatchday());
    }

    public PlayerStats toStatsDomain(PlayerStatsEntity entity) {
        if (entity == null) return null;
        return new PlayerStats(entity.getId(), entity.getPlayerId(), entity.getMatchId(),
                entity.getGoals(), entity.getAssists(), entity.getYellowCards(),
                entity.getRedCards(), entity.getMinutesPlayed(), entity.getSaves(),
                entity.getPasses(), entity.getShotsOnTarget(), entity.getTackles(), entity.getRating());
    }

    public Prediction toPredictionDomain(PredictionEntity entity) {
        if (entity == null) return null;
        return new Prediction(entity.getId(), entity.getUserId(), entity.getMatchId(),
                entity.getPredictedHomeScore(), entity.getPredictedAwayScore(),
                entity.getPoints(), entity.getResolved(), entity.getCreatedAt());
    }

    public PredictionEntity toPredictionEntity(Prediction prediction) {
        if (prediction == null) return null;
        PredictionEntity entity = new PredictionEntity();
        entity.setId(prediction.getId());
        entity.setUserId(prediction.getUserId());
        entity.setMatchId(prediction.getMatchId());
        entity.setPredictedHomeScore(prediction.getPredictedHomeScore());
        entity.setPredictedAwayScore(prediction.getPredictedAwayScore());
        entity.setPoints(prediction.getPoints() != null ? prediction.getPoints() : 0);
        entity.setResolved(prediction.getResolved() != null ? prediction.getResolved() : false);
        return entity;
    }
}
