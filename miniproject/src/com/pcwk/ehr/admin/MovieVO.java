package com.pcwk.ehr.admin;

import java.time.LocalDate;
import java.util.Objects;

public class MovieVO {
    private String title;
    private String genre;
    private int age;
    private Double rating;
    @Override
	public int hashCode() {
		return Objects.hash(age, genre, rating, seat, startDate, time, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovieVO other = (MovieVO) obj;
		return age == other.age && Objects.equals(genre, other.genre) && Objects.equals(rating, other.rating)
				&& Objects.equals(seat, other.seat) && Objects.equals(startDate, other.startDate)
				&& Objects.equals(time, other.time) && Objects.equals(title, other.title);
	}

	private LocalDate startDate;
    private String time;
    private String seat; 

    public MovieVO(String title, String genre, int age, Double rating, LocalDate startDate, String time, String seat) {
        this.title = title;
        this.genre = genre;
        this.age = age;
        this.rating = rating;
        this.startDate = startDate != null ? startDate : LocalDate.now(); // 현재 날짜로 설정
        this.time = time;
        this.seat = seat; 
    }

    public MovieVO() {
		// TODO Auto-generated constructor stub
	}

	// Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    @Override
    public String toString() {
        return "MovieVO{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", age=" + age +
                ", rating=" + rating +
                ", startDate=" + startDate +
                ", time='" + time + '\'' +
                ", seat=" + seat +
                '}';
    }
}
