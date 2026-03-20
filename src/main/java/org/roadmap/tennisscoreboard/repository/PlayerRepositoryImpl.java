package org.roadmap.tennisscoreboard.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.exception.ExceptionMessages;
import org.roadmap.tennisscoreboard.exception.PlayerAlreadyExistsException;

import java.util.Optional;

public class PlayerRepositoryImpl implements PlayerRepository {
    private static final String FIND_BY_NAME = """
            FROM Player
            WHERE name =:name
            """;
    private final SessionFactory sessionFactory;

    public PlayerRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
    public Optional<Player> findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.find(Player.class, id));
    }

    @Override
    public Optional<Player> findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(
                session.createQuery(FIND_BY_NAME, Player.class)
                        .setParameter("name", name)
                        .uniqueResult()
        );
    }
}
