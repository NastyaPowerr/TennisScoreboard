package org.roadmap.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.roadmap.model.dto.MatchDto;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScorePageServlet extends HttpServlet {
    private Map<UUID, MatchDto> matches;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        this.matches = (Map<UUID, MatchDto>) context.getAttribute("matches");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchId = req.getParameter("uuid");

        MatchDto match = matches.get(UUID.fromString(matchId));
        System.out.println(match);

        resp.getWriter().write("Match found");
    }
}
