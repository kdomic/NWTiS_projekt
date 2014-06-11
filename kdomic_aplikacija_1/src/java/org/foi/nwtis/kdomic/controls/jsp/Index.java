/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.controls.jsp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.foi.nwtis.kdomic.data.WeatherDataSmall;
import org.foi.nwtis.kdomic.database.Database;

/**
 *  SERVLET koji se brine o opsluživanju sekcije index.jsp
 * @author Krunoslav
 */
public class Index extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ;

        Boolean first = true;
        String s = "[";
        for (WeatherDataSmall wd : Database.getRecentGeoMeteo("2014-05-01", "2016-01-01")) {
            String text = "<h3>" + wd.getAdress().replaceAll("'", "") + "</h3>";
            text += "<ul>";
            text += "<li>Temperature: " + wd.getTemperature() + "</li>";
            text += "<li>FeelsLike: " + wd.getFeelsLike()+ "</li>";
            text += "<li>Humidity: " + wd.getHumidity() + "</li>";
            text += "<li>RainRate: " + wd.getRainRate() + "</li>";
            text += "<li>WindSpeed: " + wd.getWindSpeed() + "</li>";
            text += "<li>WindDirection: " + wd.getWindDirection() + "</li>";
            text += "<li>DewPoint: " + wd.getDewPoint() + "</li>";
            text += "</ul>";

            if (!first) {
                s += ",";
            }
            s += "['" + text + "'," + wd.getLatitude() + "," + wd.getLongitude() + "," + wd.getAdresaId() + "]";
            first = false;
        }
        s += "]";
        request.setAttribute("getAllAddress", s);
        request.getRequestDispatcher("./index.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
