package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.server.contract.type.Artist;

import java.rmi.Remote;

public interface ArtistManagerInterface extends Remote {
    public boolean insertArtist(Artist artist);
    public boolean removeArtist(Artist artist);
    public boolean editArtist(Artist artist);
}
