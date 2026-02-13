package org.roadmap.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.roadmap.model.dto.MatchDto;
import org.roadmap.repository.PlayerRepository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class ApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        PlayerRepository playerRepository = new PlayerRepository();
        Map<UUID, MatchDto> matches = new ConcurrentHashMap<>();

        ServletContext context = sce.getServletContext();

        context.setAttribute("playerRepository", playerRepository);
        context.setAttribute("matches", matches);
    }
}
