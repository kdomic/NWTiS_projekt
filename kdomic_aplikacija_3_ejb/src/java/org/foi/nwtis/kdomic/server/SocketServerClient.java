/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.kdomic.beans.MessageQueue;
import org.foi.nwtis.kdomic.mdb.AddressQueueReader;

/**
 *
 * @author Krunoslav
 */
public class SocketServerClient {

    Socket s = null;
    String host;
    Integer port;
    Integer numberOfAttempts;
    Integer pauseTime;

    public SocketServerClient() {
        this.host = MessageQueue.getHost();
        this.port = MessageQueue.getPort();
        this.numberOfAttempts = MessageQueue.getNumberOfAttempts();
        this.pauseTime = MessageQueue.getPauseTime();
    }

    public String send(String command) throws UnsupportedEncodingException {
        Integer counter = 0;
        StringBuilder response = null;

        while (counter < numberOfAttempts) {
            try {
                s = new Socket(host, port);
                counter = 0;
                break;
            } catch (IOException e) {
                counter++;
                System.out.println("SEARCHING FOR SERVER....[" + counter + "]");
                try {
                    sleep(pauseTime * 1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AddressQueueReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (counter == numberOfAttempts || s == null) {
            System.out.println("ERROR: Server Not Found");
            return "ERROR: Server Not Found";
        }

        command = URLEncoder.encode(command, "UTF-8");
        try {
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();

            os.write(command.getBytes());
            os.flush();
            s.shutdownOutput();

            response = new StringBuilder();
            while (true) {
                int znak = is.read();
                if (znak == -1) {
                    break;
                }
                response.append((char) znak);
            }
            System.out.println("Request: " + command);
            System.out.println("Response: " + response);
            is.close();
            os.close();
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response.toString();
    }

}
