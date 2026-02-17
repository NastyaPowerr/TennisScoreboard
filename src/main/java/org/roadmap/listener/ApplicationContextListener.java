package org.roadmap.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.roadmap.H2DatabaseViewer;
import org.roadmap.repository.MatchRepository;
import org.roadmap.repository.PlayerRepository;
import org.roadmap.service.MatchService;
import org.roadmap.service.PlayerService;
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
        PlayerService playerService = new PlayerService(playerRepository);

        ServletContext context = sce.getServletContext();

        context.setAttribute("objectMapper", objectMapper);
        context.setAttribute("playerRepository", playerRepository);
        context.setAttribute("matchRepository", matchRepository);
        context.setAttribute("matchService", matchService);
        context.setAttribute("playerService", playerService);
    }
}
