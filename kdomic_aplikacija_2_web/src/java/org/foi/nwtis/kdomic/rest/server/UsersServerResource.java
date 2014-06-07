/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.kdomic.rest.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;

/**
 * REST Web Service
 *
 * @author Krunoslav
 */
public class UsersServerResource {

    private String id;

    /**
     * Creates a new instance of UsersServerResource
     */
    private UsersServerResource(String id) {
        this.id = id;
    }

    /**
     * Get instance of the UsersServerResource
     */
    public static UsersServerResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of UsersServerResource class.
        return new UsersServerResource(id);
    }

    /**
     * Retrieves representation of an instance of org.foi.nwtis.kdomic.rest.server.UsersServerResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of UsersServerResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putXml(String content) {
    }

    /**
     * DELETE method for resource UsersServerResource
     */
    @DELETE
    public void delete() {
    }
}
