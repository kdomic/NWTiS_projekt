/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.mdb;

import java.io.UnsupportedEncodingException;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.foi.nwtis.kdomic.beans.MessageQueue;
import org.foi.nwtis.kdomic.data.CommunicationMessageAddress;
import org.foi.nwtis.kdomic.server.SocketServerClient;

/**
 *
 * @author Krunoslav
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/NWTiS_kdomic_2_new")
})
public class AddressQueueReader implements MessageListener {

    public AddressQueueReader() {
    }

    @Override
    public void onMessage(Message message) {
        MessageQueue.setNewAddressAdded(true);
        try {
            ObjectMessage om = (ObjectMessage) message;
            CommunicationMessageAddress cm = (CommunicationMessageAddress) om.getObject();
            MessageQueue.addCommunicationMessageAddress(cm);
            System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
            System.out.println("Adresa: " + cm.getAddressName());

            int i = 0;
            while (i < 3 && MessageQueue.getAdminUsername() == null) {
                System.out.println("Waiting for context init ...... ["+(i++)+"]");
                try {
                    sleep(3 * 1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AddressQueueReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String adminUsername = MessageQueue.getAdminUsername();
            String adminPassword = MessageQueue.getAdminPassword();

            SocketServerClient ssc = new SocketServerClient();
            String res = ssc.send("USER " + adminUsername + "; PASSWD " + adminPassword + "; TEST " + cm.getAddressName() + ";");
            if (res != null && res.equals("ERR 51")) {
                ssc.send("USER " + adminUsername + "; PASSWD " + adminPassword + "; ADD " + cm.getAddressName() + ";");
            }

        } catch (JMSException ex) {
            Logger.getLogger(EmailQueueReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AddressQueueReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
