package org.roadmap;

import org.h2.tools.Server;

import java.sql.SQLException;

public class H2DatabaseViewer {
    public static void start() {
        try {
            Server webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
        } catch (SQLException ex) {
            throw new RuntimeException("Couldn't load the DB in browser.");
        }
    }
}
