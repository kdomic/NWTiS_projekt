/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.beans.admin;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.foi.nwtis.kdomic.listeners.ApplicationListener;
import org.foi.nwtis.kdomic.server.Email;

/**
 *
 * @author Krunoslav
 */
@ManagedBean
@SessionScoped
public class EmailPostavke implements Serializable  {

    private String emailServer;
    private String email;
    private String password;

    /**
     * Creates a new instance of Index
     */
    public EmailPostavke() {
        this.emailServer = ApplicationListener.context.getInitParameter("emailServer");
        this.email = ApplicationListener.context.getInitParameter("emailAdminEmail");
        this.password = ApplicationListener.context.getInitParameter("emailAdminPassword");
    }

    public String connect() {
        if (emailServer.length() == 0 || email.length() == 0 || password.length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Greška!", "Niste popounili sva polja"));
            return null;
        }
        Email e = new Email();
        if (!e.uspostaviVezu(emailServer, email, password)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška!", "Krivi podaci za prijavu"));
            return null;
        }
        return "connect";
    }

    public String getEmailServer() {
        return emailServer;
    }

    public void setEmailServer(String emailServer) {
        this.emailServer = emailServer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
