package org.roadmap.service;

import org.roadmap.model.dto.PlayerDtoRequest;
import org.roadmap.model.entity.PlayerEntity;
import org.roadmap.repository.PlayerRepository;

public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerDtoRequest create(PlayerDtoRequest player) {
        PlayerEntity entity = new PlayerEntity(null, player.name());
        PlayerEntity savedPlayer = playerRepository.save(entity);
        return new PlayerDtoRequest(
                savedPlayer.getId(),
                savedPlayer.getName()
        );
    }

    public PlayerDtoRequest getById(Integer id) {
        PlayerEntity savedPlayer = playerRepository.findById(id);
        return new PlayerDtoRequest(
                savedPlayer.getId(),
                savedPlayer.getName()
        );
    }
}
