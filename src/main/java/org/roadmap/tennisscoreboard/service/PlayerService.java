package org.roadmap.tennisscoreboard.service;

import org.roadmap.tennisscoreboard.dto.request.PlayerDtoRequest;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.repository.PlayerRepository;

public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerDtoRequest create(PlayerDtoRequest player) {
        Player entity = new Player(null, player.name());
        Player savedPlayer = playerRepository.save(entity);
        return new PlayerDtoRequest(
                savedPlayer.getId(),
                savedPlayer.getName()
        );
    }

    public PlayerDtoRequest getById(Integer id) {
        Player savedPlayer = playerRepository.findById(id);
        return new PlayerDtoRequest(
                savedPlayer.getId(),
                savedPlayer.getName()
        );
    }
}
