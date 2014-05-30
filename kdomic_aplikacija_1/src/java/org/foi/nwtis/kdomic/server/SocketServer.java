/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.kdomic.listeners.ApplicationListener;

/**
 *
 * @author Krunoslav
 */
public class SocketServer {

    private ServerSocket server;

    public SocketServer() {
        try {
            server = new ServerSocket(Integer.parseInt(ApplicationListener.context.getInitParameter("ServerSocketPort")), 1);
            while (true) {
                Socket s = server.accept();
                SocketServerClient ssc = new SocketServerClient(s);
                ssc.start();
            }
        } catch (IOException ex) {
            try {
                server.close();
            } catch (IOException ex1) {
                Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close(){
        try {
            server.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
