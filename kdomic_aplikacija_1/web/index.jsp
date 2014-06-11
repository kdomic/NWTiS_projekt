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
        <script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>

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

            <!-- Main 45.812864, 15.977506 -->
            <div id="main">
                <!-- Email -->
                <article id="email" class="panel">
                    <div id="map" style="width: 800px; height: 600px;"></div>

                    <script type="text/javascript">
                        var locations = ${requestScope.getAllAddress};

                        var map = new google.maps.Map(document.getElementById('map'), {
                            zoom: 7,
                            center: new google.maps.LatLng(45.812864, 15.977506),
                            mapTypeId: google.maps.MapTypeId.ROADMAP
                        });

                        var infowindow = new google.maps.InfoWindow();

                        var marker, i;

                        for (i = 0; i < locations.length; i++) {
                            marker = new google.maps.Marker({
                                position: new google.maps.LatLng(locations[i][1], locations[i][2]),
                                map: map
                            });

                            google.maps.event.addListener(marker, 'click', (function(marker, i) {
                                return function() {
                                    infowindow.setContent(locations[i][0]);
                                    infowindow.open(map, marker);
                                }
                            })(marker, i));
                        }
                    </script>

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
