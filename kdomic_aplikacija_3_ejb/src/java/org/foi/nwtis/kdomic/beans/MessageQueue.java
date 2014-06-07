/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.beans;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.foi.nwtis.kdomic.data.CommunicationMessageEmail;
import org.foi.nwtis.kdomic.data.CommunicationMessageAddress;

/**
 *
 * @author Krunoslav
 */
@ApplicationScoped
public class MessageQueue {

    static List<CommunicationMessageEmail> email;
    static List<CommunicationMessageAddress> address;

    private static String adminUsername;
    private static String adminPassword;
    private static String host;
    private static Integer port;
    private static Integer numberOfAttempts;
    private static Integer pauseTime;
    private static String  fileNameAddress;
    private static String  fileNameEmail;

    public static void addCommunicationMessageEmail(CommunicationMessageEmail temp) {
        if (MessageQueue.email == null) {
            MessageQueue.email = new ArrayList<>();
        }
        MessageQueue.email.add(temp);
    }

    public static void addCommunicationMessageAddress(CommunicationMessageAddress temp) {
        if (MessageQueue.address == null) {
            MessageQueue.address = new ArrayList<>();
        }
        MessageQueue.address.add(temp);
    }

    public static List<CommunicationMessageEmail> getEmail() {
        return email;
    }

    public static void setEmail(List<CommunicationMessageEmail> email) {
        MessageQueue.email = email;
    }

    public static List<CommunicationMessageAddress> getAddress() {
        return address;
    }

    public static void setAddress(List<CommunicationMessageAddress> address) {
        MessageQueue.address = address;
    }

    public static String getAdminUsername() {
        return adminUsername;
    }

    public static void setAdminUsername(String aAdminUsername) {
        adminUsername = aAdminUsername;
    }

    public static String getAdminPassword() {
        return adminPassword;
    }

    public static void setAdminPassword(String aAdminPassword) {
        adminPassword = aAdminPassword;
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String aHost) {
        host = aHost;
    }

    public static Integer getPort() {
        return port;
    }

    public static void setPort(Integer aPort) {
        port = aPort;
    }

    public static Integer getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public static void setNumberOfAttempts(Integer aNumberOfAttempts) {
        numberOfAttempts = aNumberOfAttempts;
    }

    public static Integer getPauseTime() {
        return pauseTime;
    }

    public static void setPauseTime(Integer aPauseTime) {
        pauseTime = aPauseTime;
    }

    public static String getFileNameAddress() {
        return fileNameAddress;
    }

    public static void setFileNameAddress(String aFileNameAddress) {
        fileNameAddress = aFileNameAddress;
    }

    public static String getFileNameEmail() {
        return fileNameEmail;
    }

    public static void setFileNameEmail(String aFileNameEmail) {
        fileNameEmail = aFileNameEmail;
    }
    
    

}
