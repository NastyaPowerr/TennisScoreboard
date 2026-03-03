<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
        <div class="input-container">
            <label>
                <input class="input-filter" placeholder="Filter by name" type="text"/>
            </label>
            <div>
                <a href="#">
                    <button class="btn-filter">Reset Filter</button>
                </a>
            </div>
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
            <a class="prev" href="${pageContext.request.contextPath}/matches?page=${pageNumber - 1}"> < </a>
            <a class="num-page current" href="${pageContext.request.contextPath}/matches?page=${pageNumber}">${pageNumber}</a>
            <a class="num-page" href="${pageContext.request.contextPath}/matches?page=${pageNumber + 1}">${pageNumber + 1}</a>
            <a class="num-page" href="${pageContext.request.contextPath}/matches?page=${pageNumber + 2}">${pageNumber + 2}</a>
            <a class="next" href="${pageContext.request.contextPath}/matches?page=${pageNumber + 1}"> > </a>
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
