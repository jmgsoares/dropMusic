package pt.onept.dropmusic.common.server.contract.type;

import java.io.Serializable;

public class Review extends DropmusicDataType implements Serializable {
	private int id;
	private float score;
	private String review;

	public Review() {
	}

	public float getScore() {
		return score;
	}

	public Review setScore(float score) {
		this.score = score;
		return this;
	}

	public String getReview() {
		return review;
	}

	public Review setReview(String review) {
		this.review = review;
		return this;
	}
}
