package org.roadmap.tennisscoreboard.service;

import org.roadmap.tennisscoreboard.dto.PlayerDto;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.repository.PlayerRepositoryImpl;

public class PlayerService {
    private final PlayerRepositoryImpl playerRepositoryImpl;

    public PlayerService(PlayerRepositoryImpl playerRepositoryImpl) {
        this.playerRepositoryImpl = playerRepositoryImpl;
    }

    public PlayerDto findOrCreate(PlayerDto playerDto) {
        Player player = playerRepositoryImpl.findByName(playerDto.name())
                .orElseGet(() -> playerRepositoryImpl.save(new Player(playerDto.name())));
        return new PlayerDto(
                player.getId(),
                player.getName()
        );
    }
}
