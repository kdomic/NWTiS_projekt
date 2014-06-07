/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.rest.client;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.kdomic.data.Users;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Krunoslav
 */
public class RESTusers {

    Client client;

    public RESTusers() {
        client = ClientBuilder.newClient();
    }

    public List<Users> getUsers() {
        WebTarget webResource = client.target("http://localhost:8080/kdomic_aplikacija_2_web/webresources/us");
        String response = webResource.request(MediaType.APPLICATION_JSON).get(String.class);
        List<Users> usersList = null;

        try {
            JSONObject jo = new JSONObject(response);
            JSONArray usersJson = jo.getJSONArray("korisnici");
            for (int i = 0; i < usersJson.length(); i++) {
                if (usersList == null) {
                    usersList = new ArrayList<>();
                }
                JSONObject userJson = usersJson.getJSONObject(i);
                usersList.add(new Users(userJson.getString("id"), replacer(userJson.getString("lastName")), replacer(userJson.getString("firstName")), userJson.getString("username")));
            }
        } catch (JSONException ex) {
            Logger.getLogger(RESTusers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usersList;
    }

    private String replacer(String a) {
        a = a.replaceAll("Ä", "č");
        a = a.replaceAll("Ä", "ć");
        a = a.replaceAll("Å¾", "ž");
        a = a.replaceAll("Å¡", "š");
        a = a.replaceAll("Ä", "đ");
        a = a.replaceAll("Ä", "Č");
        a = a.replaceAll("Ä", "Ć");
        a = a.replaceAll("Å½", "Ž");
        a = a.replaceAll("Å ", "Š");
        a = a.replaceAll("Ä", "Đ");
        return a;
    }

}
