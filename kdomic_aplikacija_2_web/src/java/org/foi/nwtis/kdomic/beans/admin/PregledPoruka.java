/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.beans.admin;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import org.foi.nwtis.kdomic.data.Poruka;
import org.foi.nwtis.kdomic.listeners.ApplicationListener;
import org.foi.nwtis.kdomic.server.Email;

/**
 *
 * @author Krunoslav
 */
@ManagedBean
@SessionScoped
public class PregledPoruka extends Email {

    List<Poruka> messages;

    private final String emailServer;
    private final String email;
    private final String password;

    private Map<String, String> folders;
    private String selectedFolder;
    private Poruka selectedMessage;

    /**
     * Creates a new instance of PregledPoruka
     */
    public PregledPoruka() {
        EmailPostavke ep = (EmailPostavke) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("emailPostavke");
        this.emailServer = ep.getEmailServer();
        this.email = ep.getEmail();
        this.password = ep.getPassword();
        try {
            uspostaviVezu(emailServer, email, password);
            folders = preuzmiMape();
            selectedFolder = ApplicationListener.context.getInitParameter("emailInboxDir");
            messages = preuzmiPoruke(selectedFolder);
        } catch (MessagingException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showMessages() {
        try {
            messages = preuzmiPoruke(selectedFolder);
        } catch (MessagingException ex) {
            Logger.getLogger(PregledPoruka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteMessage(Poruka m) {
        if (m != null) {
            System.out.println(m.getId());
            messages.remove(m);
            deleteMessage(m.getRaw(), selectedFolder);
        }
    }

    public List<Poruka> getMessages() {
        return messages;
    }

    public void setMessages(List<Poruka> messages) {
        this.messages = messages;
    }

    public Map<String, String> getFolders() {
        return folders;
    }

    public void setFolders(Map<String, String> folders) {
        this.folders = folders;
    }

    public String getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(String selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    public Poruka getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(Poruka selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

}
