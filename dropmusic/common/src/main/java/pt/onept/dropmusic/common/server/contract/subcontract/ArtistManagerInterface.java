package pt.onept.dropmusic.common.server.contract.subcontract;

import pt.onept.dropmusic.common.exception.DuplicatedException;
import pt.onept.dropmusic.common.exception.NotFoundException;
import pt.onept.dropmusic.common.exception.UnauthorizedException;
import pt.onept.dropmusic.common.server.contract.Crudable;
import pt.onept.dropmusic.common.server.contract.type.Artist;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Manage platform Albums
 * @author João Soares
 * @version 1e-1024
 */
public interface ArtistManagerInterface extends Remote, Crudable<Artist> {
}