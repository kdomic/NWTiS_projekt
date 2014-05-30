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
                <a href="MeteoPodaci" class="fa fa-folder"><span>Meteorološki podaci</span></a>
                <a href="PregledDnevnika" class="fa fa-folder active"><span>Pregled dnevnika</span></a>
                <a href="PregledDnevnika" class="fa fa-folder"><span>Pregled dnevnika</span></a>
            </nav>

            <!-- Main -->
            <div id="main">
                <!-- Email -->
                <article id="email" class="panel">
                    <h2>Pregled dnevnika</h2>

                    <table>
                        <tr>
                            <th>id</th>
                            <th>user</th>
                            <th>action</th>
                            <th>duration</th>
                            <th>datetime</th>
                        </tr>
                        <c:forEach items="${requestScope.logs}" var="l">
                            <tr>
                                <td>${l.id}</td>
                                <td>${l.user}</td>
                                <td>${l.action}</td>
                                <td>${l.duration}</td>
                                <td>${l.datetime}</td>
                            </tr>
                        </c:forEach>
                    </table>
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