/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.kdomic.data.Location;
import org.foi.nwtis.kdomic.data.WeatherData;
import org.foi.nwtis.kdomic.database.Database;
import org.foi.nwtis.kdomic.listeners.ApplicationListener;
import org.foi.nwtis.kdomic.rest.client.GoogleMapsKlijent;

/**
 *
 * @author Krunoslav
 */
public class SocketServerClient extends Thread {

    private final Socket s;

    SocketServerClient(Socket s) {
        System.out.println("SOCKET: " + new Date());
        this.s = s;
    }

    @Override
    public void interrupt() {
        super.interrupt();
        try {
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        ApplicationListener.reciecedCommandNum++;
        boolean sendEmail = false;
        String userId = null;
        StringBuilder command = new StringBuilder();
        long threadStart = System.nanoTime();

        try (InputStream is = s.getInputStream()) {
            String response = "";
            OutputStream os = s.getOutputStream();
            while (true) {
                int token = is.read();
                if (token == -1) {
                    break;
                }
                command.append((char) token);
            }

            Matcher m = Pattern.compile("^USER ([^\\s]+); GET ([\\d\\D\\s\\S\\w\\W]*);$").matcher(command);
            if (m.matches()) { 
                response = get(m.group(2));
            } else {
                m = Pattern.compile("^USER ([^\\s]+); PASSWD ([^\\s]+); ([^\\s]+)[\\d\\D\\s\\S\\w\\W]*").matcher(command);
                if (m.matches()) {
                    userId = Database.authenticateUser(m.group(1), m.group(2));
                    if (userId != null) {
                        sendEmail = true;
                        switch (m.group(3)) {
                            case "PAUSE;":
                                if (ApplicationListener.paused) {
                                    response = "ERR 40";
                                    ApplicationListener.errorCommandNum++;
                                } else {
                                    ApplicationListener.paused = true;
                                    response = "OK 10";
                                    ApplicationListener.execCommandNum++;
                                }
                                break;
                            case "START;":
                                if (ApplicationListener.paused) {
                                    response = "OK 10";
                                    ApplicationListener.paused = false;
                                    ApplicationListener.execCommandNum++;
                                } else {
                                    response = "ERR 41";
                                    ApplicationListener.errorCommandNum++;
                                }
                                break;
                            case "STOP;":
                                if (ApplicationListener.stoped) {
                                    response = "ERR 42";
                                    ApplicationListener.errorCommandNum++;
                                } else {
                                    ApplicationListener.stoped = true;
                                    response = "OK 10";
                                    ApplicationListener.execCommandNum++;
                                }
                                break;
                            case "ADD":
                                response = "ADD";
                                m = Pattern.compile("^USER ([^\\s]+); PASSWD ([^\\s]+); ADD ([^\\s]+); NEWPASSWD ([^\\s]+);").matcher(command);
                                if (m.matches()) {
                                    Database.insertUser(m.group(3), m.group(4));
                                    response = "OK 10";
                                    ApplicationListener.execCommandNum++;
                                } else {
                                    m = Pattern.compile("^USER ([^\\s]+); PASSWD ([^\\s]+); ADD ([\\d\\D\\s\\S\\w\\W]*);").matcher(command);
                                    if (m.matches()) {
                                        GoogleMapsKlijent gmk = new GoogleMapsKlijent();
                                        Location l = gmk.getGeoLocation(m.group(3));
                                        if (Database.getAdresaId(l.getAdress()) != null) {
                                            response = "ERR 51";
                                            ApplicationListener.errorCommandNum++;
                                        } else {
                                            Database.insertAddress(l.getAdress(), l.getLatitude(), l.getLongitude(), userId);
                                            response = "OK 10";
                                            ApplicationListener.execCommandNum++;
                                        }
                                    } else {
                                        response = "ERR 99";
                                        ApplicationListener.errorCommandNum++;
                                    }
                                }
                                break;
                            case "TEST":
                                m = Pattern.compile("^USER ([^\\s]+); PASSWD ([^\\s]+); TEST ([\\d\\D\\s\\S\\w\\W]*);").matcher(command);
                                if (m.matches()) {
                                    if (Database.getAdresaId(m.group(3)) != null) {
                                        response = "OK 10";
                                        ApplicationListener.execCommandNum++;
                                    } else {
                                        response = "ERR 51";
                                        ApplicationListener.errorCommandNum++;
                                    }
                                } else {
                                    response = "ERR 99";
                                    ApplicationListener.errorCommandNum++;
                                }
                                break;
                            case "GET":
                                m = Pattern.compile("^USER ([^\\s]+); PASSWD ([^\\s]+); GET ([\\d\\D\\s\\S\\w\\W]*);").matcher(command);
                                if (m.matches()) {
                                    response = get(m.group(3));
                                } else {
                                    response = "ERR 99";
                                    ApplicationListener.errorCommandNum++;
                                }
                                break;
                            default:
                                response = "ERR 00";
                                ApplicationListener.errorCommandNum++;
                        }
                    } else {
                        response = "ERR 30";
                        ApplicationListener.errorCommandNum++;
                    }
                } else {
                    response = "ERR 01";
                    ApplicationListener.errorCommandNum++;
                }
            }
            System.out.println("Request: " + command);
            System.out.println("Response: " + response);
            os.write(response.getBytes());
            os.close();
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        long threadEnd = System.nanoTime();
        final long threadTime = (long) ((threadEnd - threadStart) / 1000000.0); //threadTime is in miliseconds
        if (sendEmail) {
            String poruka = "";
            poruka += "\nTrajanje obrade: " + threadTime;
            poruka += "\nTrajanje prethodne obrade: " + ApplicationListener.lastThreadTime;
            poruka += "\nUkupan broj komandi: " + ApplicationListener.reciecedCommandNum;
            poruka += "\nBroj ispravnih komandi: " + ApplicationListener.execCommandNum;
            poruka += "\nBroj neisprevnih komandi: " + ApplicationListener.errorCommandNum;
            Email email = new Email();
            email.uspostaviVezu(ApplicationListener.context.getInitParameter("emailServer"), ApplicationListener.context.getInitParameter("emailServisEmail"), ApplicationListener.context.getInitParameter("emailServisPassword"));
            email.posaljiPoruku(ApplicationListener.context.getInitParameter("emailAdminEmail"), ApplicationListener.context.getInitParameter("emailSubject"), poruka, "text/plain", null, ApplicationListener.context.getInitParameter("emailPort"), ApplicationListener.context.getInitParameter("emailInboxDir"));
            ApplicationListener.lastThreadTime = threadTime;
            Database.insertLog(userId, command.toString(), "" + threadTime);
        } else {
            Database.insertLog("", command.toString(), "" + threadTime);
        }
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    private String get(String address) {
        Location l = Database.getAddressByName(address);
        if (l != null) {
            WeatherData wd = Database.getMeteo(l.getAdresaId());
            if (wd != null) {
                String temp = wd.getTemperature() == null ? "n/a" : wd.getTemperature().toString();
                String hum = wd.getHumidity() == null ? "n/a" : wd.getHumidity().toString();
                String press = wd.getPressureSeaLevel() == null ? "n/a" : wd.getPressureSeaLevel().toString();
                ApplicationListener.execCommandNum++;
                return "OK TEMP " + temp + " VLAGE " + hum + " TLAK " + press + " GEOSIR " + l.getLatitude() + " GEODZ " + l.getLongitude();
            } else {
                ApplicationListener.errorCommandNum++;
                return "ERR 52";
            }
        } else {
            ApplicationListener.errorCommandNum++;
            return "ERR 52";
        }
    }

}
