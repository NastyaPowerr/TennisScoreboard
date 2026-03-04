package org.roadmap.tennisscoreboard.service;

import org.roadmap.tennisscoreboard.dto.PlayerDto;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.repository.PlayerRepositoryImpl;

public class PlayerService {
    private final PlayerRepositoryImpl playerRepositoryImpl;

    public PlayerService(PlayerRepositoryImpl playerRepositoryImpl) {
        this.playerRepositoryImpl = playerRepositoryImpl;
    }

    public PlayerDto create(PlayerDto player) {
        Player entity = new Player(null, player.name());
        Player savedPlayer = playerRepositoryImpl.save(entity);
        return new PlayerDto(
                savedPlayer.getId(),
                savedPlayer.getName()
        );
    }

    public PlayerDto getById(Integer id) {
        Player savedPlayer = playerRepositoryImpl.findById(id);
        return new PlayerDto(
                savedPlayer.getId(),
                savedPlayer.getName()
        );
    }
}
