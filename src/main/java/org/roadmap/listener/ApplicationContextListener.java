package org.roadmap.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.roadmap.H2DatabaseViewer;
import org.roadmap.model.dto.MatchDto;
import org.roadmap.repository.MatchRepository;
import org.roadmap.repository.PlayerRepository;
import org.roadmap.service.MatchService;
import org.roadmap.service.PlayerService;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        H2DatabaseViewer.start();

        PlayerRepository playerRepository = new PlayerRepository();
        MatchRepository matchRepository = new MatchRepository();
        MatchService matchService = new MatchService(matchRepository, playerRepository);
        PlayerService playerService = new PlayerService(playerRepository);

        Map<UUID, MatchDto> matches = new ConcurrentHashMap<>();

        ServletContext context = sce.getServletContext();

        context.setAttribute("playerRepository", playerRepository);
        context.setAttribute("matchRepository", matchRepository);
        context.setAttribute("matchService", matchService);
        context.setAttribute("playerService", playerService);
        context.setAttribute("matches", matches);
    }
}
