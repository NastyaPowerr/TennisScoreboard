package org.roadmap.tennisscoreboard.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.util.HibernateSessionFactoryUtil;

public class PlayerRepositoryImpl implements PlayerRepository {
    private final SessionFactory sessionFactory;

    public PlayerRepositoryImpl() {
        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    @Override
    public Player save(Player player) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(player);
        return player;
    }

    @Override
    public Player findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Player.class, id);
    }
}
