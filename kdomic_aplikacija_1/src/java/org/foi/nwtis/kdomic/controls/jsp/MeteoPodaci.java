/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.controls.jsp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.foi.nwtis.kdomic.data.Location;
import org.foi.nwtis.kdomic.data.WeatherData;
import org.foi.nwtis.kdomic.database.Database;

/**
 *
 * @author Krunoslav
 */
public class MeteoPodaci extends HttpServlet {

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

        String page = request.getParameter("page") == null ? "1" : request.getParameter("page");        

        Boolean dateCheck = request.getParameter("dateCheck") != null;
        Boolean addressCheck = request.getParameter("addressCheck") != null;

        String maxPerPage = request.getParameter("maxPerPage") == null ? request.getParameter("perPage") == null ? "5" : request.getParameter("perPage") : request.getParameter("maxPerPage");
        String dateStart = request.getParameter("dateStart") == null ? "" : request.getParameter("dateStart");
        String dateEnd = request.getParameter("dateEnd") == null ? "" : request.getParameter("dateEnd");
        String addressId = request.getParameter("addressId") == null ? "" : request.getParameter("addressId");
        
        List<WeatherData> wd = Database.getMeteoAllByFilter(page, maxPerPage, dateCheck, dateStart, dateEnd, addressCheck, addressId);
        ArrayList<Location> getAllAddress = Database.getAllAddress();
        Integer maxPage = (Database.countAllMeteoByFilter(page, maxPerPage, dateCheck, dateStart, dateEnd, addressCheck, addressId)+ Integer.parseInt(maxPerPage)-1)/Integer.parseInt(maxPerPage);
        System.out.println("Broj zapisa: " + getAllAddress.size());
        System.out.println("Djelitelj: " + Integer.parseInt(maxPerPage));

        
        request.setAttribute("getAllAddress", getAllAddress);
        request.setAttribute("wd", wd);

        request.setAttribute("maxPage", maxPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("maxPerPage", maxPerPage);

        request.setAttribute("dateCheck", dateCheck ? "checked" : "");
        request.setAttribute("dateStart", dateStart);
        request.setAttribute("dateEnd", dateEnd);

        request.setAttribute("addressCheck", addressCheck ? "checked" : "");
        request.setAttribute("addressId", addressId);

        request.getRequestDispatcher("meteoPodaci.jsp").forward(request, response);
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
