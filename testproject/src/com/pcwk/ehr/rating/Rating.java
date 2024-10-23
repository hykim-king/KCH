package com.pcwk.ehr.rating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.pcwk.ehr.movie.MovieDao;
import com.pcwk.ehr.movie.MovieVO;

public class Rating {

    private MovieDao movieDao;

    public Rating() {
        movieDao = new MovieDao();
    }

    public void rating() {
        List<MovieVO> allMovies = movieDao.getAllMovies(); // Get all movies

        // Movie title and ratings map
        Map<String, Double> movieRatings = new HashMap<>();

        // Keep only the last rating for movies with duplicate titles
        for (MovieVO movie : allMovies) {
            movieRatings.put(movie.getMovieTitle(), movie.getMovieRating());
        }

        // Create a list from the map
        List<Map.Entry<String, Double>> movieRatingList = new ArrayList<>(movieRatings.entrySet());

        // Sort by rating in descending order
        Collections.sort(movieRatingList, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        System.out.println("\n************ 평점순으로 영화 목록 나열 ************\n");

        // Output the sorted data
        for (int i = 0; i < movieRatingList.size(); i++) {
            Map.Entry<String, Double> entry = movieRatingList.get(i);
            System.out.printf("%d. %s의 평점은 %.1f 입니다.\n", (i + 1), entry.getKey(), entry.getValue());
        }
    }

    public void ratingByThreshold() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("조회할 최소 평점을 입력하세요: ");
        double minRating = scanner.nextDouble();
        List<MovieVO> filteredMovies = movieDao.getMoviesByRating(minRating);

        System.out.println("\n************ 평점이 " + minRating + " 이상인 영화 목록 ************\n");

        if (filteredMovies.isEmpty()) {
            System.out.println("해당 평점 이상의 영화가 없습니다.");
        } else {
            for (MovieVO movie : filteredMovies) {
                System.out.printf("제목: %s, 평점: %.1f\n", movie.getMovieTitle(), movie.getMovieRating());
            }
        }
    }
}
