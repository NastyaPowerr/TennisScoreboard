package org.roadmap.tennisscoreboard.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.exception.ExceptionMessages;
import org.roadmap.tennisscoreboard.exception.PlayerAlreadyExistsException;
import org.roadmap.tennisscoreboard.util.HibernateSessionFactoryUtil;

public class PlayerRepositoryImpl implements PlayerRepository {
    private final SessionFactory sessionFactory;

    public PlayerRepositoryImpl() {
        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    @Override
    public Player save(Player player) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(player);
            return player;
        } catch (ConstraintViolationException ex) {
            throw new PlayerAlreadyExistsException(
                    String.format(
                            ExceptionMessages.PLAYER_ALREADY_EXISTS,
                            player.getName()
                    )
            );
        }
    }

    @Override
    public Player findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Player.class, id);
    }
}
