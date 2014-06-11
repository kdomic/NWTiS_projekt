/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.beans.portfelj;

import java.io.Serializable;
import java.util.ResourceBundle;
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
    
    ResourceBundle i18n;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String passwordCheck;

    /**
     * Creates a new instance of Registracija
     */
    public Registracija() {
        FacesContext context = FacesContext.getCurrentInstance();
        i18n = context.getApplication().evaluateExpressionGet(context, "#{i18n}", ResourceBundle.class);
    }

    public String doRegister() {
        if (firstName.length() < 3 || lastName.length() < 3 || username.length() < 3 || password.length() < 3) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, i18n.getString("msg_warning"), i18n.getString("msg_emptyFields")));
            return null;
        } else if (!password.equals(passwordCheck)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, i18n.getString("msg_warning"), i18n.getString("msg_passwordCheck")));
            return null;
        } else if (usersFacade.findByUsername(username) != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, i18n.getString("msg_warning"), i18n.getString("msg_usernameTaken")));
            return null;
        }
        Users u = new Users(firstName, lastName, username, password);
        usersFacade.create(u);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK!", i18n.getString("msg_dataSaved")));
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
