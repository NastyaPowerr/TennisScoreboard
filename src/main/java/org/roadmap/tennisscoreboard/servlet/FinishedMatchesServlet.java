package org.roadmap.tennisscoreboard.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.roadmap.tennisscoreboard.dto.FinishedMatchDto;
import org.roadmap.tennisscoreboard.service.MatchService;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class FinishedMatchesServlet extends HttpServlet {
    private MatchService matchService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        this.matchService = (MatchService) context.getAttribute("matchService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String pageNumberString = req.getParameter("page");
        int pageNumber = pageNumberString == null || pageNumberString.isEmpty() ? 1 : Integer.parseInt(pageNumberString);
        int pageSize = 10;

        List<FinishedMatchDto> matches = matchService.getMatches(pageNumber, pageSize);
        req.setAttribute("matches", matches);
        req.setAttribute("pageNumber", pageNumber);

        req.getRequestDispatcher("WEB-INF/matches.jsp").forward(req, resp);
    }
}
