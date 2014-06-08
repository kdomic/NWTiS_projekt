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
import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
import org.foi.nwtis.kdomic.data.Logs;
import org.foi.nwtis.kdomic.data.Users;
import org.foi.nwtis.kdomic.database.Database;

/**
 *
 * @author Krunoslav
 */
public class PregledDnevnika extends HttpServlet {

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
        Boolean userCheck = request.getParameter("userCheck") != null;

        String maxPerPage = request.getParameter("maxPerPage") == null ? request.getParameter("perPage") == null ? "5" : request.getParameter("perPage") : request.getParameter("maxPerPage");
        String dateStart = request.getParameter("dateStart") == null ? "" : request.getParameter("dateStart");
        String dateEnd = request.getParameter("dateEnd") == null ? "" : request.getParameter("dateEnd");
        String userId = request.getParameter("userId") == null ? "" : request.getParameter("userId");
        
        Integer maxPage = maxPerPage.equals("svi") ? 0 : (Database.countAllLogsByFilter(page, maxPerPage, dateCheck, dateStart, dateEnd, userCheck, userId)+ Integer.parseInt(maxPerPage)-1)/Integer.parseInt(maxPerPage);
        request.setAttribute("maxPage", maxPage);
        
        if(Integer.parseInt(page)>maxPage){
            page = "1";
        }
        
        request.setAttribute("currentPage", page);
        request.setAttribute("maxPerPage", maxPerPage);

        request.setAttribute("dateCheck", dateCheck ? "checked" : "");
        request.setAttribute("dateStart", dateStart);
        request.setAttribute("dateEnd", dateEnd);

        request.setAttribute("userCheck", userCheck ? "checked" : "");
        request.setAttribute("userId", userId);

        List<Users> users = Database.getUsers();
        request.setAttribute("getUsers", users);

        List<Logs> l = Database.getAllLogsByFilter(page, maxPerPage, dateCheck, dateStart, dateEnd, userCheck, userId);
        request.setAttribute("logs", l);
        
        
        
        
        
        request.getRequestDispatcher("./pregledDnevnika.jsp").forward(request, response);
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
