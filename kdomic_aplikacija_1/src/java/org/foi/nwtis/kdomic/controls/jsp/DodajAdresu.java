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
import org.foi.nwtis.kdomic.data.Location;
import org.foi.nwtis.kdomic.database.Database;
import org.foi.nwtis.kdomic.rest.client.GoogleMapsKlijent;

/**
 *
 * @author Krunoslav
 */
public class DodajAdresu extends HttpServlet {

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

        String message;
        if (request.getParameter("address") != null) {
            GoogleMapsKlijent gmk = new GoogleMapsKlijent();
            Location l = gmk.getGeoLocation(replacer(request.getParameter("address")));
            if (l != null) {
                if (Database.insertAddress(l.getAdress(), l.getLatitude(), l.getLongitude()) != null) {
                    message = "Zapis dodan: " + l.getAdress();
                } else {
                    message = "Adresa [" + l.getAdress() + "] već postoji u bazi";
                }
            } else {
                message = "Ne mogu pronaći mjesto: " + request.getParameter("address");
            }
        } else {
            message = "Krivi zahtjev";
        }

        request.setAttribute("message", message);
        request.getRequestDispatcher("dodajAdresu.jsp").forward(request, response);
    }

    private String replacer(String a) {
        a = a.replaceAll("Ä", "č");
        a = a.replaceAll("Ä", "ć");
        a = a.replaceAll("Å¾", "ž");
        a = a.replaceAll("Å¡", "š");
        a = a.replaceAll("Ä", "đ");
        a = a.replaceAll("Ä", "Č");
        a = a.replaceAll("Ä", "Ć");
        a = a.replaceAll("Å½", "Ž");
        a = a.replaceAll("Å ", "Š");
        a = a.replaceAll("Ä", "Đ");
        return a;
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
