package org.roadmap.tennisscoreboard.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.roadmap.tennisscoreboard.entity.Match;
import org.roadmap.tennisscoreboard.entity.Player;

public final class HibernateSessionFactory {
    private HibernateSessionFactory() {
    }

    public static SessionFactory createSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Player.class);
            configuration.addAnnotatedClass(Match.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
            return configuration.buildSessionFactory(builder.build());
        } catch (Exception ex) {
            throw new RuntimeException("Couldn't create session factory: " + ex.getMessage());
        }
    }
}