package org.roadmap.tennisscoreboard.service;

import org.roadmap.tennisscoreboard.domain.OngoingMatch;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchService {
    private final Map<UUID, OngoingMatch> matches;

    public OngoingMatchService() {
        this.matches = new ConcurrentHashMap<>();
    }

    public UUID create(OngoingMatch match) {
        UUID uuid = UUID.randomUUID();
        matches.put(uuid, match);
        return uuid;
    }

    public OngoingMatch getById(UUID id) {
        return matches.get(id);
    }

    public void delete(UUID id) {
        matches.remove(id);
    }
}
