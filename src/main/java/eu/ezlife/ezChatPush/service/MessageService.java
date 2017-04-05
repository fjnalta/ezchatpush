/*package eu.ezlife.ezChatPush.service;

import eu.ezlife.ezChatPush.beans.Token;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

*//**
 * Created by ajo on 05.04.17.
 *//*
@Path("/msg")
public class MessageService {

    @RolesAllowed("ADMIN")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(Token token, String contactName) throws URISyntaxException {
        if(token.getUsername() == null || token.getToken() == null) {
            return Response.status(400).entity("Token incomplete").build();
        }
        if(contactName == "") {
            return Response.status(400).entity("ContactName incomplete").build();
        }

        // TODO - Check local database for username and token

        // TODO - search for token of contactName

        // TODO - connect to GCM and send MSG

        return Response.created(new URI("/rest/token/"+token.getId())).build();
    }
}*/
