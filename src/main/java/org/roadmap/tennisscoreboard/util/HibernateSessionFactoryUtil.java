package org.roadmap.tennisscoreboard.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.roadmap.tennisscoreboard.entity.Match;
import org.roadmap.tennisscoreboard.entity.Player;

public final class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Player.class);
                configuration.addAnnotatedClass(Match.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception ex) {
                System.out.println("Exception in getting HibernateSessionFactory!" + ex);
            }
        }
        return sessionFactory;
    }
}
