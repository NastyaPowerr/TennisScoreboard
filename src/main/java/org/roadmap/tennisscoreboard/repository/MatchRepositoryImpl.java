package org.roadmap.tennisscoreboard.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.roadmap.tennisscoreboard.entity.Match;
import org.roadmap.tennisscoreboard.util.HibernateSessionFactoryUtil;

import java.util.List;

public class MatchRepositoryImpl implements MatchRepository {
    private final SessionFactory sessionFactory;
    private static final String FIND_ALL_PAGINATION = """
            FROM Match
            """;
    private static final String FIND_ALL_PAGINATION_WITH_FILTER = """
            SELECT matches FROM Match matches
            WHERE matches.firstPlayer.name LIKE :name
            OR matches.secondPlayer.name LIKE :name
            """;

    public MatchRepositoryImpl() {
        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    @Override
    public Match save(Match entity) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(entity);
        return entity;
    }

    @Override
    public List<Match> findAll(int pageSize, int offset, String filterName) {
        Session session = sessionFactory.getCurrentSession();
        return session
                .createQuery(FIND_ALL_PAGINATION_WITH_FILTER, Match.class)
                .setParameter("name", "%" + filterName + "%")
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .list();
    }

    public List<Match> findMatchesWithPagination(int pageSize, int offset) {
        Session session = sessionFactory.getCurrentSession();
        return session
                .createQuery(FIND_ALL_PAGINATION, Match.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .list();
    }
}
