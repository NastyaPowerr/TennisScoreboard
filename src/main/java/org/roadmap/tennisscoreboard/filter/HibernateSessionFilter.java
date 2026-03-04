package org.roadmap.tennisscoreboard.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.roadmap.tennisscoreboard.util.HibernateSessionFactoryUtil;

@WebFilter("/*")
public class HibernateSessionFilter implements Filter {
    private SessionFactory sessionFactory;

    @Override
    public void init(FilterConfig filterConfig) {
        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            filterChain.doFilter(servletRequest, servletResponse);

            sessionFactory.getCurrentSession().getTransaction().commit();
        } catch (Exception ex) {
            Session session = sessionFactory.getCurrentSession();
            if (session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException("Exception in HibernateSessionFilter", ex);
        }
    }
}
