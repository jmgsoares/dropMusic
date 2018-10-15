package pt.onept.dropmusic.multicastserver;

import pt.onept.dropmusic.multicastserver.datatype.PlayList;
import pt.onept.dropmusic.multicastserver.datatype.User;

import java.util.HashMap;
import java.util.HashSet;

public class PlaylistManager {
    private HashMap<String, PlayList> playlists;
    private HashSet<String> sharedplaylists;

    public HashMap<String, PlayList> getPlaylists() {
        return this.playlists;
    }

    public PlaylistManager addPlaylist(User user, String name) {
        this.playlists.put(name, new PlayList().setName(name));
        return this;
    }

    public PlayList getPlaylist(String name) {
        return this.playlists.get(name);
    }

    public PlaylistManager removePlaylist(String name) {
        this.playlists.remove(name);
        return this;
    }

    public PlaylistManager addSharedPlaylist(String name){
        this.sharedplaylists.add(name);
        return this;
    }

    public PlaylistManager removeSharedPlaylist(String name){
        this.sharedplaylists.remove(name);
        return this;
    }
}