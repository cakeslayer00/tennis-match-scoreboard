package com.vladsv.tennismatchscoreboard.dao.impl;

import com.vladsv.tennismatchscoreboard.dao.Dao;
import com.vladsv.tennismatchscoreboard.model.FinishedMatch;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.SelectionQuery;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

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

    public List<FinishedMatch> findAllPaginated(int pageNo, int elementsPerPage) {
        Session session = sessionFactory.openSession();
        SelectionQuery<FinishedMatch> query = session.createSelectionQuery("from FinishedMatch", FinishedMatch.class)
                .setFirstResult((pageNo - 1) * elementsPerPage)
                .setMaxResults(elementsPerPage);
        return applyPagination(pageNo, elementsPerPage, query).getResultList();
    }

    public List<FinishedMatch> findByFilter(int pageNo, int elementsPerPage, String filterName) {
        Session session = sessionFactory.openSession();
        String hqlString = "from FinishedMatch m where m.firstPlayer.name = :name or m.secondPlayer.name = :name";

        SelectionQuery<FinishedMatch> query = session.createSelectionQuery(hqlString, FinishedMatch.class)
                .setParameter("name", filterName);
        return applyPagination(pageNo,elementsPerPage,query).getResultList();
    }

    @Override
    public void delete(FinishedMatch match) {
        sessionFactory.inTransaction(session -> {
            session.remove(match);
        });
    }

    private SelectionQuery<FinishedMatch> applyPagination(int pageNo, int elementsPerPage, SelectionQuery<FinishedMatch> query) {
        return query.setFirstResult((pageNo - 1) * elementsPerPage).setMaxResults(elementsPerPage);
    }
}
