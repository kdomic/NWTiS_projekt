package org.foi.nwtis.kdomic.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.foi.nwtis.kdomic.data.Poruka;

/**
 * Klasa za obradu email poruka: SLANJE i PRIMANJE
 *
 * @author Krunoslav
 */
public class Email {

    private String email_poslusitelj;
    private String korisnicko_ime;
    private String lozinka;

    private Session session;
    private Store store;
    private Folder folder;

    protected List<Poruka> poruke;
    protected Map<String, String> mape;

    /**
     * Metoda za uspostavljanje veze s serverom
     *
     * @param email_poslusitelj
     * @param korisnicko_ime
     * @param lozinka
     * @return
     */
    public Boolean uspostaviVezu(String email_poslusitelj, String korisnicko_ime, String lozinka) {
        this.email_poslusitelj = email_poslusitelj;
        this.korisnicko_ime = korisnicko_ime;
        this.lozinka = lozinka;

        java.util.Properties properties = System.getProperties();
        session = Session.getInstance(properties, null);
        try {
            store = session.getStore("imap");
            store.connect(this.email_poslusitelj, this.korisnicko_ime, this.lozinka);
            folder = store.getDefaultFolder();
        } catch (NoSuchProviderException ex) {
            return false;
        } catch (MessagingException ex) {
            return false;
        }
        return true;
    }

    /**
     * Preuzimanje mapa s poslužitalja
     *
     * @return
     */
    public Map<String, String> preuzmiMape() {
        try {
            Folder[] folderi = folder.list();
            mape = new HashMap<String, String>();
            for (Folder f : folderi) {
                mape.put(f.getName(), f.getName());
            }
            return mape;
        } catch (MessagingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Slanje poruke
     *
     * @param prima
     * @param predmet
     * @param poruka
     * @param tip
     * @param privitak
     * @return
     */
    public Boolean posaljiPoruku(String prima, String predmet, String poruka, String tip, String privitak, String port, String izlazniFolder) {

        try {
            Properties props = (Properties) System.getProperties().clone();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", this.email_poslusitelj);
            props.put("mail.smtp.port", port);
            Session session = Session.getInstance(props, null);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(InternetAddress.parse(this.korisnicko_ime, false)[0]);
            message.setSentDate(new Date());
            message.setSubject(predmet, "utf-8");
            message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(prima, false));

            Multipart multiPart = new MimeMultipart();

            MimeBodyPart messageText = new MimeBodyPart();
            messageText.setContent(poruka, tip + "; charset=utf-8");
            multiPart.addBodyPart(messageText);
            if (privitak != null) {
                Matcher m = Pattern.compile("^Filename=([^\\s]+),StoreLocation=([^\\s]+),size=[\\d\\D\\s\\S\\w\\W]*").matcher(privitak.trim().replaceAll("\\s+", ""));
                if (m.matches()) {
                    String imeDatoteke = m.group(1);
                    String putanja = m.group(2);
                    System.out.println(imeDatoteke);
                    System.out.println(putanja);
                    MimeBodyPart attachment = new MimeBodyPart();
                    FileDataSource attFile = new FileDataSource(putanja);
                    attachment.setDataHandler(new DataHandler(attFile));
                    attachment.setFileName(imeDatoteke);
                    multiPart.addBodyPart(attachment);
                }
            }

            message.setContent(multiPart);

            Transport transport = session.getTransport("smtp");
            transport.connect(this.korisnicko_ime, this.lozinka);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            Folder f = store.getFolder(izlazniFolder);
            kreirajFolder(izlazniFolder);
            f.appendMessages(new Message[]{message});

        } catch (MessagingException mex) {
        }
        return true;
    }

    /**
     * Preuzimanje svijh poruka za danu mapu
     *
     * @param odabranaMapa
     * @return
     * @throws MessagingException
     */
    public List<Poruka> preuzmiPoruke(String odabranaMapa) throws MessagingException {
        Message[] messages;

        folder = store.getFolder(odabranaMapa);
        folder.open(Folder.READ_ONLY);

        messages = folder.getMessages();
        poruke = new ArrayList<Poruka>();
        for (int i = 0; i < messages.length; ++i) {
            Poruka p = new Poruka(messages[i], null);
            poruke.add(p);
        }
        return poruke;
    }

    /**
     * Preuzimanje određene poruke iz određene mape
     *
     * @param findId
     * @param odabranaMapa
     * @param mapaZaPrivitke
     * @return
     * @throws MessagingException
     */
    public Poruka preuzmiPoruku(String findId, String odabranaMapa, String mapaZaPrivitke) throws MessagingException {
        Message[] messages;
        folder = folder.getFolder(odabranaMapa);
        folder.open(Folder.READ_ONLY);
        messages = folder.getMessages();
        for (int i = 0; i < messages.length; ++i) {
            Message m = messages[i];
            System.out.println(m.getHeader("Message-ID")[0]);
            if (findId.equals(m.getHeader("Message-ID")[0])) {
                Poruka p = new Poruka(m, mapaZaPrivitke);
                return p;
            }
        }
        return null;
    }

    /**
     * Provjerava i kreira folder za poruke na serveru
     *
     * @param imeFoldera
     */
    public void kreirajFolder(String imeFoldera) {
        try {
            Folder dfolder = store.getFolder(imeFoldera);
            if (!dfolder.exists()) {
                dfolder.create(Folder.HOLDS_MESSAGES);
            }
        } catch (MessagingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Premještanje poruke iz jednog u drugi direktorij
     *
     * @param src
     * @param dest
     * @param m
     */
    public void premjestiPoruku(String src, String dest, ArrayList<Message> m) {
        if (m.size() <= 0) {
            return;
        }
        try {
            Message[] msg = new Message[m.size()];
            for (int i = 0; i < m.size(); i++) {
                msg[i] = m.get(i);
            }
            Folder sFolder = store.getFolder(src);
            sFolder.open(Folder.READ_WRITE);
            this.kreirajFolder(dest);
            Folder dFolder = store.getFolder(dest);
            sFolder.copyMessages(msg, dFolder);
            sFolder.setFlags(msg, new Flags(Flags.Flag.DELETED), true);
            sFolder.close(true);
        } catch (MessagingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteMessage(Message message, String folder) {
        try {
            Message[] msg = new Message[1];
            msg[0] = message;
            Folder sFolder = store.getFolder(folder);
            sFolder.open(Folder.READ_WRITE);
            sFolder.setFlags(msg, new Flags(Flags.Flag.DELETED), true);
            sFolder.close(true);
        } catch (MessagingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
