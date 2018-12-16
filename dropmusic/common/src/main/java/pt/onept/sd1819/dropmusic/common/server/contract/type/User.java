package pt.onept.sd1819.dropmusic.common.server.contract.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class User extends DropmusicDataType<User> {
	private String username;
	private String password;
	private Boolean editor;
	private String dropBoxUid;
	private String dropBoxToken;
	private String dropBoxEmail;
}