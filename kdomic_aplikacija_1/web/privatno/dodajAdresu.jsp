<%-- 
    Document   : index
    Created on : May 27, 2014, 11:03:44 PM
    Author     : Krunoslav 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>NWTiS</title>
        <script src="./js/jquery.min.js"></script>
        <script src="./js/skel.min.js"></script>
        <script src="./js/init.js"></script>
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

            <!-- Nav -->
            <nav id="nav">
                <a href="./dodajAdresu.jsp" class="fa fa-folder-open-o active"><span>Unos adrese</span></a>
                <a href="./PregledAdresa" class="fa fa-folder"><span>Pregled adresa</span></a>
                <a href="./MeteoPodaci" class="fa fa-folder"><span>Meteorološki podaci</span></a>
                <a href="./PregledDnevnika" class="fa fa-folder"><span>Pregled dnevnika</span></a>
            </nav>

            <!-- Main -->
            <div id="main">
                <!-- Email -->
                <article id="email" class="panel">
                    <c:choose>
                        <c:when test="${not empty requestScope.message}">
                            <p>${requestScope.message}</p>
                        </c:when>
                    </c:choose>
                    <header>
                        <h2>Dodaj adresu</h2>
                    </header>
                    <form action="./DodajAdresu" method="post">
                        <div>
                            <div class="row half">
                                <div class="12u">
                                    <input type="text" class="text" name="address" placeholder="Upišite adresu" />
                                </div>
                            </div>                            
                            <div class="row">
                                <div class="12u">
                                    <input type="submit" class="button" value="Dodaj" />
                                </div>
                            </div>
                        </div>
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
