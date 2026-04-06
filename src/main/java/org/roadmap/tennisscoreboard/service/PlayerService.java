package org.roadmap.tennisscoreboard.service;

import org.hibernate.SessionFactory;
import org.roadmap.tennisscoreboard.dto.PlayerDto;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.repository.PlayerRepositoryImpl;
import org.roadmap.tennisscoreboard.util.TransactionController;

public class PlayerService {
    private final PlayerRepositoryImpl playerRepositoryImpl;
    private final SessionFactory sessionFactory;

    public PlayerService(PlayerRepositoryImpl playerRepositoryImpl, SessionFactory sessionFactory) {
        this.playerRepositoryImpl = playerRepositoryImpl;
        this.sessionFactory = sessionFactory;
    }

    public PlayerDto findOrCreate(PlayerDto playerDto) {
        return TransactionController.execute(sessionFactory, () -> {
            Player player = playerRepositoryImpl.findByName(playerDto.name())
                    .orElseGet(() -> playerRepositoryImpl.save(new Player(playerDto.name())));
            return new PlayerDto(
                    player.getId(),
                    player.getName()
            );
        });
    }
}
