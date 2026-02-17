package org.roadmap.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.roadmap.dto.response.MatchDtoResponse;
import org.roadmap.service.MatchService;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<MatchDtoResponse> matches = matchService.getAll();

        resp.setContentType("application/json");
        resp.setStatus(200);

        String jsonResponse = objectMapper.writeValueAsString(matches);
        resp.getWriter().write(jsonResponse);
    }
}
