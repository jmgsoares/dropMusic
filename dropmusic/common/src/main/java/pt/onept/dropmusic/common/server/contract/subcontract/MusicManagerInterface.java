package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.server.contract.Crudable;
import pt.onept.dropmusic.common.server.contract.type.Music;

import java.io.Serializable;
import java.rmi.Remote;

/**
 * Manage the platform musics
 * @author João Soares
 * @version 1e-1024
 */
public interface MusicManagerInterface extends Remote, Crudable<Music>, Serializable {
}
