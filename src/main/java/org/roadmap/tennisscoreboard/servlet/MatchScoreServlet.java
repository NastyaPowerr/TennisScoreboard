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
import org.roadmap.tennisscoreboard.util.OngoingMatchMapper;
import org.roadmap.tennisscoreboard.service.MatchScoreService;
import org.roadmap.tennisscoreboard.service.OngoingMatchService;
import org.roadmap.tennisscoreboard.util.MatchValidator;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@WebServlet(PagePaths.MATCH_SCORE_PAGE)
public class MatchScoreServlet extends HttpServlet {
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
        UUID matchId = validateAndExtractUUID(req);
        OngoingMatch match = getById(matchId);

        if (match.isFinished()) {
            handleFinishedMatch(req, resp, match, matchId);
        } else {
            handleOngoingMatch(req, resp, match, matchId);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UUID matchId = validateAndExtractUUID(req);
        Integer playerId = validateAndExtractPlayerId(req);

        matchScoreService.givePoint(playerId, matchId);
        resp.sendRedirect(req.getContextPath() + PagePaths.MATCH_SCORE_PAGE + "?uuid=" + matchId);
    }

    private static Integer validateAndExtractPlayerId(HttpServletRequest req) {
        String playerIdString = req.getParameter("playerId");
        MatchValidator.validatePlayerId(playerIdString);
        return Integer.valueOf(playerIdString);
    }

    private static UUID validateAndExtractUUID(HttpServletRequest req) {
        String uuid = req.getParameter("uuid");
        MatchValidator.validateUUID(uuid);
        return UUID.fromString(uuid);
    }

    private void handleFinishedMatch(HttpServletRequest req, HttpServletResponse resp, OngoingMatch match, UUID matchId) throws ServletException, IOException {
        Map<Integer, SetScoreInfo> setsHistory = match.getSetsHistory();
        FinishedMatchView matchView = OngoingMatchMapper.toFinishedMatchView(match, matchId, setsHistory);
        req.setAttribute("match", matchView);
        req.getRequestDispatcher(PagePaths.MATCH_SCORE_PAGE_FINISHED_JSP).forward(req, resp);
        ongoingMatchService.delete(matchId);
    }

    private void handleOngoingMatch(HttpServletRequest req, HttpServletResponse resp, OngoingMatch match, UUID matchId) throws ServletException, IOException {
        MatchView matchView = getMatchViewDependingOnTieBreak(match, matchId);
        req.setAttribute("match", matchView);
        req.getRequestDispatcher(PagePaths.MATCH_SCORE_PAGE_JSP).forward(req, resp);
    }

    private MatchView getMatchViewDependingOnTieBreak(OngoingMatch match, UUID matchId) {
        if (match.isTiebreak()) {
            return OngoingMatchMapper.toTiebreakMatchView(match, matchId);
        } else {
            return OngoingMatchMapper.toDefaultMatchView(match, matchId);
        }
    }

    private OngoingMatch getById(UUID matchId) {
        return ongoingMatchService.getById(matchId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format(
                                ExceptionMessages.MATCH_NOT_FOUND,
                                matchId
                        )));
    }
}
