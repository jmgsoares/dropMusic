package pt.onept.dropmusic.multicastserver.datatype;

import java.util.HashMap;
import java.util.HashSet;

public class User {
    private String name;
    private String email;
    private String login;
    private String password;
    private boolean editor;
    private HashMap<String, PlayList> playlists;
    private HashSet<String> sharedplaylists;

    public User() {

    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isEditor() {
        return editor;
    }

    public User setEditor(boolean editor) {
        this.editor = editor;
        return this;
    }
}