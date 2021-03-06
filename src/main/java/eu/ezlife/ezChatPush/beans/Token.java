package eu.ezlife.ezChatPush.beans;

/**
 * Created by ajo on 05.04.17.
 */
public class Token {
    private Integer id;
    private String username;
    private String token;

    public Token() {}

    public Token(Integer id, String username, String token) {
        this.id  = id;
        this.username = username;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Token [id=" + id + ", username=" + username + ", token=" + token + "]";
    }
}
