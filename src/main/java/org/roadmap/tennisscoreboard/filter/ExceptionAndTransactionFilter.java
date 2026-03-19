package org.roadmap.tennisscoreboard.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.roadmap.tennisscoreboard.exception.ExceptionMessages;
import org.roadmap.tennisscoreboard.exception.InvalidMatchIdException;
import org.roadmap.tennisscoreboard.exception.PlayerAlreadyExistsException;
import org.roadmap.tennisscoreboard.exception.ValidationException;
import org.roadmap.tennisscoreboard.servlet.PagePaths;

import java.io.IOException;
import java.util.NoSuchElementException;

@WebFilter("/*")
public class ExceptionAndTransactionFilter implements Filter {
    private SessionFactory sessionFactory;

    @Override
    public void init(FilterConfig filterConfig) {
        ServletContext context = filterConfig.getServletContext();
        this.sessionFactory = (SessionFactory) context.getAttribute("sessionFactory");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            filterChain.doFilter(servletRequest, servletResponse);
            transaction.commit();
        } catch (InvalidMatchIdException | NoSuchElementException ex) {
            sendNotFoundError(ex, resp, req);
        } catch (ValidationException ex) {
            sendBadRequestError(ex, req, resp);
        } catch (PlayerAlreadyExistsException ex) {
            sendConflictError(ex, resp, req);
        } catch (Exception ex) {
            sendInternalError(resp, req);
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
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
        String path = "WEB-INF" + req.getServletPath() + ".jsp";
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
