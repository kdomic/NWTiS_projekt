/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.rest.server;

import java.util.List;
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
import org.foi.nwtis.kdomic.eb.Users;
import org.foi.nwtis.kdomic.sb.MeteoPortfolioFacade;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Krunoslav
 */
public class PortfolioListResource {

    MeteoPortfolioFacade meteoPortfolioFacade = lookupMeteoPortfolioFacadeBean();

    private String id;

    /**
     * Creates a new instance of PortfolioListResource
     */
    private PortfolioListResource(String id) {
        this.id = id;
    }

    /**
     * Get instance of the PortfolioListResource
     */
    public static PortfolioListResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of PortfolioListResource class.
        return new PortfolioListResource(id);
    }

    /**
     * Retrieves representation of an instance of
     * org.foi.nwtis.kdomic.rest.server.PortfolioListResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        JSONObject json = new JSONObject();
        try {
            JSONArray portfolios = new JSONArray();
            for (MeteoPortfolio mp : meteoPortfolioFacade.findByUser(new Users(Integer.parseInt(this.id)))) {
                JSONObject jo = new JSONObject();
                jo.put("id", mp.getId());
                jo.put("name", mp.getName());
                portfolios.put(jo);
            }
            json.put("portfelji", portfolios);
        } catch (JSONException ex) {
            Logger.getLogger(UsersServersResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json.toString();
    }

    /**
     * PUT method for updating or creating an instance of PortfolioListResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }

    /**
     * DELETE method for resource PortfolioListResource
     */
    @DELETE
    public void delete() {
    }

    private MeteoPortfolioFacade lookupMeteoPortfolioFacadeBean() {
        try {
            Context c = new InitialContext();
            return (MeteoPortfolioFacade) c.lookup("java:global/kdomic_aplikacija_2/kdomic_aplikacija_2_ejb/MeteoPortfolioFacade!org.foi.nwtis.kdomic.sb.MeteoPortfolioFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }

    }
}
