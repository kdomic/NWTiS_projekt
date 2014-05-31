/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.mdb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.foi.nwtis.kdomic.data.CommunicationMessage;

/**
 *
 * @author Krunoslav
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/NWTiS_kdomic_1")
})
public class EmailQueueReader implements MessageListener {

    public EmailQueueReader() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage om = (ObjectMessage) message;
            CommunicationMessage cm = (CommunicationMessage) om.getObject();
            System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
            System.out.println("startTime: " + cm.getStartTime());
            System.out.println("endTime: " + cm.getEndTime());
            System.out.println("numberOfMessages: " + cm.getNumberOfMessages());
            System.out.println("numberOfNwtisMessage: " + cm.getNumberOfNwtisMessage());
            System.out.println("numberOfOtherMessages: " + cm.getNumberOfOtherMessages());
            System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        } catch (JMSException ex) {
            Logger.getLogger(EmailQueueReader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
