package pt.onept.sd1819.dropmusic.common.server.contract.subcontract;

import pt.onept.sd1819.dropmusic.common.server.contract.Crudable;
import pt.onept.sd1819.dropmusic.common.server.contract.Listable;
import pt.onept.sd1819.dropmusic.common.server.contract.Searchable;
import pt.onept.sd1819.dropmusic.common.server.contract.type.Album;

import java.io.Serializable;
import java.rmi.Remote;

/**
 * Manage platform Albums
 *
 * @author João Soares
 * @version 1e-1024
 */
public interface AlbumManagerInterface extends Remote, Crudable<Album>, Listable<Album>, Searchable<Album>, Serializable {
}