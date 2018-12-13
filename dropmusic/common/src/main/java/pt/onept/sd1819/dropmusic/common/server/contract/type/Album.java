package pt.onept.sd1819.dropmusic.common.server.contract.type;

import java.util.List;

public class Album extends DropmusicDataType<Album> {
	private String name;
	private Artist artist;
	private String description;
	private List<Music> musics;
	private List<Review> reviews;
	private float score;

	public Album() {
	}

	public Album(int id, String name, Artist artist, String description, List<Music> musics, List<Review> reviews, float score) {
		super(id);
		this.name = name;
		this.artist = artist;
		this.description = description;
		this.musics = musics;
		this.reviews = reviews;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public Album setName(String name) {
		this.name = name;
		return this;
	}

	public Artist getArtist() {
		return artist;
	}

	public Album setArtist(Artist artist) {
		this.artist = artist;
		return this;
	}

	public List<Music> getMusics() {
		return musics;
	}

	public Album setMusics(List<Music> musics) {
		this.musics = musics;
		return this;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public Album setReviews(List<Review> reviews) {
		this.reviews = reviews;
		return this;
	}

	public float getScore() {
		return score;
	}

	public Album setScore(float score) {
		this.score = score;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Album setDescription(String description) {
		this.description = description;
		return this;
	}

}


