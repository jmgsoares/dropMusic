package pt.onept.sd1819.dropmusic.common.communication.protocol;

import pt.onept.sd1819.dropmusic.common.server.contract.type.DropmusicDataType;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Base Class to support the information exchange via multicast
 * @param <T> Class T
 */
public class Message<T extends DropmusicDataType> implements Serializable {
	private final static String APPID = "de9db107-9834-4aeb-a731-86d959513831";
	private String appId;
	private UUID id;
	private Operation operation;
	private User self;
	private T data;
	private long dataId;
	private String query;
	//todo the target user can be passed on the T data parameter
	private User target;
	private List<T> dataList;

	public Message() {
	}

	public Message(String appId, UUID id, Operation operation, User self, T data, long dataId, String query, User target, List<T> dataList) {
		this.appId = appId;
		this.id = id;
		this.operation = operation;
		this.self = self;
		this.data = data;
		this.dataId = dataId;
		this.query = query;
		this.target = target;
		this.dataList = dataList;
	}

	public static String getAPPID() {
		return APPID;
	}

	public String getAppId() {
		return appId;
	}

	public Message setAppId(String appId) {
		this.appId = appId;
		return this;
	}

	public UUID getId() {
		return id;
	}

	public Message setId(UUID id) {
		this.id = id;
		return this;
	}

	public Operation getOperation() {
		return operation;
	}

	public Message setOperation(Operation operation) {
		this.operation = operation;
		return this;
	}

	public User getSelf() {
		return self;
	}

	public Message setSelf(User self) {
		this.self = self;
		return this;
	}

	public T getData() {
		return data;
	}

	public Message setData(T data) {
		this.data = data;
		return this;
	}

	public String getQuery() {
		return query;
	}

	public Message setQuery(String query) {
		this.query = query;
		return this;
	}

	public User getTarget() {
		return target;
	}

	public Message setTarget(User target) {
		this.target = target;
		return this;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public Message setDataList(List<T> dataList) {
		this.dataList = dataList;
		return this;
	}

	public long getDataId() {
		return dataId;
	}

	public Message setDataId(long dataId) {
		this.dataId = dataId;
		return this;
	}
}
