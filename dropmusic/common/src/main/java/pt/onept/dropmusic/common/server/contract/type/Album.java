package pt.onept.dropmusic.common.server.contract.type;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Album implements Serializable {
	private String name;
	private Artist artist;
	private List<Music> songs;
	private Set<Review> reviews;
	private float score;

	public Album(String name, Artist artist, List<Music> songs, Set<Review> reviews, float score) {
		this.name = name;
		this.artist = artist;
		this.songs = songs;
		this.reviews = reviews;
		this.score = score;
	}

	public Album() {
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

	public List<Music> getSongs() {
		return songs;
	}

	public Album setSongs(List<Music> songs) {
		this.songs = songs;
		return this;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public Album setReviews(Set<Review> reviews) {
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
}

