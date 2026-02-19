package org.roadmap.tennisscoreboard.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.roadmap.tennisscoreboard.entity.Match;
import org.roadmap.tennisscoreboard.util.HibernateSessionFactoryUtil;

import java.util.List;

public class MatchRepository {
    private final SessionFactory sessionFactory;

    public MatchRepository() {
        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    public Match save(Match entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
        return entity;
    }

    public List<Match> findAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("from " + Match.class.getName()).list();
    }
}
