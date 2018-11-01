package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.server.contract.Crudable;
import pt.onept.dropmusic.common.server.contract.Searchable;
import pt.onept.dropmusic.common.server.contract.type.Album;

import java.io.Serializable;
import java.rmi.Remote;

/**
 * Manage platform Albums
 *
 * @author João Soares
 * @version 1e-1024
 */
public interface AlbumManagerInterface extends Remote, Crudable<Album>, Searchable<Album>, Serializable {
}