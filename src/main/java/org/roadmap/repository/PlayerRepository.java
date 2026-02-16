package org.roadmap.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.roadmap.model.entity.PlayerEntity;
import org.roadmap.util.HibernateSessionFactoryUtil;

public class PlayerRepository {
    private final SessionFactory sessionFactory;

    public PlayerRepository() {
        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    public PlayerEntity save(PlayerEntity playerEntity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(playerEntity);
        transaction.commit();
        session.close();
        return playerEntity;
    }
}
