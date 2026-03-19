package org.roadmap.tennisscoreboard.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.roadmap.tennisscoreboard.dto.PlayerDto;
import org.roadmap.tennisscoreboard.exception.ExceptionMessages;
import org.roadmap.tennisscoreboard.service.OngoingMatchService;
import org.roadmap.tennisscoreboard.service.PlayerService;
import org.roadmap.tennisscoreboard.util.MatchValidator;

import java.io.IOException;
import java.util.UUID;

@WebServlet(PagePaths.NEW_MATCH)
public class NewMatchServlet extends HttpServlet {
    private PlayerService playerService;
    private OngoingMatchService ongoingMatchService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        this.playerService = (PlayerService) context.getAttribute("playerService");
        this.ongoingMatchService = (OngoingMatchService) context.getAttribute("ongoingMatchService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PagePaths.NEW_MATCH_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String firstPlayerName = req.getParameter("firstPlayerName");
        String secondPlayerName = req.getParameter("secondPlayerName");
        MatchValidator.validateName(firstPlayerName);
        MatchValidator.validateName(secondPlayerName);

        if (firstPlayerName.equals(secondPlayerName)) {
            req.setAttribute("error", ExceptionMessages.PLAYERS_THE_SAME_NAME);
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            req.getRequestDispatcher(PagePaths.NEW_MATCH_JSP).forward(req, resp);
            return;
        }
        PlayerDto firstPlayer = playerService.create(new PlayerDto(null, firstPlayerName));
        PlayerDto secondPlayer = playerService.create(new PlayerDto(null, secondPlayerName));

        OngoingMatch match = new OngoingMatch(
                firstPlayer,
                secondPlayer
        );
        UUID matchId = ongoingMatchService.create(match);
        resp.sendRedirect(req.getContextPath() + PagePaths.MATCH_SCORE_PAGE + "?uuid=" + matchId);
    }
}
