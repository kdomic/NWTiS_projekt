/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.beans.portfelj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.foi.nwtis.kdomic.eb.Users;
import org.foi.nwtis.kdomic.sb.UsersFacade;

/**
 *
 * @author Krunoslav
 */
@ManagedBean
@SessionScoped
public class Prijava implements Serializable {

    @EJB
    private UsersFacade usersFacade;

    private String username = "kdomic";
    private String password = "123456";
    private Boolean isLoggedIn;
    public static Boolean isAdmin;
    public static Users loggdeUser;

    /**
     * Creates a new instance of Prijava
     */
    public Prijava() {
        isLoggedIn = false;
        isAdmin = false;
    }

    public String doLogin() {
        if (username.length() == 0 || password.length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Greška!", "Niste popounili sva polja"));
            return null;
        }

        Users u = usersFacade.findByUsername(username);
        if (u != null) {
            if (u.getPassword().equals(password)) {
                this.isLoggedIn = true;
                this.loggdeUser = u;
                PrijavljeniKorisnici.usersList.add(u);
                if (u.getUsername().equals("admin")) {
                    isAdmin = true;
                    return "isAdmin";
                } else {
                    isAdmin = false;
                }
                return "isUser";
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Greška!", "Neispravni podaci za prijavu"));
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(Boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public Users getLoggdeUser() {
        return loggdeUser;
    }

    public void setLoggdeUser(Users loggdeUser) {
        this.loggdeUser = loggdeUser;
    }

    public static Boolean isAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(Boolean isAdmin) {
        Prijava.isAdmin = isAdmin;
    }

}
