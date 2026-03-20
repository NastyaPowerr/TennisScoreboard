package org.roadmap.tennisscoreboard.servlet;

public final class PagePaths {
    private PagePaths() {
    }

    public static final String WEB_INF_JSP = "WEB-INF/jsp";
    public static final String MAIN_PAGE_JSP = WEB_INF_JSP + "/index.jsp";
    public static final String NEW_MATCH_JSP = WEB_INF_JSP + "/new-match.jsp";
    public static final String MATCH_SCORE_PAGE_JSP = WEB_INF_JSP + "/match-score.jsp";
    public static final String MATCH_SCORE_PAGE_FINISHED_JSP = WEB_INF_JSP + "/match-score-finished.jsp";
    public static final String MATCHES_PAGE_JSP = WEB_INF_JSP + "/matches.jsp";

    public static final String PAGE_404_JSP = WEB_INF_JSP + "/error-404.jsp";
    public static final String PAGE_500_JSP = WEB_INF_JSP + "/error-500.jsp";

    public static final String MAIN_PAGE = "";
    public static final String NEW_MATCH = "/new-match";
    public static final String MATCH_SCORE_PAGE = "/match-score";
    public static final String MATCHES_PAGE = "/matches";
}
