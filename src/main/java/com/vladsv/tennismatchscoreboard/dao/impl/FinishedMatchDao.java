package com.vladsv.tennismatchscoreboard.dao.impl;

import com.vladsv.tennismatchscoreboard.dao.Dao;
import com.vladsv.tennismatchscoreboard.model.FinishedMatch;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Order;
import org.hibernate.query.SelectionQuery;

import java.util.List;
import java.util.Optional;

public class FinishedMatchDao implements Dao<FinishedMatch> {

    private final SessionFactory sessionFactory;

    public FinishedMatchDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void persist(FinishedMatch match) {
        sessionFactory.inTransaction(session -> session.persist(match));
    }

    @Override
    public Optional<FinishedMatch> findById(Long id) {
        Session session = sessionFactory.openSession();
        FinishedMatch match = session.createSelectionQuery("from FinishedMatch where id = :id", FinishedMatch.class)
                .setParameter("id", id)
                .uniqueResult();

        session.close();
        return Optional.ofNullable(match);
    }

    @Override
    public List<FinishedMatch> findAll() {
        Session session = sessionFactory.openSession();
        List<FinishedMatch> res = session.
                createSelectionQuery("from FinishedMatch", FinishedMatch.class).getResultList();
        session.close();
        return res;
    }

    public List<FinishedMatch> findByPage(int pageNo, int elementsPerPage) {
        Session session = sessionFactory.openSession();
        SelectionQuery<FinishedMatch> query = session.createSelectionQuery("from FinishedMatch f order by f.id desc", FinishedMatch.class);

        List<FinishedMatch> res = applyPagination(pageNo, elementsPerPage, query).getResultList();

        session.close();
        return res;
    }

    public List<FinishedMatch> findByFilter(int pageNo, int elementsPerPage, String filterName) {
        Session session = sessionFactory.openSession();
        String hqlString = "from FinishedMatch m " +
                "where lower(m.firstPlayer.name) like lower(:name) " +
                "or lower(m.secondPlayer.name) like lower(:name)" +
                "order by m.id desc";

        SelectionQuery<FinishedMatch> query = session.createSelectionQuery(hqlString, FinishedMatch.class)
                .setParameter("name", "%" + filterName + "%");

        List<FinishedMatch> res = applyPagination(pageNo, elementsPerPage, query).getResultList();

        session.close();
        return res;
    }

    public long getMatchesCount() {
        Session session = sessionFactory.openSession();
        String hqlString = "select count(*) from FinishedMatch";
        long res = session.createSelectionQuery(hqlString, Long.class).getResultCount();

        session.close();
        return res;
    }

    public long getFilteredMatchesCount(String filterName) {
        Session session = sessionFactory.openSession();
        String hqlString = "select count(*) from FinishedMatch m " +
                "where lower(m.firstPlayer.name) like lower(:name) " +
                "or lower(m.secondPlayer.name) like lower(:name)";

        long res = session.createSelectionQuery(hqlString, Long.class)
                .setParameter("name", "%" + filterName + "%").getResultCount();

        session.close();
        return res;
    }

    @Override
    public void delete(FinishedMatch match) {
        sessionFactory.inTransaction(session -> session.remove(match));
    }

    private SelectionQuery<FinishedMatch> applyPagination(int pageNo, int elementsPerPage, SelectionQuery<FinishedMatch> query) {
        return query.setFirstResult((pageNo - 1) * elementsPerPage).setMaxResults(elementsPerPage);
    }

}
