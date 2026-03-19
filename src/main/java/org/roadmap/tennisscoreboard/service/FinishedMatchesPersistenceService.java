package org.roadmap.tennisscoreboard.service;

import org.roadmap.tennisscoreboard.dto.FinishedMatchDto;
import org.roadmap.tennisscoreboard.dto.PlayerDto;
import org.roadmap.tennisscoreboard.entity.Match;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.repository.MatchRepository;
import org.roadmap.tennisscoreboard.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;

public class FinishedMatchesPersistenceService {
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;

    public FinishedMatchesPersistenceService(MatchRepository matchRepository, PlayerRepository playerRepository) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
    }

    public void save(FinishedMatchDto finishedMatchDto) {
        Player firstPlayer = playerRepository.findById(finishedMatchDto.firstPlayer().id());
        Player secondPlayer = playerRepository.findById(finishedMatchDto.secondPlayer().id());
        Player winner = playerRepository.findById(finishedMatchDto.winner().id());
        Match entity = new Match(
                firstPlayer,
                secondPlayer,
                winner
        );
        matchRepository.save(entity);
    }

    public List<FinishedMatchDto> getMatches(int pageNumber, int pageSize, String filterName) {
        List<Match> matches;
        int offset = (pageNumber - 1) * pageSize;
        if (filterName == null) {
            matches = matchRepository.findMatchesWithPagination(pageSize, offset);
        } else {
            matches = matchRepository.findAll(pageSize, offset, filterName);
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

    public int getTotalPages(int pageSize, String filterName) {
        long matchesCount;
        if (filterName == null) {
            matchesCount = matchRepository.getCount();
        } else {
            matchesCount = matchRepository.getCountWithFilter(filterName);
        }
        return (int) Math.ceil((double) matchesCount / pageSize);
    }
}
