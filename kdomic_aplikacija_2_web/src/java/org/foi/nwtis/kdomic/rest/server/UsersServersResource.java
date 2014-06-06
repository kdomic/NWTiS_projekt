/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.rest.server;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.foi.nwtis.kdomic.beans.portfelj.PrijavljeniKorisnici;
import org.foi.nwtis.kdomic.eb.Users;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 * REST Web Service
 *
 * @author Krunoslav
 */
@Path("/us")
public class UsersServersResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UsersServersResource
     */
    public UsersServersResource() {
    }

    /**
     * Retrieves representation of an instance of
     * org.foi.nwtis.kdomic.rest.server.UsersServersResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getXml() {
        JSONArray users = new JSONArray();
        for (Users u : PrijavljeniKorisnici.usersList) {
            JSONObject jo = new JSONObject();
            try {
                jo.put("id", u.getId());
                jo.put("username", u.getUsername());
                jo.put("firstName", u.getFirstname());
                jo.put("lastName", u.getLastname());
            } catch (JSONException ex) {
                Logger.getLogger(UsersServersResource.class.getName()).log(Level.SEVERE, null, ex);
            }
                users.put(jo);
        }
        return users.toString();
    }

    /**
     * POST method for creating an instance of UsersServerResource
     *
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response postXml(String content) {
        //TODO
        return Response.created(context.getAbsolutePath()).build();
    }

    /**
     * Sub-resource locator method for {id}
     */
    @Path("{id}")
    public UsersServerResource getUsersServerResource(@PathParam("id") String id) {
        return UsersServerResource.getInstance(id);
    }
}
