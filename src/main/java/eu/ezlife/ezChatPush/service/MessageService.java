package eu.ezlife.ezChatPush.service;

import eu.ezlife.ezChatPush.beans.PushMessage;
import eu.ezlife.ezChatPush.database.DBService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

/**
 * Created by ajo on 05.04.17.
 */
@Path("/msg")
public class MessageService {

    private static DBService dbHandler = new DBService();

    @RolesAllowed("ADMIN")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(PushMessage msg) throws URISyntaxException {
        // Check if the User told the right username + token
        if(dbHandler.getUserToken(msg.getUserName()).getToken().equals(msg.getToken())) {
            // Check if the contact got a Token
            if(!dbHandler.getUserToken(msg.getContactName()).getToken().equals("")) {

                // TODO - connect to GCM and send MSG
                System.out.println("From: " + msg.getUserName());
                System.out.println("Token: " + msg.getToken());
                System.out.println("To (Token): " + dbHandler.getUserToken(msg.getContactName()).getToken());
            }

            return Response.status(200).entity(msg.toString()).build();

        } else {

            return Response.status(400).entity("Token incomplete").build();
        }
    }
}
