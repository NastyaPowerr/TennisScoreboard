package org.roadmap.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.roadmap.dto.MatchDto;
import org.roadmap.dto.Score;
import org.roadmap.entity.Player;
import org.roadmap.repository.PlayerRepository;
import org.roadmap.service.MatchService;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String firstPlayerName = req.getParameter("player1");
        String secondPlayerName = req.getParameter("player2");

        Player firstPlayer = playerRepository.save(new Player(null, firstPlayerName));
        Player secondPlayer = playerRepository.save(new Player(null, secondPlayerName));

        // экземпляр класса, содержащего айди игроков и текущий счёт (ТЗ)
        MatchDto match = new MatchDto(firstPlayer.getId(), secondPlayer.getId(), new Score(0, 0));
        UUID uuid = matchService.create(match);

        resp.sendRedirect("match-score?uuid=" + uuid);
    }
}
