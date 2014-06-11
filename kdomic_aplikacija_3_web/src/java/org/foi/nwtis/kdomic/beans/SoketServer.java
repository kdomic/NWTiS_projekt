/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.beans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Krunoslav
 */
@ManagedBean
@RequestScoped
public class SoketServer {

    @EJB
    private MessageQueueBridge messageQueueBridge;

    private String currentSelcted;
    private String currentCommand;
    private String response;

    /**
     * Creates a new instance of SoketServer
     */
    public SoketServer() {

    }

    public void callSelect() {
        response = messageQueueBridge.callServerSocket(currentSelcted);
    }

    public void callCommand() {
        response = messageQueueBridge.callServerSocket(currentCommand);
    }

    public String getCurrentSelcted() {
        return currentSelcted;
    }

    public void setCurrentSelcted(String currentSelcted) {
        this.currentSelcted = currentSelcted;
    }

    public String getCurrentCommand() {
        return currentCommand;
    }

    public void setCurrentCommand(String currentCommand) {
        this.currentCommand = currentCommand;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
