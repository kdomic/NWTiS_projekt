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
import javax.faces.bean.RequestScoped;
import org.foi.nwtis.kdomic.data.CommunicationMessageAddress;

/**
 *
 * @author Krunoslav
 */
@ManagedBean
@RequestScoped
public class JmsAdrese implements Serializable{
    @EJB
    private MessageQueueBridge messageQueueBridge;

    /**
     * Creates a new instance of JmsAdrese
     */
    public JmsAdrese() {
        
    }
    
    public synchronized List<CommunicationMessageAddress> getList(){
        return messageQueueBridge.getAddress();
    }
    
    public synchronized void delete(CommunicationMessageAddress cma){
        messageQueueBridge.removeAddress(cma);
    }
    
}
