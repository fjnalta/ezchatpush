package eu.ezlife.ezChatPush.service;

import eu.ezlife.ezChatPush.beans.Token;
import eu.ezlife.ezChatPush.database.DBService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.List;

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

        List<Token> myTokens = dbHandler.getUserToken(token.getUsername());

        if(myTokens == null) {
            dbHandler.setUserToken(token);

        } else {
            for(Token curToken : myTokens) {
                if(curToken.getToken().equals(token.getToken())) {
                    return Response.status(400).entity("Token already exists").build();
                }
            }
            dbHandler.setUserToken(token);
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
