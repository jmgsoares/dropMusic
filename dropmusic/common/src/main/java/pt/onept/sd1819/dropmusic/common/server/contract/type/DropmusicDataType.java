package pt.onept.sd1819.dropmusic.common.server.contract.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class DropmusicDataType<T extends DropmusicDataType> implements Serializable {
	private int id;

	public T setId(int id) {
		this.id = id;
		return (T) this;
	}
}