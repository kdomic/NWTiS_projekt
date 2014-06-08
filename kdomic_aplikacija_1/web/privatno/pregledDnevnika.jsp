<%-- 
    Document   : index
    Created on : May 27, 2014, 11:03:44 PM
    Author     : Krunoslav 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.foi.nwtis.kdomic.data.Logs" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>NWTiS</title>
        <script src="./js/jquery.min.js"></script>
        <script src="./js/skel.min.js"></script>
        <script src="./js/init.js"></script>
        <link rel="stylesheet" href="./css/tablica.css" />
        <noscript>
        <link rel="stylesheet" href="./css/skel-noscript.css" />
        <link rel="stylesheet" href="./css/style.css" />
        <link rel="stylesheet" href="./css/style-desktop.css" />
        <link rel="stylesheet" href="./css/noscript.css" />
        </noscript>
        <!--[if lte IE 8]><script src="js/html5shiv.js"></script><link rel="stylesheet" href="css/ie8.css" /><![endif]-->
    </head>
    <body class="homepage">

        <!-- Wrapper-->
        <div id="wrapper">

            <nav id="nav">
                <a href="./dodajAdresu.jsp" class="fa fa-folder"><span>Unos adrese</span></a>
                <a href="./PregledAdresa" class="fa fa-folder"><span>Pregled adresa</span></a>
                <a href="./MeteoPodaci" class="fa fa-folder"><span>Meteorološki podaci</span></a>
                <a href="./PregledDnevnika" class="fa fa-folder-open-o active"><span>Pregled dnevnika</span></a>
            </nav>

            <!-- Main -->
            <div id="main">
                <!-- Email -->
                <article id="email" class="panel">
                    <h2>Pregled dnevnika</h2>

                    <form action="" method="post">
                        <div class="row">
                            <div class="4u">
                                <label for="maxPerPage">Zapisa po stranici: </label>
                                <select name="maxPerPage" id="maxPerPage">
                                    <c:forTokens items="5,10,20,50,100,svi" delims="," var="num">
                                        <c:choose>
                                            <c:when test="${num eq requestScope.maxPerPage}">
                                                <option value="${num}" selected="selected">${num}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${num}">${num}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forTokens>
                                </select>

                                <label for="maxPerPage">Prikaži stranicu: </label>
                                <select id="page" name="page">
                                    <c:forEach var="i" begin="1" end="${requestScope.maxPage}">
                                        <c:choose>
                                            <c:when test="${i eq requestScope.currentPage}">
                                                <option selected="selected" value="${i}">${i}</option>

                                            </c:when>
                                            <c:otherwise>
                                                <option value="${i}">${i}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>

                            </div>
                            <div class="4u">
                                <label for="dateStart">Od: </label>
                                <input type="text" name="dateStart" id="dateStart" value="${requestScope.dateStart}" placeholder="dd.MM.yyyy hh.mm.ss"/>
                                <label for="dateEnd">Do: </label>
                                <input type="taxt" name="dateEnd" id="dateEnd" value="${requestScope.dateEnd}"  placeholder="dd.MM.yyyy hh.mm.ss"/>
                                <br/><input type="checkbox" name="dateCheck" ${requestScope.dateCheck} />
                            </div>
                            <div class="4u">
                                <label for="addressId">Korisnici:</label>
                                <select name="userId" id="userId">
                                    <c:forEach items="${requestScope.getUsers}" var="u">
                                        <c:choose>
                                            <c:when test="${u.id eq requestScope.userId}">
                                                <option selected="selected" value="${u.id}">${u.username}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${u.id}">${u.username}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                                <input type="checkbox" name="userCheck" ${requestScope.userCheck} />
                            </div>
                        </div>
                        <input type="submit" name="submit" value="Primjeni filter" />
                    </form>
                    <hr/>
                    <table>
                        <thead>
                            <tr>
                                <th>id</th>
                                <th>user</th>
                                <th>action</th>
                                <th>duration</th>
                                <th>datetime</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.logs}" var="l">
                                <tr>
                                    <td>${l.id}</td>
                                    <td>${l.user}</td>
                                    <td>${l.action}</td>
                                    <td>${l.duration}</td>
                                    <td>${l.datetime}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <hr/>
                </article>
            </div>

            <!-- Footer -->
            <div id="footer">
                <ul class="links">
                    <li>Krunoslav Domić &copy; NWTiS</li>
                </ul>
            </div>

        </div>
    </body>
</html>
