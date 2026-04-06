package org.roadmap.tennisscoreboard.util;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.function.Supplier;

@Slf4j
public final class TransactionController {
    private TransactionController() {
    }

    public static void execute(SessionFactory sessionFactory, Runnable runnable) {
        log.debug("TransactionUtil working...");

        Session session = sessionFactory.getCurrentSession();
        log.debug("Session hash: {}", System.identityHashCode(session));
        log.debug("Session is open: {}", session.isOpen());

        log.debug("Start transaction.");
        session.beginTransaction();
        try {
            runnable.run();
            session.getTransaction().commit();

            log.debug("Session hash: {}", System.identityHashCode(session));
            log.debug("Session is open: {}", session.isOpen());

            log.debug("Transaction commited.");
        } catch (Exception ex) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
                log.debug("Transaction rolled back");
            }
            log.debug("Session hash: {}", System.identityHashCode(session));
            log.debug("Session is open: {}", session.isOpen());
            log.error("Caught error, transaction failed, throwing in further...");
            throw ex;
        }
    }

    public static <T> T execute(SessionFactory sessionFactory, Supplier<T> supplier) {
        log.debug("TransactionUtil working...");

        Session session = sessionFactory.getCurrentSession();
        log.debug("Session hash: {}", System.identityHashCode(session));
        log.debug("Session is open: {}", session.isOpen());

        log.debug("Start transaction.");
        session.beginTransaction();
        try {
            T result = supplier.get();
            session.getTransaction().commit();
            log.debug("Transaction commited.");

            log.debug("Session hash: {}", System.identityHashCode(session));
            log.debug("Session is open: {}", session.isOpen());

            return result;
        } catch (Exception ex) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
                log.debug("Transaction rolled back");
            }
            log.debug("Session hash: {}", System.identityHashCode(session));
            log.debug("Session is open: {}", session.isOpen());
            log.error("Caught error, transaction failed, throwing in further...");
            throw ex;
        }
    }
}
