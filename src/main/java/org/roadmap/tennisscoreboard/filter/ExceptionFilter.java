package org.roadmap.tennisscoreboard.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.roadmap.tennisscoreboard.exception.ExceptionMessages;
import org.roadmap.tennisscoreboard.exception.InvalidMatchIdException;
import org.roadmap.tennisscoreboard.exception.PlayerAlreadyExistsException;
import org.roadmap.tennisscoreboard.exception.ValidationException;
import org.roadmap.tennisscoreboard.servlet.PagePaths;

import java.io.IOException;
import java.util.NoSuchElementException;

@Slf4j
@WebFilter("/*")
public class ExceptionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        log.info("ExceptionFilter working....");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (InvalidMatchIdException | NoSuchElementException ex) {
            log.debug("Sending 404 error page: {}", ex.getMessage());
            sendNotFoundError(ex, resp, req);
        } catch (ValidationException ex) {
            log.debug("Sending 400 error info: {}", ex.getMessage());
            sendBadRequestError(ex, req, resp);
        } catch (PlayerAlreadyExistsException ex) {
            log.debug("Sending 409 error info: {}", ex.getMessage());
            sendConflictError(ex, resp, req);
        } catch (Exception ex) {
            log.error("Unexpected error: {}. Sending 500 error page.", ex.getMessage());
            sendInternalError(resp, req);
        }
        log.info("ExceptionFilter finished working....");
    }

    private static void sendInternalError(HttpServletResponse resp, HttpServletRequest req) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        req.setAttribute("error", ExceptionMessages.INTERNAL_ERROR);
        req.getRequestDispatcher(PagePaths.PAGE_500_JSP).forward(req, resp);
    }

    private static void sendConflictError(PlayerAlreadyExistsException ex, HttpServletResponse resp, HttpServletRequest req) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_CONFLICT);
        req.setAttribute("error", ex.getMessage());
        req.getRequestDispatcher(PagePaths.NEW_MATCH_JSP).forward(req, resp);
    }

    private static void sendBadRequestError(ValidationException ex, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = PagePaths.WEB_INF_JSP + req.getServletPath() + ".jsp";
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        req.setAttribute("error", ex.getMessage());
        req.getRequestDispatcher(path).forward(req, resp);
    }

    private static void sendNotFoundError(RuntimeException ex, HttpServletResponse resp, HttpServletRequest req) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        req.setAttribute("error", ex.getMessage());
        req.getRequestDispatcher(PagePaths.PAGE_404_JSP).forward(req, resp);
    }
}
