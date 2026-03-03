package org.roadmap.tennisscoreboard.service;

import org.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.roadmap.tennisscoreboard.dto.PlayerDto;
import org.roadmap.tennisscoreboard.dto.FinishedMatchDto;
import org.roadmap.tennisscoreboard.entity.Match;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.repository.MatchRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MatchService {
    private final MatchRepository matchRepository;
    private final Map<UUID, OngoingMatch> matches;

    public MatchService(MatchRepository matchRepository) {
        this.matches = new ConcurrentHashMap<>();
        this.matchRepository = matchRepository;
    }

    public UUID create(OngoingMatch match) {
        UUID uuid = UUID.randomUUID();
        matches.put(uuid, match);
        return uuid;
    }

    public OngoingMatch getById(UUID id) {
        return matches.get(id);
    }

    public FinishedMatchDto save(OngoingMatch match, Player winner) {
        Match entity = new Match(match.getFirstPlayer(), match.getSecondPlayer(), winner);

        Match savedEntity = matchRepository.save(entity);

        PlayerDto firstPlayerDto = new PlayerDto(match.getFirstPlayer().getId(), match.getFirstPlayer().getName());
        PlayerDto secondPlayerDto = new PlayerDto(match.getSecondPlayer().getId(), match.getSecondPlayer().getName());
        return new FinishedMatchDto(
                savedEntity.getId(),
                firstPlayerDto,
                secondPlayerDto,
                new PlayerDto(winner.getId(), winner.getName())
        );
    }

    public List<FinishedMatchDto> getAll() {
        List<Match> matches = matchRepository.findAll();
        List<FinishedMatchDto> responseMatches = new ArrayList<>();
        for (Match match : matches) {
            PlayerDto firstPlayer = new PlayerDto(match.getFirstPlayer().getId(), match.getFirstPlayer().getName());
            PlayerDto secondPlayer = new PlayerDto(match.getSecondPlayer().getId(), match.getSecondPlayer().getName());
            PlayerDto winner = new PlayerDto(match.getWinner().getId(), match.getWinner().getName());

            FinishedMatchDto matchDto = new FinishedMatchDto(match.getId(), firstPlayer, secondPlayer, winner);
            responseMatches.add(matchDto);
        }
        return responseMatches;
    }

    public List<FinishedMatchDto> getMatches(int pageNumber, int pageSize, String filterName) {
        List<Match> matches;
        int offset = (pageNumber - 1) * pageSize;
        if (filterName == null) {
            matches = matchRepository.findMatchesWithPagination(pageSize, offset);
        } else {
            matches = matchRepository.findMatchesWithPaginationAndPlayerName(pageSize, offset, filterName);
        }

        List<FinishedMatchDto> responseMatches = new ArrayList<>();
        for (Match match : matches) {
            PlayerDto firstPlayer = new PlayerDto(match.getFirstPlayer().getId(), match.getFirstPlayer().getName());
            PlayerDto secondPlayer = new PlayerDto(match.getSecondPlayer().getId(), match.getSecondPlayer().getName());
            PlayerDto winner = new PlayerDto(match.getWinner().getId(), match.getWinner().getName());

            FinishedMatchDto matchDto = new FinishedMatchDto(match.getId(), firstPlayer, secondPlayer, winner);
            responseMatches.add(matchDto);
        }
        return responseMatches;
    }
}
