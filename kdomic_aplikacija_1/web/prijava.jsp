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
        <title>NWTiS Prijava</title>
    </head>
    <body class="homepage">
        <h1>Prijava u sustav</h1> <br/>
        <form action="j_security_check" method=post>
            <input type="text" class="text" name="j_username" size="25" placeholder="Korisničko ime">                                    
            <input type="password" class="text"  size="15" name="j_password" placeholder="Lozinka">
            <input type="submit" value="Submit" class="button">
        </form>
    </body>
</html>
