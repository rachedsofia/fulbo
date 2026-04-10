package com.fulbo.infrastructure.adapter.out.persistence;

import com.fulbo.domain.model.FantasyLeague;
import com.fulbo.domain.model.FantasyTeam;
import com.fulbo.domain.port.out.FantasyLeagueRepository;
import com.fulbo.domain.port.out.FantasyTeamRepository;
import com.fulbo.infrastructure.adapter.out.persistence.entity.FantasyTeamPlayerEntity;
import com.fulbo.infrastructure.adapter.out.persistence.mapper.FantasyPersistenceMapper;
import com.fulbo.infrastructure.adapter.out.persistence.repository.JpaFantasyLeagueRepository;
import com.fulbo.infrastructure.adapter.out.persistence.repository.JpaFantasyTeamPlayerRepository;
import com.fulbo.infrastructure.adapter.out.persistence.repository.JpaFantasyTeamRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FantasyPersistenceAdapter implements FantasyTeamRepository, FantasyLeagueRepository {

    private final JpaFantasyTeamRepository teamRepo;
    private final JpaFantasyTeamPlayerRepository playerRepo;
    private final JpaFantasyLeagueRepository leagueRepo;
    private final FantasyPersistenceMapper mapper;

    public FantasyPersistenceAdapter(JpaFantasyTeamRepository teamRepo,
                                     JpaFantasyTeamPlayerRepository playerRepo,
                                     JpaFantasyLeagueRepository leagueRepo,
                                     FantasyPersistenceMapper mapper) {
        this.teamRepo = teamRepo;
        this.playerRepo = playerRepo;
        this.leagueRepo = leagueRepo;
        this.mapper = mapper;
    }

    @Override
    public FantasyTeam save(FantasyTeam team) {
        var entity = mapper.toEntity(team);
        var saved = teamRepo.save(entity);

        if (team.getPlayers() != null) {
            for (var player : team.getPlayers()) {
                if (player.getId() == null) {
                    var playerEntity = mapper.toPlayerEntity(player);
                    playerEntity.setFantasyTeamId(saved.getId());
                    playerRepo.save(playerEntity);
                }
            }
        }

        return findById(saved.getId()).orElse(mapper.toDomain(saved));
    }

    @Override
    public Optional<FantasyTeam> findById(Long id) {
        return teamRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<FantasyTeam> findByUserId(Long userId) {
        return teamRepo.findByUserId(userId).map(mapper::toDomain);
    }

    @Override
    public List<FantasyTeam> findByLeagueIdOrderByTotalPointsDesc(Long leagueId) {
        return teamRepo.findByLeagueIdOrderByTotalPointsDesc(leagueId)
                .stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public FantasyLeague save(FantasyLeague league) {
        return mapper.toLeagueDomain(leagueRepo.save(mapper.toLeagueEntity(league)));
    }

    @Override
    public Optional<FantasyLeague> findLeagueById(Long id) {
        return leagueRepo.findById(id).map(mapper::toLeagueDomain);
    }

    @Override
    public Optional<FantasyLeague> findByCode(String code) {
        return leagueRepo.findByCode(code).map(mapper::toLeagueDomain);
    }

    @Override
    public List<FantasyLeague> findByType(FantasyLeague.LeagueType type) {
        return leagueRepo.findByType(
                com.fulbo.infrastructure.adapter.out.persistence.entity.FantasyLeagueEntity.LeagueType.valueOf(type.name())
        ).stream().map(mapper::toLeagueDomain).collect(Collectors.toList());
    }
}
