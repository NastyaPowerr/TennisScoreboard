package org.roadmap.tennisscoreboard.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.roadmap.tennisscoreboard.dto.response.FinishedMatchDto;
import org.roadmap.tennisscoreboard.service.MatchService;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class FinishedMatchesServlet extends HttpServlet {
    private MatchService matchService;
    private ObjectMapper objectMapper;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        this.matchService = (MatchService) context.getAttribute("matchService");
        this.objectMapper = (ObjectMapper) context.getAttribute("objectMapper");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<FinishedMatchDto> matches = matchService.getAll();
        req.setAttribute("matches", matches);

        req.getRequestDispatcher("WEB-INF/matches.jsp").forward(req, resp);
    }
}
