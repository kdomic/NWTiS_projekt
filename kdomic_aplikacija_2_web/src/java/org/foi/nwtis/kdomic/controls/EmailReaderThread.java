/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.controls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.mail.MessagingException;
import javax.naming.Context;
import javax.servlet.ServletContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.foi.nwtis.kdomic.data.CommunicationMessageEmail;
import org.foi.nwtis.kdomic.data.Poruka;
import org.foi.nwtis.kdomic.listeners.ApplicationListener;
import org.foi.nwtis.kdomic.server.Email;

/**
 *
 * @author Krunoslav
 */
public class EmailReaderThread extends TimerTask {

    private final String emailServer;
    private final String emailAdminEmail;
    private final String emailAdminPassword;
    private final String emailInboxDir;
    private final String emailNWTiSDir;
    private final String emailOtherDir;
    private final String emailSubject;

    public EmailReaderThread() {
        ServletContext context = ApplicationListener.context;
        this.emailServer = context.getInitParameter("emailServer");
        this.emailAdminEmail = context.getInitParameter("emailAdminEmail");
        this.emailAdminPassword = context.getInitParameter("emailAdminPassword");
        this.emailInboxDir = context.getInitParameter("emailInboxDir");
        this.emailNWTiSDir = context.getInitParameter("emailNWTiSDir");
        this.emailOtherDir = context.getInitParameter("emailOtherDir");
        this.emailSubject = context.getInitParameter("emailSubject");
    }

    @Override
    public void run() {
        System.out.println("[APP 2] THREAD STARTED AT: " + new Date());
        this.process();
    }

    private void process() {
        Email email = new Email();
        email.uspostaviVezu(emailServer, emailAdminEmail, emailAdminPassword);
        Integer numberOfMessages = 0;
        Integer numberOfNwtisMessage = 0;
        Integer numberOfOtherMessages = 0;
        Date dateStart = new Date();
        try {
            List<Poruka> poruke = email.preuzmiPoruke(emailInboxDir);
            ArrayList<javax.mail.Message> listaIspravni = new ArrayList<>();
            ArrayList<javax.mail.Message> listaNeisprevni = new ArrayList<>();
            for (Poruka poruka : poruke) {
                numberOfMessages++;
                if (poruka.getPredmet().startsWith(emailSubject)) {
                    numberOfNwtisMessage++;
                    listaIspravni.add(poruka.getRaw());
                } else {
                    numberOfOtherMessages++;
                    listaNeisprevni.add(poruka.getRaw());
                }
            }
            email.premjestiPoruku(emailInboxDir, emailNWTiSDir, listaIspravni);
            email.premjestiPoruku(emailInboxDir, emailOtherDir, listaNeisprevni);
        } catch (MessagingException ex) {
            Logger.getLogger(EmailReaderThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date dateEnd = new Date();
        try {
            this.sendJMSMessageToNWTiS_kdomic_1(new CommunicationMessageEmail(dateStart, dateEnd, numberOfMessages, numberOfNwtisMessage, numberOfOtherMessages));
        } catch (JMSException | NamingException ex) {
            Logger.getLogger(EmailReaderThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Message createJMSMessageForjmsNWTiS_kdomic_1(Session session, CommunicationMessageEmail messageData) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage();
        objectMessage.setObject(messageData);
        return (Message) objectMessage;
    }

    public void sendJMSMessageToNWTiS_kdomic_1(CommunicationMessageEmail messageData) throws JMSException, NamingException {
        Context c = new InitialContext();
        ConnectionFactory cf = (ConnectionFactory) c.lookup("java:comp/env/jms/NWTiS_QF_kdomic_1");
        Connection conn = null;
        Session s = null;
        try {
            conn = cf.createConnection();
            s = conn.createSession(false, s.AUTO_ACKNOWLEDGE);
            Destination destination = (Destination) c.lookup("java:comp/env/jms/NWTiS_kdomic_1");
            MessageProducer mp = s.createProducer(destination);
            mp.send(createJMSMessageForjmsNWTiS_kdomic_1(s, messageData));
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
                }
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

}
