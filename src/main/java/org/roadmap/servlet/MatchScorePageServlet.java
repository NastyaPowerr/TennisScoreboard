package org.roadmap.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.roadmap.dto.MatchDto;
import org.roadmap.service.MatchService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScorePageServlet extends HttpServlet {
    private MatchService matchService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        this.matchService = (MatchService) context.getAttribute("matchService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchId = req.getParameter("uuid");
        System.out.println(matchId);

        MatchDto match = matchService.getById(UUID.fromString(matchId));
        System.out.println(match);

        req.setAttribute("match", match);
        req.setAttribute("uuid", matchId);

        req.getRequestDispatcher("WEB-INF/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        UUID matchId = UUID.fromString(uuid);

        Integer playerId = Integer.valueOf(req.getParameter("playerId"));

        MatchDto match = matchService.getById(matchId);
        System.out.println(match);

        Integer firstPlayerId = match.getFirstPlayerId();
        Integer secondPlayerId = match.getSecondPlayerId();
        if (playerId.equals(firstPlayerId)) {
            matchService.givePoint(match.getFirstPlayerId(), match);
        }
        if (playerId.equals(secondPlayerId)) {
            matchService.givePoint(match.getSecondPlayerId(), match);
        }
        resp.sendRedirect("match-score?uuid=" + uuid);
    }
}
