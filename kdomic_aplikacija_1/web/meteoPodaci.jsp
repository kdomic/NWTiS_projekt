<%-- 
    Document   : index
    Created on : May 27, 2014, 11:03:44 PM
    Author     : Krunoslav 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.foi.nwtis.kdomic.data.WeatherData,org.foi.nwtis.kdomic.data.Location" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>NWTiS</title>
        <script src="js/jquery.min.js"></script>
        <script src="js/skel.min.js"></script>
        <script src="js/init.js"></script>
        <noscript>
        <link rel="stylesheet" href="css/skel-noscript.css" />
        <link rel="stylesheet" href="css/style.css" />
        <link rel="stylesheet" href="css/style-desktop.css" />
        <link rel="stylesheet" href="css/noscript.css" />
        </noscript>
        <!--[if lte IE 8]><script src="js/html5shiv.js"></script><link rel="stylesheet" href="css/ie8.css" /><![endif]-->
    </head>
    <body class="homepage">

        <!-- Wrapper-->
        <div id="wrapper">

            <!-- Nav -->
            <nav id="nav">
                <a href="/kdomic_aplikacija_1" class="fa fa-home"><span>Unos adrese</span></a>
                <a href="PregledAdresa" class="fa fa-folder"><span>Pregled adresa</span></a>
                <a href="MeteoPodaci" class="fa fa-folder active"><span>Meteorološki podaci</span></a>
                <a href="PregledDnevnika" class="fa fa-folder"><span>Pregled dnevnika</span></a>
            </nav>

            <!-- Main -->
            <div id="main">
                <!-- Email -->
                <article id="email" class="panel">
                    <h2>Pregled meteoroloških podataka</h2>

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
                            </div>
                            <div class="4u">
                                <label for="dateStart">Od: </label>
                                <input type="text" name="dateStart" id="dateStart" value="${requestScope.dateStart}" placeholder="dd.MM.yyyy hh.mm.ss"/>
                                <label for="dateEnd">Do: </label>
                                <input type="taxt" name="dateEnd" id="dateEnd" value="${requestScope.dateEnd}"  placeholder="dd.MM.yyyy hh.mm.ss"/>
                                <br/><input type="checkbox" name="dateCheck" ${requestScope.dateCheck} />
                            </div>
                            <div class="4u">
                                <label for="addressId">Mjesto: ${sessionScope.test}</label>
                                <select name="addressId" id="addressId">
                                    <c:forEach items="${requestScope.getAllAddress}" var="l">
                                        <c:choose>
                                            <c:when test="${l.adresaId eq requestScope.addressId}">
                                                <option selected="selected" value="${l.adresaId}">${l.adress}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${l.adresaId}">${l.adress}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                                <input type="checkbox" name="addressCheck" ${requestScope.addressCheck} />
                            </div>
                        </div>
                        <input type="submit" name="submit" value="Primjeni filter" />
                        <hr/>
                        <table>
                            <tr>
                                <th>dewPoint</th>
                                <th>humidity</th>
                                <th>rainRate</th>
                                <th>temperature</th>
                                <th>windSpeed</th>
                                <th>windDirection</th>
                                <th>feelsLike</th>
                            </tr>
                            <c:forEach items="${requestScope.wd}" var="wd">
                                <tr>
                                    <td>${wd.dewPoint}</td>
                                    <td>${wd.humidity}</td>
                                    <td>${wd.rainRate}</td>
                                    <td>${wd.temperature}</td>
                                    <td>${wd.windSpeed}</td>
                                    <td>${wd.windDirection}</td>
                                    <td>${wd.feelsLike}</td>
                                </tr>
                            </c:forEach>
                        </table>
                        <hr/>
                        <p>
                            <c:forEach var="i" begin="1" end="${requestScope.maxPage}">
                                <c:choose>
                                    <c:when test="${i eq requestScope.currentPage}">
                                        ${i}
                                    </c:when>
                                    <c:otherwise>
                                        <a href="/kdomic_aplikacija_1/MeteoPodaci?perPage=${requestScope.maxPerPage}&page=${i}">${i}</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </p>
                    </form>
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
