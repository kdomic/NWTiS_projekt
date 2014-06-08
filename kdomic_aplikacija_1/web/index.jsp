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
                <a href="./privatno/dodajAdresu.jsp" class="fa fa-folder"><span>Unos adrese</span></a>
                <a href="./privatno/PregledAdresa" class="fa fa-folder"><span>Pregled adresa</span></a>
                <a href="./privatno/MeteoPodaci" class="fa fa-folder"><span>Meteorološki podaci</span></a>
                <a href="./privatno/PregledDnevnika" class="fa fa-folder"><span>Pregled dnevnika</span></a>
            </nav>

            <!-- Main -->
            <div id="main">
                <!-- Email -->
                <article id="email" class="panel">
                    <ul>
                        <li><a href="./privatno/dodajAdresu.jsp">Dodaj adresu</a></li>
                        <li><a href="./privatno/PregledAdresa">PregledAdresa</a></li>
                        <li><a href="./privatno/MeteoPodaci">MeteoPodaci</a></li>
                        <li><a href="./privatno/PregledDnevnika">PregledDnevnika</a></li>
                    </ul>
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
