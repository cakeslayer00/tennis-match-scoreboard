package com.vladsv.tennismatchscoreboard.dao;

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
        sessionFactory.inTransaction(session -> {
            session.persist(player);
        });
    }

    @Override
    public Optional<Player> findById(Long id) {
        Session session = sessionFactory.openSession();
        Player player = session.createSelectionQuery("from Player where id = :id", Player.class)
                .setParameter("id", id)
                .uniqueResult();

        return Optional.ofNullable(player);
    }

    @Override
    public List<Player> findAll() {
        Session session = sessionFactory.openSession();
        return session.
                createSelectionQuery("from Player", Player.class).getResultList();
    }

    public Optional<Player> findByName(String name) {
        Session session = sessionFactory.openSession();
        Player player = session.createSelectionQuery("from Player where name = :name", Player.class)
                .setParameter("name", name)
                .uniqueResult();

        return Optional.ofNullable(player);
    }
}
