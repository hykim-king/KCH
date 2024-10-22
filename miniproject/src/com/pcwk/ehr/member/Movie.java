package com.pcwk.ehr.member;

public class Movie {
	private String title;
	private String genre;
	private double rating;

	public Movie(String title, String genre, double rating) {
		this.title = title;   //제목
		this.genre = genre;   //장르
		this.rating = rating; //평점
	}

	public String getGenre() {
		return genre;
	}

	public String getTitle() {
		return title;
	}

	public double getRating() {
		return rating;
	}
}
