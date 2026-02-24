package org.roadmap.tennisscoreboard.service;

import org.roadmap.tennisscoreboard.dto.PlayerDto;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.repository.PlayerRepository;

public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerDto create(PlayerDto player) {
        Player entity = new Player(null, player.name());
        Player savedPlayer = playerRepository.save(entity);
        return new PlayerDto(
                savedPlayer.getId(),
                savedPlayer.getName()
        );
    }

    public PlayerDto getById(Integer id) {
        Player savedPlayer = playerRepository.findById(id);
        return new PlayerDto(
                savedPlayer.getId(),
                savedPlayer.getName()
        );
    }
}
