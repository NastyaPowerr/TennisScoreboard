package org.roadmap.tennisscoreboard.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
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
import org.roadmap.tennisscoreboard.util.HibernateSessionFactoryUtil;

import java.io.IOException;
import java.util.NoSuchElementException;

@WebFilter("/*")
public class ExceptionAndTransactionFilter implements Filter {
    private SessionFactory sessionFactory;

    @Override
    public void init(FilterConfig filterConfig) {
        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Transaction transaction = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            filterChain.doFilter(servletRequest, servletResponse);
            transaction.commit();
        } catch (InvalidMatchIdException | NoSuchElementException ex) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            req.setAttribute("error", ex.getMessage());
            req.getRequestDispatcher(PagePaths.PAGE_404_JSP).forward(req, resp);
        } catch (ValidationException ex) {
            String path = "WEB-INF" + req.getServletPath() + ".jsp";
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("error", ex.getMessage());
            req.getRequestDispatcher(path).forward(req, resp);
        } catch (PlayerAlreadyExistsException ex) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            req.setAttribute("error", ex.getMessage());
            req.getRequestDispatcher(PagePaths.NEW_MATCH_JSP).forward(req, resp);
        } catch (Exception ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.setAttribute("error", ExceptionMessages.INTERNAL_ERROR);
            req.getRequestDispatcher(PagePaths.PAGE_500_JSP).forward(req, resp);
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
    }
}
