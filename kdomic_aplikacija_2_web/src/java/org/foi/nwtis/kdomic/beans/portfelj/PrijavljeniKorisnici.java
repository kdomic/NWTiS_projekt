/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.beans.portfelj;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import org.foi.nwtis.kdomic.eb.Users;

/**
 *
 * @author Krunoslav
 */
@ManagedBean
@ApplicationScoped
public class PrijavljeniKorisnici {

    public static List<Users> usersList = new ArrayList<>();

    public PrijavljeniKorisnici() {
    }

}
