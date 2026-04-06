package org.roadmap.tennisscoreboard.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;
import org.roadmap.tennisscoreboard.repository.MatchRepositoryImpl;
import org.roadmap.tennisscoreboard.repository.PlayerRepositoryImpl;
import org.roadmap.tennisscoreboard.service.FinishedMatchesPersistenceService;
import org.roadmap.tennisscoreboard.service.MatchScoreService;
import org.roadmap.tennisscoreboard.service.OngoingMatchService;
import org.roadmap.tennisscoreboard.service.PlayerService;
import org.roadmap.tennisscoreboard.util.HibernateSessionFactory;
import org.roadmap.tennisscoreboard.util.TransactionController;
import tools.jackson.databind.ObjectMapper;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    private SessionFactory sessionFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sessionFactory = HibernateSessionFactory.createSessionFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        PlayerRepositoryImpl playerRepositoryImpl = new PlayerRepositoryImpl(sessionFactory);
        MatchRepositoryImpl matchRepositoryImpl = new MatchRepositoryImpl(sessionFactory);
        FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService(
                matchRepositoryImpl,
                playerRepositoryImpl,
                sessionFactory
        );
        OngoingMatchService ongoingMatchService = new OngoingMatchService();
        MatchScoreService matchScoreService = new MatchScoreService(
                ongoingMatchService,
                finishedMatchesPersistenceService
        );
        PlayerService playerService = new PlayerService(playerRepositoryImpl, sessionFactory);

        ServletContext context = sce.getServletContext();

        context.setAttribute("sessionFactory", sessionFactory);
        context.setAttribute("objectMapper", objectMapper);
        context.setAttribute("playerRepository", playerRepositoryImpl);
        context.setAttribute("matchRepository", matchRepositoryImpl);
        context.setAttribute("finishedMatchesPersistenceService", finishedMatchesPersistenceService);
        context.setAttribute("ongoingMatchService", ongoingMatchService);
        context.setAttribute("playerService", playerService);
        context.setAttribute("matchScoreService", matchScoreService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
