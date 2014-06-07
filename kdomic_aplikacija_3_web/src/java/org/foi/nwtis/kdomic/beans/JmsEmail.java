/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.kdomic.beans;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.foi.nwtis.kdomic.data.CommunicationMessageEmail;

/**
 *
 * @author Krunoslav
 */
@ManagedBean
@ViewScoped
public class JmsEmail implements Serializable {
    
    @EJB
    private MessageQueueBridge messageQueueBridge;

    /**
     * Creates a new instance of JmsEmail
     */
    public JmsEmail() {
        
    }
    
    public synchronized List<CommunicationMessageEmail> getList(){
        return messageQueueBridge.getMessage();
    }
    
    public synchronized void delete(CommunicationMessageEmail cme){
        messageQueueBridge.removeMessage(cme);
    }
    
}
