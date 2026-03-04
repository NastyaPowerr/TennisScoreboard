package org.roadmap.tennisscoreboard.repository;

import org.roadmap.tennisscoreboard.entity.Player;

public interface PlayerRepository extends BaseRepository<Player> {
    Player save(Player player);

    Player findById(Integer id);
}
