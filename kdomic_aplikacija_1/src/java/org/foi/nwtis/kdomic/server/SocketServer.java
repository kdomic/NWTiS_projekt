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
 * Klasa za kreiranje SERVER SOCKETA, on zaprima zahtjeve i prosljeđuje ih dretvi
 * @author Krunoslav
 */
public class SocketServer extends Thread {

    private ServerSocket server;
    private Socket s;
    private Boolean acceptSocket;

    @Override
    public synchronized void start() {
        System.out.println("START");
        acceptSocket = true;
        super.start();
    }

    @Override
    public void run() {
        System.out.println("RUN");
        int i = 1;
        try {
            server = new ServerSocket(Integer.parseInt(ApplicationListener.context.getInitParameter("ServerSocketPort")), 1);
            while (acceptSocket) {
                System.out.println("WAITING FOR SOCKET ......["+(i++)+"]");
                s = server.accept();
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

    @Override
    public void interrupt() {
        System.out.println("INTERUPT");
        acceptSocket = false;
        try {
            if (s != null) {
                s.close();
            }
            if (server != null) {
                server.close();

            }
        } catch (IOException ex) {
            Logger.getLogger(SocketServer.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        super.interrupt();
    }

}
