package pt.onept.dropmusic.common.server.contract.type;

import java.io.Serializable;

public class DropmusicDataType<T extends DropmusicDataType> implements Serializable {
	private int id;

	public DropmusicDataType() {
	}

	public DropmusicDataType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public T setId(int id) {
		this.id = id;
		return (T) this;
	}
}