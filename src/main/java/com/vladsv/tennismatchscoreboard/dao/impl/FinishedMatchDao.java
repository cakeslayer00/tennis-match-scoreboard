package com.vladsv.tennismatchscoreboard.dao.impl;

import com.vladsv.tennismatchscoreboard.dao.Dao;
import com.vladsv.tennismatchscoreboard.model.FinishedMatch;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class FinishedMatchDao implements Dao<FinishedMatch> {

    private final SessionFactory sessionFactory;

    public FinishedMatchDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void persist(FinishedMatch match) {
        sessionFactory.inTransaction(session -> {
            session.persist(match);
        });
    }

    @Override
    public Optional<FinishedMatch> findById(Long id) {
        Session session = sessionFactory.openSession();
        FinishedMatch match = session.createSelectionQuery("from FinishedMatch where id = :id", FinishedMatch.class)
                .setParameter("id", id)
                .uniqueResult();

        return Optional.ofNullable(match);
    }

    @Override
    public List<FinishedMatch> findAll() {
        Session session = sessionFactory.openSession();
        return session.
                createSelectionQuery("from FinishedMatch", FinishedMatch.class).getResultList();
    }

    @Override
    public void delete(FinishedMatch match) {
        sessionFactory.inTransaction(session -> {
            session.remove(match);
        });
    }
}
