package org.roadmap.tennisscoreboard.servlet;

public final class PagePaths {
    private PagePaths() {
    }

    public static final String MAIN_PAGE_JSP = "WEB-INF/index.jsp";
    public static final String NEW_MATCH_JSP = "WEB-INF/new-match.jsp";
    public static final String MATCH_SCORE_PAGE_JSP = "WEB-INF/match-score.jsp";
    public static final String MATCH_SCORE_PAGE_FINISHED_JSP = "WEB-INF/match-score-finished.jsp";
    public static final String MATCHES_PAGE_JSP = "WEB-INF/matches.jsp";

    public static final String PAGE_404_JSP = "WEB-INF/error-404.jsp";
    public static final String PAGE_500_JSP = "WEB-INF/error-500.jsp";

    public static final String MAIN_PAGE = "";
    public static final String NEW_MATCH = "/new-match";
    public static final String MATCH_SCORE_PAGE = "/match-score";
    public static final String MATCHES_PAGE = "/matches";
}
