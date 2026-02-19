package org.roadmap.tennisscoreboard.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.roadmap.tennisscoreboard.H2DatabaseViewer;
import org.roadmap.tennisscoreboard.repository.MatchRepository;
import org.roadmap.tennisscoreboard.repository.PlayerRepository;
import org.roadmap.tennisscoreboard.service.MatchScoreService;
import org.roadmap.tennisscoreboard.service.MatchService;
import org.roadmap.tennisscoreboard.service.PlayerService;
import tools.jackson.databind.ObjectMapper;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        H2DatabaseViewer.start();

        ObjectMapper objectMapper = new ObjectMapper();
        PlayerRepository playerRepository = new PlayerRepository();
        MatchRepository matchRepository = new MatchRepository();
        MatchService matchService = new MatchService(matchRepository, playerRepository);
        MatchScoreService matchScoreService = new MatchScoreService(matchService);
        PlayerService playerService = new PlayerService(playerRepository);

        ServletContext context = sce.getServletContext();

        context.setAttribute("objectMapper", objectMapper);
        context.setAttribute("playerRepository", playerRepository);
        context.setAttribute("matchRepository", matchRepository);
        context.setAttribute("matchService", matchService);
        context.setAttribute("playerService", playerService);
        context.setAttribute("matchScoreService", matchScoreService);
    }
}
