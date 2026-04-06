package org.roadmap.tennisscoreboard.service;

import org.hibernate.SessionFactory;
import org.roadmap.tennisscoreboard.dto.FinishedMatchDto;
import org.roadmap.tennisscoreboard.dto.PlayerDto;
import org.roadmap.tennisscoreboard.entity.Match;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.exception.ExceptionMessages;
import org.roadmap.tennisscoreboard.repository.MatchRepository;
import org.roadmap.tennisscoreboard.repository.PlayerRepository;
import org.roadmap.tennisscoreboard.util.TransactionController;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class FinishedMatchesPersistenceService {
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final SessionFactory sessionFactory;

    public FinishedMatchesPersistenceService(
            MatchRepository matchRepository,
            PlayerRepository playerRepository,
            SessionFactory sessionFactory) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
        this.sessionFactory = sessionFactory;
    }

    public void save(FinishedMatchDto finishedMatchDto) {
        TransactionController.execute(sessionFactory, () -> {
            Player firstPlayer = getPlayerOrThrow(finishedMatchDto.firstPlayer());
            Player secondPlayer = getPlayerOrThrow(finishedMatchDto.secondPlayer());
            Player winner = getPlayerOrThrow(finishedMatchDto.winner());
            Match entity = new Match(
                    firstPlayer,
                    secondPlayer,
                    winner
            );
            matchRepository.save(entity);
        });
    }

    public List<FinishedMatchDto> getMatches(int pageNumber, int pageSize, String filterName) {
        return TransactionController.execute(sessionFactory, () -> {
            List<Match> matches;
            int offset = (pageNumber - 1) * pageSize;
            if (filterName == null) {
                matches = matchRepository.findMatchesWithPagination(pageSize, offset);
            } else {
                matches = matchRepository.findAll(pageSize, offset, filterName);
            }
            return mapToFinishedMatchDto(matches);
        });
    }

    public int getTotalPages(int pageSize, String filterName) {
        return TransactionController.execute(sessionFactory, () -> {
            long matchesCount;
            if (filterName == null) {
                matchesCount = matchRepository.getCount();
            } else {
                matchesCount = matchRepository.getCountWithFilter(filterName);
            }
            return (int) Math.ceil((double) matchesCount / pageSize);
        });
    }

    private List<FinishedMatchDto> mapToFinishedMatchDto(List<Match> matches) {
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

    private Player getPlayerOrThrow(PlayerDto playerDto) {
        return playerRepository.findById(playerDto.id())
                .orElseThrow(() -> new NoSuchElementException(ExceptionMessages.PLAYER_NOT_FOUND + playerDto.id()));
    }
}
