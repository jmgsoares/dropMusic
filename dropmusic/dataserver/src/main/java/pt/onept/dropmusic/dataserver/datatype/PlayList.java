package pt.onept.dropmusic.dataserver.datatype;

import java.util.HashSet;

public class PlayList {
    private HashSet<Song> playlist;
    private String name;
    private User owner;
    private HashSet<User> shareList;

    public PlayList() {
        playlist = new HashSet<Song>();
        shareList = new HashSet<User>();
    }

    public PlayList setName(String name){
        this.name=name;
        return this;
    }

    public String getName(){
        return this.name;
    }

    public PlayList setOwner(User user){
        this.owner=user;
        return this;
    }

    public User getOwner() {
        return this.owner;
    }

    public PlayList addSong(Song song){
        this.playlist.add(song);
        return this;
    }

    public Song searchSong(String name) {
        for (Song temp : playlist){
            if(temp.getName().equals(name)) {
                return temp;
            }
        }
        return null;
    }

    public PlayList removeSong(Song song){
        this.playlist.remove(song);
        return this;
    }

    public PlayList addShare(User user){
        this.shareList.add(user);
        return this;
    }

    public PlayList removeShare(User user){
        this.shareList.remove(user);
        return this;
    }

    public HashSet<User> getShareList() {
        return this.shareList;
    }
}