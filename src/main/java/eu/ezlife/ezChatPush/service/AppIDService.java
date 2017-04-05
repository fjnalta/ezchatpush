package eu.ezlife.ezChatPush.service;

import eu.ezlife.ezChatPush.beans.AppID;
import eu.ezlife.ezChatPush.database.DBService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by ajo on 05.04.17.
 */

@Path("/appid")
public class AppIDService {

    private DBService dbHandler;
    public AppIDService() {

        dbHandler = new DBService();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public AppID getAppID()
    {
        AppID appID = dbHandler.getAppID();
        System.out.println(appID.toString());
        return appID;
    }
}
