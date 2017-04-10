package eu.ezlife.ezChatPush.service;

import eu.ezlife.ezChatPush.beans.PushMessage;
import eu.ezlife.ezChatPush.beans.Token;
import eu.ezlife.ezChatPush.database.DBService;
import eu.ezlife.ezChatPush.database.PropertiesService;
import org.json.JSONObject;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * Created by ajo on 05.04.17.
 */
@Path("/msg")
public class MessageService {

    private final static String API_URL = "https://fcm.googleapis.com/fcm/send";

    private static DBService dbHandler = new DBService();

    @RolesAllowed("ADMIN")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(PushMessage msg) throws URISyntaxException {

        PropertiesService propService = new PropertiesService();
        Properties prop = propService.getProp();
        String AUTH_KEY = prop.getProperty("appid");

        // Check if the User told the right username + token
        if(dbHandler.tokenExists(msg.getUserName(), msg.getToken())) {
            // Check if the contact got a Token
            if(dbHandler.hasToken(msg.getContactName())) {

                // connect to GCM and send MSG
                try {
                    URL url = new URL(API_URL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setUseCaches(false);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Authorization","key="+AUTH_KEY);
                    conn.setRequestProperty("Content-Type",MediaType.APPLICATION_JSON);

                    List<Token> contactTokens = dbHandler.getUserToken(msg.getContactName());
                    for (Token curToken : contactTokens) {

                        // create JSON Body
                        JSONObject wrapper = new JSONObject();
                        wrapper.put("to",curToken.getToken().trim());
                        JSONObject content = new JSONObject();
                        content.put("contact", msg.getUserName());
                        wrapper.put("data", content);
                        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                        wr.write(wrapper.toString());
                        wr.flush();

                        conn.getInputStream();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return Response.status(200).entity("OK").build();
        } else {
            return Response.status(400).entity("Token incomplete").build();
        }
    }
}
