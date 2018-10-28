package pt.onept.dropmusic.common.server.contract.type;

import java.io.Serializable;

public class Review extends DropmusicDataType<Review> {
	private int albumId;
	private float score;
	private String review;

	public Review() {
	}

	public Review(int id, int albumId, float score, String review) {
		super(id);
		this.albumId = albumId;
		this.score = score;
		this.review = review;
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

	public int getAlbumId() {
		return albumId;
	}

	public Review setAlbumId(int albumId) {
		this.albumId = albumId;
		return this;
	}
}
