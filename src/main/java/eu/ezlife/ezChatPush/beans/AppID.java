package eu.ezlife.ezChatPush.beans;

/**
 * Created by ajo on 05.04.17.
 */
public class AppID {
    private Integer id;
    private String appId;

    public AppID() {}

    public AppID(Integer id, String appId) {
        this.id  = id;
        this.appId = appId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "AppID [id=" + id + ", AppID=" + appId + "]";
    }
}
