package pt.onept.sd1819.dropmusic.common.server.contract.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Review
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Review extends DropmusicDataType<Review> {
	private int albumId;
	private float score;
	private String review;
}