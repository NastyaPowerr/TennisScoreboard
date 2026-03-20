<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="matches" scope="request" type="java.util.List"/>
<jsp:useBean id="pageParams" scope="request" type="org.roadmap.tennisscoreboard.dto.PageParams"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Finished Matches</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</head>

<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="${pageContext.request.contextPath}/images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/matches">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Matches</h1>
        <c:if test="${not empty pageParams.errorMessage()}">
            <div class="error-message">
                    ${pageParams.errorMessage()}
            </div>
        </c:if>
        <div class="input-container">
            <form method="get" action="${pageContext.request.contextPath}/matches" class="form-filter">
                    <input class="input-filter" name="filter_by_player_name" placeholder="Filter by name" type="text"
                           value="${pageParams.filterName()}"/>
                    <button type="submit" class="btn-apply">Apply</button>
            </form>
            <c:if test="${not empty pageParams.filterName()}">
                <div>
                    <a href="${pageContext.request.contextPath}/matches">
                        <button class="btn-filter">Reset Filter</button>
                    </a>
                </div>
            </c:if>
        </div>

        <table class="table-matches">
            <tr>
                <th>Player One</th>
                <th>Player Two</th>
                <th>Winner</th>
            </tr>
            <c:forEach items="${matches}" var="match">
                <tr>
                    <td>${match.firstPlayer().name()}</td>
                    <td>${match.secondPlayer().name()}</td>
                    <td>
                        <span class="winner-name-td">${match.winner().name()}</span>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <div class="pagination">
            <c:set var="filterParameter"
                   value="${not empty pageParams.filterName() ? '&filter_by_player_name=' += pageParams.filterName() : ''}"/>
            <c:set var="prevPage"
                   value="${pageContext.request.contextPath}/matches?page=${pageParams.pageNumber() - 1}"/>
            <c:set var="nextPage"
                   value="${pageContext.request.contextPath}/matches?page=${pageParams.pageNumber() + 1}"/>

            <c:if test="${pageParams.pageNumber() > 1}">
                <a class="prev" href="${prevPage}${filterParameter}"> < </a>
            </c:if>

            <c:if test="${pageParams.pageNumber() - 1 >= 1}">
                <a class="num-page"
                   href="${pageContext.request.contextPath}/matches?page=${pageParams.pageNumber() - 1}${filterParameter}">${pageParams.pageNumber() - 1}</a>
            </c:if>

            <a class="num-page"
               href="${pageContext.request.contextPath}/matches?page=${pageParams.pageNumber()}${filterParameter}">${pageParams.pageNumber()}</a>

            <c:if test="${pageParams.pageNumber() + 1 <= pageParams.pageQuantity()}">
                <a class="num-page"
                   href="${pageContext.request.contextPath}/matches?page=${pageParams.pageNumber() + 1}${filterParameter}">${pageParams.pageNumber() + 1}</a>
            </c:if>

            <c:if test="${pageParams.pageNumber() < pageParams.pageQuantity()}">
                <a class="next" href="${nextPage}${filterParameter}"> > </a>
            </c:if>
        </div>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
            roadmap.</p>
    </div>
</footer>
</body>
</html>
