package org.roadmap.service;

import org.roadmap.model.dto.MatchDto;
import org.roadmap.model.dto.MatchDtoResponse;
import org.roadmap.model.dto.PlayerDtoRequest;
import org.roadmap.model.dto.PlayerDtoResponse;
import org.roadmap.model.dto.Score;
import org.roadmap.model.entity.MatchEntity;
import org.roadmap.model.entity.PlayerEntity;
import org.roadmap.repository.MatchRepository;
import org.roadmap.repository.PlayerRepository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MatchService {
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final Map<UUID, MatchDto> matches;

    public MatchService(MatchRepository matchRepository, PlayerRepository playerRepository) {
        this.matches = new ConcurrentHashMap<>();
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
    }

    public UUID create(MatchDto matchDto) {
        UUID uuid = UUID.randomUUID();
        matches.put(uuid, matchDto);
        return uuid;
    }

    public MatchDto getById(UUID id) {
        return matches.get(id);
    }

    public void givePoint(Integer playerId, MatchDto match) {
        Score currenctScore = match.getScore();
        // правила тенниса
        currenctScore.setFirstPlayerScore(15);
        System.out.println(match);
    }

    public MatchDtoResponse save(MatchDto match, PlayerEntity winner) {
        PlayerEntity firstPlayer = playerRepository.findById(match.getFirstPlayerId());
        PlayerEntity secondPlayer = playerRepository.findById(match.getSecondPlayerId());
        MatchEntity entity = new MatchEntity(firstPlayer, secondPlayer, winner);

        MatchEntity savedEntity = matchRepository.save(entity);

        PlayerDtoResponse firstPlayerDto = new PlayerDtoResponse(firstPlayer.getName());
        PlayerDtoResponse secondPlayerDto = new PlayerDtoResponse(secondPlayer.getName());
        return new MatchDtoResponse(
                firstPlayerDto,
                secondPlayerDto,
                new PlayerDtoResponse(winner.getName())
        );
    }
}
