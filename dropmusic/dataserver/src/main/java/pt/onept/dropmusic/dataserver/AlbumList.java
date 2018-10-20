package pt.onept.dropmusic.dataserver;

import pt.onept.dropmusic.dataserver.datatype.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumList {
    private List<Album> albums = new ArrayList<Album>();

    public AlbumList addAlbum(String name) {
        this.albums.add(new Album().setName(name));
        return this;
    }

    public AlbumList removeAlbum(Album album){
        this.albums.remove(album);
        return this;
    }

    public Album searchAlbum(String name) {
        for (Album temp : albums){
            if(temp.getName().equals(name)) {
                return temp;
            }
        }
        return null;
    }
    public List<Album> getAlbums(){
        return albums;
    }
}