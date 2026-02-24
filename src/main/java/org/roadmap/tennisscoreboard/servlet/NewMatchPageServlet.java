package org.roadmap.tennisscoreboard.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.roadmap.tennisscoreboard.domain.Score;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.repository.PlayerRepository;
import org.roadmap.tennisscoreboard.service.MatchService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchPageServlet extends HttpServlet {
    private PlayerRepository playerRepository;
    private MatchService matchService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        this.playerRepository = (PlayerRepository) context.getAttribute("playerRepository");
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
        Player firstPlayer = playerRepository.save(new Player(null, firstPlayerName));
        Player secondPlayer = playerRepository.save(new Player(null, secondPlayerName));

        // экземпляр класса, содержащего айди игроков и текущий счёт (ТЗ)
        OngoingMatch match = new OngoingMatch(null, firstPlayer, secondPlayer, new Score(0, 0));
        UUID uuid = matchService.create(match);

        resp.sendRedirect("match-score?uuid=" + uuid);
    }
}
