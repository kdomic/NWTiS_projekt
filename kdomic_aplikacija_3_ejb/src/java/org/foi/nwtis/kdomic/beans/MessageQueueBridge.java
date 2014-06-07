/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import org.foi.nwtis.kdomic.data.CommunicationMessageEmail;
import org.foi.nwtis.kdomic.data.CommunicationMessageAddress;
import org.foi.nwtis.kdomic.server.SocketServerClient;

/**
 *
 * @author Krunoslav
 */
@Stateless
@LocalBean
public class MessageQueueBridge {

    public static List<CommunicationMessageAddress> getAddress() {
        return MessageQueue.getAddress();
    }

    public static void removeAddress(CommunicationMessageAddress cma) {
        MessageQueue.removeCommunicationMessageAddress(cma);
    }

    public static Boolean refreshAddress() {
        Boolean ret = MessageQueue.isNewAddressAdded() == null ? false : MessageQueue.isNewAddressAdded();
        MessageQueue.setNewAddressAdded(false);
        return ret;
    }

    public static List<CommunicationMessageEmail> getMessage() {
        return MessageQueue.getEmail();
    }

    public static void removeMessage(CommunicationMessageEmail cme) {
        MessageQueue.removeCommunicationMessageEmail(cme);
    }

    public static Boolean refreshMessage() {
        Boolean ret = MessageQueue.isNewMessageAdded() == null ? false : MessageQueue.isNewMessageAdded();
        MessageQueue.setNewMessageAdded(false);
        return ret;
    }

    public void init(String adminUsername, String adminPassword, String host, Integer port, Integer numberOfAttempts, Integer pauseTime, String fileNameAddress, String fileNameEmail) {
        MessageQueue.setAdminUsername(adminUsername);
        MessageQueue.setAdminPassword(adminPassword);
        MessageQueue.setHost(host);
        MessageQueue.setPort(port);
        MessageQueue.setNumberOfAttempts(numberOfAttempts);
        MessageQueue.setPauseTime(pauseTime);
        MessageQueue.setFileNameAddress(fileNameAddress);
        MessageQueue.setFileNameEmail(fileNameEmail);
        loadSerilization();
    }

    public void loadSerilization() {
        System.out.println("LOAD SERIALIZED DATA IN PROGRESS...");
        File f = new File(MessageQueue.getFileNameAddress());
        if (!f.exists()) {
            System.out.println("---no data found---");
            System.out.println("LOAD SERIALIZED DATA END");
            return;
        }
        try {
            try (FileInputStream in = new FileInputStream(MessageQueue.getFileNameAddress())) {
                try (ObjectInputStream s = new ObjectInputStream(in)) {
                    MessageQueue.setAddress((ArrayList<CommunicationMessageAddress>) s.readObject());
                    s.close();
                }
                in.close();
            }
            try (FileInputStream in = new FileInputStream(MessageQueue.getFileNameEmail())) {
                try (ObjectInputStream s = new ObjectInputStream(in)) {
                    MessageQueue.setEmail((ArrayList<CommunicationMessageEmail>) s.readObject());
                    s.close();
                }
                in.close();
            }
        } catch (Exception e) {
            Logger.getLogger(MessageQueueBridge.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("LOAD SERIALIZED DATA END");
    }

    public void runSerialization() {
        if (MessageQueue.getFileNameAddress() == null) {
            return;
        }
        System.out.println("DATA SERIALIZATION IN PROGRESS...");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(MessageQueue.getFileNameAddress());
            try (ObjectOutputStream s = new ObjectOutputStream(out)) {
                s.writeObject(MessageQueue.getAddress());
                s.close();
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(MessageQueueBridge.class.getName()).log(Level.SEVERE, null, ex);
            }
            out = new FileOutputStream(MessageQueue.getFileNameEmail());
            try (ObjectOutputStream s = new ObjectOutputStream(out)) {
                s.writeObject(MessageQueue.getEmail());
                s.close();
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(MessageQueueBridge.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MessageQueueBridge.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(MessageQueueBridge.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("DATA SERIALIZATION IN END");
    }

    public String getCurrentMeteo(String adress) {
        String adminUsername = MessageQueue.getAdminUsername();
        String adminPassword = MessageQueue.getAdminPassword();
        SocketServerClient ssc = new SocketServerClient();
        return ssc.send("USER " + adminUsername + "; PASSWD " + adminPassword + "; GET " + adress + ";");
    }

}
