
package org.foi.nwtis.kdomic.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

/**
 * Struktura (MODEL) podataka za opis poruke te omogućava manipulaciju porukama
 * @author Krunoslav
 */

public class Poruka {

    Message raw;

    private String id;
    private Date vrijeme;
    private String salje;
    private String predmet;
    private String vrsta;
    private int velicina;
    private int brojPrivitaka;
    private Flags zastavice;
    private boolean brisati;
    private boolean procitano;
    private String sadrzaj;

    /**
     * Inicijalizacija poruke
     * @param m poruka tipa Message
     * @param mapaZaPrivtke putanja do foldera za sopremanje, ili NULL ako se ne želi preuzeti privitak
     */
    public Poruka(Message m, String mapaZaPrivtke) {
        try {
            this.raw = m;
            this.id = m.getHeader("Message-ID")[0];
            this.vrijeme = m.getSentDate();
            this.salje = m.getFrom()[0].toString();
            this.predmet = m.getSubject();
            this.vrsta = m.getContentType();
            this.velicina = m.getSize();
            this.zastavice = m.getFlags();
            this.brisati = false;
            this.procitano = true;

            this.brojPrivitaka = 0;
            this.sadrzaj = "";

            Object tjeloPoruke;
            try {
                tjeloPoruke = m.getContent();
                if (tjeloPoruke instanceof String) {
                    this.sadrzaj = (String) tjeloPoruke;
                } else if (tjeloPoruke instanceof Multipart) {
                    Multipart mp = (Multipart) m.getContent();
                    for (int i = 0; i < mp.getCount(); i++) {
                        BodyPart bp = mp.getBodyPart(i);
                        if (bp.isMimeType("text/*")) {
                            this.sadrzaj = (String) bp.getContent();
                        } 
                    }
                } else {
                    this.sadrzaj = "???NEPOZNATI SADRŽAJ";
                }
            } catch (IOException ex) {
            }
        } catch (MessagingException ex) {
            Logger.getLogger(Poruka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Preuzmi privitak i pohrani u fleder
     * @param bodyPart
     * @param mapaZaPrivtke
     * @throws IOException
     * @throws MessagingException 
     */
    private void preuzniPrivitak(BodyPart bodyPart, String mapaZaPrivtke) throws IOException, MessagingException {
        InputStream is = bodyPart.getInputStream();
        File f = new File(mapaZaPrivtke + bodyPart.getFileName());
        FileOutputStream fos = new FileOutputStream(f);
        byte[] buf = new byte[4096];
        int bytesRead;
        while ((bytesRead = is.read(buf)) != -1) {
            fos.write(buf, 0, bytesRead);
        }
        fos.close();
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    public String getId() {
        return id;
    }

    public boolean isBrisati() {
        return brisati;
    }

    public void setBrisati(boolean brisati) {
        this.brisati = brisati;
    }

    public int getBrojPrivitaka() {
        return brojPrivitaka;
    }

    public Flags getZastavice() {
        return zastavice;
    }

    public Date getVrijeme() {
        return vrijeme;
    }

    public String getPredmet() {
        return predmet;
    }

    public boolean isProcitano() {
        return procitano;
    }

    public void setProcitano(boolean procitano) {
        this.procitano = procitano;
    }

    public String getSalje() {
        return salje;
    }

    public String getVrsta() {
        return vrsta;
    }

    public int getVelicina() {
        return velicina;
    }

    public void setVrijeme(Date vrijeme) {
        this.vrijeme = vrijeme;
    }

}
