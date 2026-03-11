package org.roadmap.tennisscoreboard.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.roadmap.tennisscoreboard.H2DatabaseViewer;
import org.roadmap.tennisscoreboard.repository.MatchRepositoryImpl;
import org.roadmap.tennisscoreboard.repository.PlayerRepositoryImpl;
import org.roadmap.tennisscoreboard.service.FinishedMatchesPersistenceService;
import org.roadmap.tennisscoreboard.service.MatchScoreService;
import org.roadmap.tennisscoreboard.service.OngoingMatchService;
import org.roadmap.tennisscoreboard.service.PlayerService;
import tools.jackson.databind.ObjectMapper;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        H2DatabaseViewer.start();

        ObjectMapper objectMapper = new ObjectMapper();
        PlayerRepositoryImpl playerRepositoryImpl = new PlayerRepositoryImpl();
        MatchRepositoryImpl matchRepositoryImpl = new MatchRepositoryImpl();
        FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService(matchRepositoryImpl);
        OngoingMatchService ongoingMatchService = new OngoingMatchService();
        MatchScoreService matchScoreService = new MatchScoreService(ongoingMatchService, finishedMatchesPersistenceService);
        PlayerService playerService = new PlayerService(playerRepositoryImpl);

        ServletContext context = sce.getServletContext();

        context.setAttribute("objectMapper", objectMapper);
        context.setAttribute("playerRepository", playerRepositoryImpl);
        context.setAttribute("matchRepository", matchRepositoryImpl);
        context.setAttribute("finishedMatchesPersistenceService", finishedMatchesPersistenceService);
        context.setAttribute("ongoingMatchService", ongoingMatchService);
        context.setAttribute("playerService", playerService);
        context.setAttribute("matchScoreService", matchScoreService);
    }
}
