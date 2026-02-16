package org.roadmap.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.roadmap.model.entity.MatchEntity;
import org.roadmap.model.entity.PlayerEntity;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(PlayerEntity.class);
                configuration.addAnnotatedClass(MatchEntity.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception ex) {
                System.out.println("Exception in getting HibernateSessionFactory!" + ex);
            }
        }
        return sessionFactory;
    }
}
