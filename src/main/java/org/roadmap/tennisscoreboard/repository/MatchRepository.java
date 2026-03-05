package org.roadmap.tennisscoreboard.repository;

import org.roadmap.tennisscoreboard.entity.Match;

import java.util.List;

public interface MatchRepository extends BaseRepository<Match> {
    Match save(Match entity);

    List<Match> findAll(int pageSize, int offset, String filterName);

    long getCount();
}
