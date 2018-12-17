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
public class File extends DropmusicDataType<File> {
	private int ownerId;
	private int musicId;
	private String musicName;
	private String dropBoxFileId;
	private String dropBoxPrevUrl;
	private String dropBoxFileName;
	private String dropBoxFilePath;
}