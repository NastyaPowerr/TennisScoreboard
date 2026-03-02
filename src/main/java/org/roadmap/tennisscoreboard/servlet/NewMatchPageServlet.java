package org.roadmap.tennisscoreboard.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.roadmap.tennisscoreboard.domain.Score;
import org.roadmap.tennisscoreboard.dto.PlayerDto;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.service.MatchService;
import org.roadmap.tennisscoreboard.service.PlayerService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchPageServlet extends HttpServlet {
    private PlayerService playerService;
    private MatchService matchService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        this.playerService = (PlayerService) context.getAttribute("playerService");
        this.matchService = (MatchService) context.getAttribute("matchService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String firstPlayerName = req.getParameter("firstPlayerName");
        String secondPlayerName = req.getParameter("secondPlayerName");

        System.out.println(firstPlayerName);
        System.out.println(secondPlayerName);
        PlayerDto firstPlayer = playerService.create(new PlayerDto(null, firstPlayerName));
        PlayerDto secondPlayer = playerService.create(new PlayerDto(null, secondPlayerName));

        // экземпляр класса, содержащего айди игроков и текущий счёт (ТЗ)
        OngoingMatch match = new OngoingMatch(
                null,
                new Player(firstPlayer.id(), firstPlayer.name()),
                new Player(secondPlayer.id(), secondPlayer.name()),
                new Score()
        );
        UUID uuid = matchService.create(match);

        resp.sendRedirect("match-score?uuid=" + uuid);
    }
}
