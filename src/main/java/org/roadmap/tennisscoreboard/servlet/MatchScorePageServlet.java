package org.roadmap.tennisscoreboard.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.roadmap.tennisscoreboard.domain.SetScoreInfo;
import org.roadmap.tennisscoreboard.dto.view.FinishedMatchView;
import org.roadmap.tennisscoreboard.dto.view.MatchView;
import org.roadmap.tennisscoreboard.exception.ExceptionMessages;
import org.roadmap.tennisscoreboard.service.MatchScoreService;
import org.roadmap.tennisscoreboard.service.OngoingMatchService;
import org.roadmap.tennisscoreboard.util.MatchValidatorUtil;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScorePageServlet extends HttpServlet {
    private OngoingMatchService ongoingMatchService;
    private MatchScoreService matchScoreService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        this.ongoingMatchService = (OngoingMatchService) context.getAttribute("ongoingMatchService");
        this.matchScoreService = (MatchScoreService) context.getAttribute("matchScoreService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String matchId = req.getParameter("uuid");
        MatchValidatorUtil.validateUUID(matchId);
        OngoingMatch match = ongoingMatchService.getById(UUID.fromString(matchId))
                .orElseThrow(() -> new NoSuchElementException(
                        String.format(
                                ExceptionMessages.MATCH_NOT_FOUND,
                                matchId
                        )));

        Object matchView;
        if (!match.isFinished()) {
            matchView = getMatchViewDependingOnTieBreak(match);
            req.setAttribute("match", matchView);
            req.setAttribute("uuid", matchId);
            req.getRequestDispatcher("WEB-INF/match-score.jsp").forward(req, resp);
        } else {
            matchView = getMatchViewDependingOnFinishedMatch(match);
            req.setAttribute("match", matchView);
            req.setAttribute("uuid", matchId);
            req.getRequestDispatcher("WEB-INF/match-score-finished.jsp").forward(req, resp);
            ongoingMatchService.delete(UUID.fromString(matchId));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uuid = req.getParameter("uuid");
        MatchValidatorUtil.validateUUID(uuid);
        UUID matchId = UUID.fromString(uuid);

        String playerIdString = req.getParameter("playerId");
        MatchValidatorUtil.validatePlayerId(playerIdString);
        Integer playerId = Integer.valueOf(playerIdString);

        matchScoreService.givePoint(playerId, matchId);
        resp.sendRedirect("match-score?uuid=" + uuid);
    }

    private FinishedMatchView getMatchViewDependingOnFinishedMatch(OngoingMatch match) {
        Map<Integer, SetScoreInfo> setsHistory = match.getSetsHistory();
        return new FinishedMatchView(
                match.getFirstPlayer(),
                match.getSecondPlayer(),
                match.getScoreModel().getWinner(match.getFirstPlayer(), match.getSecondPlayer()).get(),
                setsHistory.get(0).firstPlayerGames(),
                setsHistory.get(0).secondPlayerGames(),
                setsHistory.get(1).firstPlayerGames(),
                setsHistory.get(1).secondPlayerGames(),
                setsHistory.get(2) != null ? setsHistory.get(2).firstPlayerGames() : 0,
                setsHistory.get(2) != null ? setsHistory.get(2).secondPlayerGames() : 0
        );
    }

    private MatchView getMatchViewDependingOnTieBreak(OngoingMatch match) {
        if (!match.isTiebreak()) {
            return new MatchView(
                    match.getFirstPlayer(),
                    match.getSecondPlayer(),
                    match.getScoreModel().getFirstPlayerScore().getPlayerPoint().toString(),
                    match.getScoreModel().getSecondPlayerScore().getPlayerPoint().toString(),
                    match.getScoreModel().getFirstPlayerScore().getPlayerGame(),
                    match.getScoreModel().getSecondPlayerScore().getPlayerGame(),
                    match.getScoreModel().getFirstPlayerScore().getPlayerSet(),
                    match.getScoreModel().getSecondPlayerScore().getPlayerSet()
            );
        } else {
            return new MatchView(
                    match.getFirstPlayer(),
                    match.getSecondPlayer(),
                    String.valueOf(match.getScoreModel().getFirstPlayerScore().getTiebreakPoints()),
                    String.valueOf(match.getScoreModel().getSecondPlayerScore().getTiebreakPoints()),
                    match.getScoreModel().getFirstPlayerScore().getPlayerGame(),
                    match.getScoreModel().getSecondPlayerScore().getPlayerGame(),
                    match.getScoreModel().getFirstPlayerScore().getPlayerSet(),
                    match.getScoreModel().getSecondPlayerScore().getPlayerSet()
            );
        }
    }
}
