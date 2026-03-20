package org.roadmap.tennisscoreboard.repository;

import org.roadmap.tennisscoreboard.entity.Player;

import java.util.Optional;

public interface PlayerRepository extends BaseRepository<Player> {
    Player save(Player player);

    Optional<Player> findById(Integer id);

    Optional<Player> findByName(String name);
}
