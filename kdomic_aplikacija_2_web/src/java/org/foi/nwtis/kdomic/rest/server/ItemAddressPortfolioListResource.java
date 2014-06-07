/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.rest.server;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import org.foi.nwtis.kdomic.beans.portfelj.PrijavljeniKorisnici;
import org.foi.nwtis.kdomic.eb.MeteoPortfolio;
import org.foi.nwtis.kdomic.eb.PortfolioAddresses;
import org.foi.nwtis.kdomic.eb.Users;
import org.foi.nwtis.kdomic.sb.PortfolioAddressesFacade;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Krunoslav
 */
public class ItemAddressPortfolioListResource {

    PortfolioAddressesFacade portfolioAddressesFacade = lookupPortfolioAddressesFacadeBean();

    private String id;

    /**
     * Creates a new instance of ItemAddressPortfolioListResource
     */
    private ItemAddressPortfolioListResource(String id) {
        this.id = id;
    }

    /**
     * Get instance of the ItemAddressPortfolioListResource
     */
    public static ItemAddressPortfolioListResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of ItemAddressPortfolioListResource class.
        return new ItemAddressPortfolioListResource(id);
    }

    /**
     * Retrieves representation of an instance of
     * org.foi.nwtis.kdomic.rest.server.ItemAddressPortfolioListResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getXml() {
        JSONObject json = new JSONObject();
        try {
            JSONArray address = new JSONArray();
            for (PortfolioAddresses pa : portfolioAddressesFacade.findByPortfolio(new MeteoPortfolio(Integer.parseInt(id)))) {
                JSONObject jo = new JSONObject();
                jo.put("id", pa.getId());
                jo.put("naziv", pa.getAddress());
                address.put(jo);
            }
            json.put("adrese", address);
        } catch (JSONException ex) {
            Logger.getLogger(UsersServersResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json.toString();
    }

    /**
     * PUT method for updating or creating an instance of
     * ItemAddressPortfolioListResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putXml(String content) {
    }

    /**
     * DELETE method for resource ItemAddressPortfolioListResource
     */
    @DELETE
    public void delete() {
    }

    private PortfolioAddressesFacade lookupPortfolioAddressesFacadeBean() {
        try {
            Context c = new InitialContext();
            return (PortfolioAddressesFacade) c.lookup("java:global/kdomic_aplikacija_2/kdomic_aplikacija_2_ejb/PortfolioAddressesFacade!org.foi.nwtis.kdomic.sb.PortfolioAddressesFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
