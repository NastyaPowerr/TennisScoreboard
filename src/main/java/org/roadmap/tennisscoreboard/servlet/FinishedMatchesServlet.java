package org.roadmap.tennisscoreboard.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.roadmap.tennisscoreboard.dto.FinishedMatchDto;
import org.roadmap.tennisscoreboard.exception.ExceptionMessages;
import org.roadmap.tennisscoreboard.exception.PageValidationException;
import org.roadmap.tennisscoreboard.service.FinishedMatchesPersistenceService;
import org.roadmap.tennisscoreboard.util.MatchValidator;

import java.io.IOException;
import java.util.List;

@WebServlet(PagePaths.MATCHES_PAGE)
public class FinishedMatchesServlet extends HttpServlet {
    private FinishedMatchesPersistenceService finishedMatchesService;
    private static final int PAGE_SIZE = 5;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        this.finishedMatchesService = (FinishedMatchesPersistenceService) context.getAttribute("finishedMatchesPersistenceService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String pageNumberString = req.getParameter("page");
        int pageNumber;
        String errorMessage = "";
        try {
            MatchValidator.validatePage(pageNumberString);
            pageNumber = Integer.parseInt(pageNumberString);
        } catch (PageValidationException ex) {
            pageNumber = 1;
            if (pageNumberString != null && !pageNumberString.trim().isEmpty()) {
                errorMessage = ExceptionMessages.INVALID_PAGE_SHOW_FIRST_PAGE;
            }
        }
        String filterName = req.getParameter("filter_by_player_name");
        int pageQuantity = finishedMatchesService.getTotalPages(PAGE_SIZE, filterName);

        if (pageQuantity != 0 && pageNumber > pageQuantity) {
            pageNumber = pageQuantity;
            errorMessage = ExceptionMessages.PAGE_NOT_EXIST_SHOW_LAST_PAGE;
        }

        List<FinishedMatchDto> matches = finishedMatchesService.getMatches(pageNumber, PAGE_SIZE, filterName);
        req.setAttribute("matches", matches);
        req.setAttribute("pageNumber", pageNumber);
        req.setAttribute("pageQuantity", pageQuantity);
        req.setAttribute("filterName", filterName);
        req.setAttribute("error", errorMessage);

        req.getRequestDispatcher(PagePaths.MATCHES_PAGE_JSP).forward(req, resp);
    }
}
