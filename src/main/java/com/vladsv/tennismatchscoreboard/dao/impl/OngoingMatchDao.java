package com.vladsv.tennismatchscoreboard.dao.impl;

import com.vladsv.tennismatchscoreboard.dao.Dao;
import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class OngoingMatchDao implements Dao<OngoingMatch> {

    private final Map<UUID, OngoingMatch> matches;

    @Deprecated
    @Override
    public void persist(OngoingMatch entity) {
        matches.put(UUID.randomUUID(), entity);
    }

    public void persist(UUID uuid, OngoingMatch entity) {
        matches.put(uuid, entity);
    }

    @Deprecated
    @Override
    public Optional<OngoingMatch> findById(Long id) {
        return Optional.empty();
    }

    public Optional<OngoingMatch> findById(UUID id) {
        return Optional.ofNullable(matches.get(id));
    }

    @Override
    public List<OngoingMatch> findAll() {
        return new ArrayList<>(matches.values());
    }

    @Deprecated
    @Override
    public void delete(OngoingMatch match) {
        matches.entrySet().removeIf(entry -> entry.getValue().equals(match));
    }

    public void delete(UUID id) {
        matches.remove(id);
    }

}
