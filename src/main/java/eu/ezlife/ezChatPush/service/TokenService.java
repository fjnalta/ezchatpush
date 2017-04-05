package eu.ezlife.ezChatPush.service;

import eu.ezlife.ezChatPush.beans.Token;
import eu.ezlife.ezChatPush.database.DBService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

/**
 * Created by ajo on 05.04.17.
 */
@Path("/token")
public class TokenService {

    private static DBService dbHandler = new DBService();

    @RolesAllowed("ADMIN")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addToken(Token token) throws URISyntaxException {
        if(token == null || token.getUsername() == null || token.getResource() == null || token.getToken() == null) {
            return Response.status(400).entity("Token invalid").build();
        }

        if(dbHandler.getUserToken(token.getUsername()).getId() == null) {
            dbHandler.setUserToken(token);
        } else {

            if(!dbHandler.getUserToken(token.getUsername()).getToken().equals(token.getToken())) {
                dbHandler.setUserToken(token);
            } else {
                return Response.status(400).entity("Token already exists").build();
            }
        }

        return Response.status(200).entity(token.toString()).build();
    }

    @RolesAllowed("ADMIN")
    @DELETE
    public Response deleteTokenByUsername(Token token) {
        if(dbHandler.deleteUserToken(token.getUsername())) {
            return Response.status(200).entity("Delete Success").build();
        } else {
            return Response.status(400).entity("Delete Failed").build();
        }

    }
}
