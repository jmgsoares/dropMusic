package pt.onept.sd1819.dropmusic.server.service;

import pt.onept.sd1819.dropmusic.common.client.contract.Updatable;
import pt.onept.sd1819.dropmusic.common.exception.*;
import pt.onept.sd1819.dropmusic.common.server.contract.subcontract.UpdateManagerInterface;
import pt.onept.sd1819.dropmusic.common.server.contract.type.*;
import pt.onept.sd1819.dropmusic.common.utililty.SubType;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class to manage all the notification related operations
 */
public class UpdateManager extends UnicastRemoteObject implements UpdateManagerInterface {
	private Map<UUID, Updatable> updatables;
	private Map<Class,Map<Integer,Set<UUID>>> objectUuid;

	public UpdateManager() throws RemoteException {
		super();
		this.updatables = new ConcurrentHashMap<>();
		this.objectUuid = new ConcurrentHashMap<>();
	}

	@Override
	public <T extends DropmusicDataType> void update(T object) throws RemoteException {
		if(this.objectUuid.get(SubType.getSubtype(object)) == null) return;
		if(this.objectUuid.get(SubType.getSubtype(object)).get(object.getId()) == null) return;
		Set<UUID> updatableIds = this.objectUuid.get(SubType.getSubtype(object)).get(object.getId());
		if( updatableIds != null) updatableIds.stream()
				.parallel()
				.map(u -> this.updatables.get(u))
				.forEach(n -> {
					try {
						n.update(object);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				});
	}

	@Override
	public <T extends DropmusicDataType> UUID subscribe(T object, Updatable client) throws RemoteException {
		UUID subscriptionId = UUID.randomUUID();
		this.updatables.put(subscriptionId, client);
		this.objectUuid.computeIfAbsent(SubType.getSubtype(object), k-> new ConcurrentHashMap<>());
		this.objectUuid.get(SubType.getSubtype(object)).computeIfAbsent(object.getId(), k -> ConcurrentHashMap.newKeySet()).add(subscriptionId);
		return subscriptionId;
	}

	@Override
	public <T extends DropmusicDataType> void unSubscribe(T object, UUID subscriptionId) throws RemoteException {
		this.objectUuid.get(SubType.getSubtype(object)).get(object.getId()).remove(subscriptionId);

		if (this.objectUuid.get(SubType.getSubtype(object)).get(object.getId()).isEmpty()) {
			this.objectUuid.get(SubType.getSubtype(object)).remove(object.getId());
		}
		if (this.objectUuid.get(SubType.getSubtype(object)).isEmpty()) {
			this.objectUuid.remove(SubType.getSubtype(object));
		}
		this.updatables.remove(subscriptionId);
	}
}
