package org.foi.nwtis.kdomic.filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.foi.nwtis.kdomic.beans.portfelj.Prijava;
import org.foi.nwtis.kdomic.beans.portfelj.PrijavljeniKorisnici;
import org.foi.nwtis.kdomic.sb.LogsFacade;

@WebFilter(filterName = "Filter", urlPatterns = {"/portfelj/*"})
public class Filter implements javax.servlet.Filter {

    LogsFacade logsFacade = lookupLogsFacadeBean();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        Prijava session = (Prijava) req.getSession().getAttribute("prijava");
        String url = req.getRequestURI();
        if (session == null || !session.isLoggedIn()) {
            if (url.indexOf("prijava.xhtml") >= 0 || url.indexOf("registracija.xhtml") >= 0) {
                logsFacade.addNewLog("Neprijavljeni korisnik pristupa: [" + url + "]");
                chain.doFilter(request, response);
            } else {
                logsFacade.addNewLog("Ilegalni pristup neprijavljenog korisnika: [" + url + "]");
                res.sendRedirect(req.getServletContext().getContextPath() + "/portfelj/prijava.xhtml");
            }
        } else {
            if (url.indexOf("prijava.xhtml") >= 0 || url.indexOf("registracija.xhtml") >= 0) {
                logsFacade.addNewLog("Ilegalni pristup prijavljenog korisnika: [" + url + "]", session.getLoggdeUser());
                res.sendRedirect(req.getServletContext().getContextPath() + "/portfelj/index.xhtml");
            } else if (url.indexOf("odjava.xhtml") >= 0) {
                PrijavljeniKorisnici.usersList.remove(session.getLoggdeUser());
                logsFacade.addNewLog("Odjava korisnika: [" + session.getUsername() + "]", session.getLoggdeUser());
                req.getSession().removeAttribute("prijava");
                res.sendRedirect(req.getServletContext().getContextPath() + "/portfelj/prijava.xhtml");
            } else {
                logsFacade.addNewLog("Pristup stranici: [" + url + "]", session.getLoggdeUser());
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {
    }

    private LogsFacade lookupLogsFacadeBean() {
        try {
            Context c = new InitialContext();
            return (LogsFacade) c.lookup("java:global/kdomic_aplikacija_2/kdomic_aplikacija_2_ejb/LogsFacade!org.foi.nwtis.kdomic.sb.LogsFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
