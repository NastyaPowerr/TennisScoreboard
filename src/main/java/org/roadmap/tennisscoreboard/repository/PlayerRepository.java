package org.roadmap.tennisscoreboard.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.util.HibernateSessionFactoryUtil;

public class PlayerRepository {
    private final SessionFactory sessionFactory;

    public PlayerRepository() {
        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    public Player save(Player player) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(player);
        transaction.commit();
        session.close();
        return player;
    }

    public Player findById(Integer id) {
        Session session = sessionFactory.openSession();
        return session.find(Player.class, id);
    }
}
