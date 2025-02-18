package com.vladsv.tennismatchscoreboard.dao.impl;

import com.vladsv.tennismatchscoreboard.dao.Dao;
import com.vladsv.tennismatchscoreboard.model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class PlayerDao implements Dao<Player> {
    private final SessionFactory sessionFactory;

    public PlayerDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void persist(Player player) {
        sessionFactory.inTransaction(session -> session.persist(player));
    }

    @Override
    public Optional<Player> findById(Long id) {
        Session session = sessionFactory.openSession();
        Player player = session.createSelectionQuery("from Player where id = :id", Player.class)
                .setParameter("id", id)
                .uniqueResult();

        session.close();
        return Optional.ofNullable(player);
    }

    @Override
    public List<Player> findAll() {
        Session session = sessionFactory.openSession();
        List<Player> res = session.
                createSelectionQuery("from Player", Player.class).getResultList();

        session.close();
        return res;
    }

    @Override
    public void delete(Player player) {
        sessionFactory.inTransaction(session -> session.remove(player));
    }

    public Optional<Player> findByName(String name) {
        Session session = sessionFactory.openSession();
        Player player = session.createSelectionQuery("from Player where name = :name", Player.class)
                .setParameter("name", name)
                .uniqueResult();

        session.close();
        return Optional.ofNullable(player);
    }

}
