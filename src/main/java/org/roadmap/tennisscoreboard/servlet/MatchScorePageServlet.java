package org.roadmap.tennisscoreboard.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.roadmap.tennisscoreboard.dto.view.MatchView;
import org.roadmap.tennisscoreboard.service.MatchScoreService;
import org.roadmap.tennisscoreboard.service.MatchService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScorePageServlet extends HttpServlet {
    private MatchService matchService;
    private MatchScoreService matchScoreService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        this.matchService = (MatchService) context.getAttribute("matchService");
        this.matchScoreService = (MatchScoreService) context.getAttribute("matchScoreService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchId = req.getParameter("uuid");
        System.out.println(matchId);

        OngoingMatch match = matchService.getById(UUID.fromString(matchId));
        System.out.println(match);

        MatchView matchView = new MatchView(
                match.getFirstPlayer(),
                match.getSecondPlayer(),
                null,
                match.getScore(),
                match.getScore().getFirstPlayerSet(),
                match.getScore().getSecondPlayerSet(),
                0,
                0,
                0,
                0
        );

        req.setAttribute("match", matchView);
        req.setAttribute("uuid", matchId);

        req.getRequestDispatcher("WEB-INF/match-score.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        UUID matchId = UUID.fromString(uuid);

        Integer playerId = Integer.valueOf(req.getParameter("playerId"));

        OngoingMatch match = matchService.getById(matchId);
        System.out.println(match);

        Integer firstPlayerId = match.getFirstPlayer().getId();
        Integer secondPlayerId = match.getSecondPlayer().getId();
        if (playerId.equals(firstPlayerId)) {
            matchScoreService.givePoint(firstPlayerId, match);
        }
        if (playerId.equals(secondPlayerId)) {
            matchScoreService.givePoint(secondPlayerId, match);
        }
        resp.sendRedirect("match-score?uuid=" + uuid);
    }
}
