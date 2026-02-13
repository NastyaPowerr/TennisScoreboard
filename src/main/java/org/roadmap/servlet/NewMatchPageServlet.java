package org.roadmap.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.roadmap.model.dto.MatchDto;
import org.roadmap.model.dto.Score;
import org.roadmap.model.entity.PlayerEntity;
import org.roadmap.repository.PlayerRepository;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchPageServlet extends HttpServlet {
    private PlayerRepository playerRepository;
    private Map<UUID, MatchDto> matches;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        this.playerRepository = (PlayerRepository) context.getAttribute("playerRepository");
        this.matches = (Map<UUID, MatchDto>) context.getAttribute("matches");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String firstPlayerName = req.getParameter("player1");
        String secondPlayerName = req.getParameter("player2");

        PlayerEntity firstPlayerEntity = playerRepository.save(new PlayerEntity(null, firstPlayerName));
        PlayerEntity secondPlayerEntity = playerRepository.save(new PlayerEntity(null, secondPlayerName));

        // экземпляр класса, содержащего айди игроков и текущий счёт (ТЗ)
        MatchDto match = new MatchDto(firstPlayerEntity.getId(), secondPlayerEntity.getId(), new Score(0, 0));
        UUID uuid = UUID.randomUUID();

        matches.put(uuid, match);

        resp.sendRedirect("match-score?uuid=" + uuid);
    }
}
