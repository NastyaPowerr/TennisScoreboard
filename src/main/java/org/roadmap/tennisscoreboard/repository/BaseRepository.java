package org.roadmap.tennisscoreboard.repository;

public interface BaseRepository<T> {
    T save(T entity);
}
