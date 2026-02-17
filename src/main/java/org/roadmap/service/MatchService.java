package org.roadmap.service;

import org.roadmap.dto.MatchDto;
import org.roadmap.dto.response.MatchDtoResponse;
import org.roadmap.dto.response.PlayerDtoResponse;
import org.roadmap.dto.Score;
import org.roadmap.entity.Match;
import org.roadmap.entity.Player;
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

    public MatchDtoResponse save(MatchDto match, Player winner) {
        Player firstPlayer = playerRepository.findById(match.getFirstPlayerId());
        Player secondPlayer = playerRepository.findById(match.getSecondPlayerId());
        Match entity = new Match(firstPlayer, secondPlayer, winner);

        Match savedEntity = matchRepository.save(entity);

        PlayerDtoResponse firstPlayerDto = new PlayerDtoResponse(firstPlayer.getName());
        PlayerDtoResponse secondPlayerDto = new PlayerDtoResponse(secondPlayer.getName());
        return new MatchDtoResponse(
                firstPlayerDto,
                secondPlayerDto,
                new PlayerDtoResponse(winner.getName())
        );
    }
}
