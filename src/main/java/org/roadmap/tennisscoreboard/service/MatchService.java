package org.roadmap.tennisscoreboard.service;

import org.roadmap.tennisscoreboard.dto.MatchDto;
import org.roadmap.tennisscoreboard.dto.Score;
import org.roadmap.tennisscoreboard.dto.response.MatchDtoResponse;
import org.roadmap.tennisscoreboard.dto.response.PlayerDtoResponse;
import org.roadmap.tennisscoreboard.entity.Match;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.repository.MatchRepository;
import org.roadmap.tennisscoreboard.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
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

    public List<MatchDtoResponse> getAll() {
        List<Match> matches = matchRepository.findAll();
        List<MatchDtoResponse> responseMatches = new ArrayList<>();
        for (Match match : matches) {
            PlayerDtoResponse firstPlayer = new PlayerDtoResponse(match.getFirstPlayer().getName());
            PlayerDtoResponse secondPlayer = new PlayerDtoResponse(match.getSecondPlayer().getName());
            PlayerDtoResponse winner = new PlayerDtoResponse(match.getWinner().getName());

            MatchDtoResponse matchDto = new MatchDtoResponse(firstPlayer, secondPlayer, winner);
            responseMatches.add(matchDto);
        }
        return responseMatches;
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

    public MatchDtoResponse save(MatchDto match, Integer winnerId) {
        Player firstPlayer = playerRepository.findById(match.getFirstPlayerId());
        Player secondPlayer = playerRepository.findById(match.getSecondPlayerId());
        Player winner = playerRepository.findById(winnerId);

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
