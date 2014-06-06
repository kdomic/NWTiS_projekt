/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.beans.portfelj;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.foi.nwtis.kdomic.eb.Users;
import org.foi.nwtis.kdomic.sb.UsersFacade;

/**
 *
 * @author Krunoslav
 */
@ManagedBean
@RequestScoped
public class Registracija implements Serializable {

    @EJB
    private UsersFacade usersFacade;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String passwordCheck;

    /**
     * Creates a new instance of Registracija
     */
    public Registracija() {

    }

    public String doRegister() {
        if (firstName.length() < 3 || lastName.length() < 3 || username.length() < 3 || password.length() < 3) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Greška!", "Niste popounili sva polja"));
            return null;
        } else if (!password.equals(passwordCheck)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Greška!", "Lozinka i potvrda lozinke se ne poklapaju"));
            return null;
        } else if (usersFacade.findByUsername(username) != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Greška!", "Korisničko ime je već zauzeto"));
            return null;
        }
        Users u = new Users(firstName, lastName, username, password);
        usersFacade.create(u);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pohranjeno!", "Vaše korisničke postavke pohranjene su u bazu"));
        firstName = lastName = username = "";
        return null;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

}
