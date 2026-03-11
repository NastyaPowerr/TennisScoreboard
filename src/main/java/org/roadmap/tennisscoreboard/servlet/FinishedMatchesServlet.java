package org.roadmap.tennisscoreboard.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.roadmap.tennisscoreboard.dto.FinishedMatchDto;
import org.roadmap.tennisscoreboard.service.FinishedMatchesPersistenceService;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class FinishedMatchesServlet extends HttpServlet {
    private FinishedMatchesPersistenceService finishedMatchesService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        this.finishedMatchesService = (FinishedMatchesPersistenceService) context.getAttribute("finishedMatchesPersistenceService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String pageNumberString = req.getParameter("page");
        int pageNumber = pageNumberString == null || pageNumberString.isEmpty() ? 1 : Integer.parseInt(pageNumberString);
        int pageSize = 2;

        String filterName = req.getParameter("filter_by_player_name");

        List<FinishedMatchDto> matches = finishedMatchesService.getMatches(pageNumber, pageSize, filterName);
        int pageQuantity = finishedMatchesService.getTotalPages(pageSize, filterName);

        req.setAttribute("matches", matches);
        req.setAttribute("pageNumber", pageNumber);
        req.setAttribute("pageQuantity", pageQuantity);
        req.setAttribute("filterName", filterName);

        req.getRequestDispatcher("WEB-INF/matches.jsp").forward(req, resp);
    }
}
