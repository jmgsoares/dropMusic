package pt.onept.dropmusic.multicastserver.datatype;

import java.util.HashSet;

public class Album {
    private HashSet<Song> album = new HashSet<Song>();
    private String name;

    public Album(){

    }

    public HashSet<Song> getSongs() {
        return this.album;
    }

    public Album addSong(String name) {
        this.album.add(new Song().setName(name));
        return this;
    }

    public Album removeSong(Song song) {
        this.album.remove(song);
        return this;
    }

    public String getName() {
        return name;
    }

    public Song searchSong(String name) {
        for (Song temp : album){
            if(temp.getName().equals(name)) {
                return temp;
            }
        }
        return null;
    }

    public Album setName(String name) {
        this.name = name;
        return this;
    }
}
