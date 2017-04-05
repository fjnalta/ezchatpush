package eu.ezlife.ezChatPush.beans;

/**
 * Created by ajo on 05.04.17.
 */
public class PushMessage {

    private String userName;
    private String contactName;
    private String token;

    public PushMessage() {}

    public PushMessage(String userName, String contactName, String token) {
        this.userName = userName;
        this.contactName = contactName;
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "PushMessage [userName=" + userName + ", contactName=" + contactName + ", token=" + token + "]";
    }

}
