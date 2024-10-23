package com.pcwk.ehr.movie;

import java.io.*;
import java.util.*;

public class MovieDao {
    private static final String fileName = "D:\\JAP_20240909\\01_JAVA\\workspace\\testproject\\movies.csv";

    public List<MovieVO> getAllMovies() {
        List<MovieVO> movies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data.length == 7) {
                    int id = Integer.parseInt(data[0]);
                    String movieTitle = data[1];
                    String movieGenre = data[2];
                    double movieRating = Double.parseDouble(data[3]);
                    int minAge = Integer.parseInt(data[4]);

                    Map<String, List<String>> schedule = new HashMap<>();
                    String[] days = data[5].split("/");
                    String[] times = data[6].split("/");
                    for (int i = 0; i < days.length; i++) {
                        schedule.put(days[i], Arrays.asList(times[i].split(";")));
                    }

                    movies.add(new MovieVO(id, movieTitle, movieGenre, movieRating, minAge, schedule));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public void doAddMovie(MovieVO movie) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            pw.println(formatMovieToString(movie));
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doUpdateMovie(MovieVO doUpdatedMovie) {
        List<MovieVO> movies = getAllMovies();
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (MovieVO movie : movies) {
                if (movie.getId() == doUpdatedMovie.getId()) {
                    pw.println(formatMovieToString(doUpdatedMovie));
                } else {
                    pw.println(formatMovieToString(movie));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doDeleteMovie(int id) {
        List<MovieVO> movies = getAllMovies();
        List<MovieVO> doUpdatedMovies = new ArrayList<>();
        int newId = 1;
        for (MovieVO movie : movies) {
            if (movie.getId() != id) {
                movie.setId(newId++);
                doUpdatedMovies.add(movie);
            }
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (MovieVO movie : doUpdatedMovies) {
                pw.println(formatMovieToString(movie));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MovieVO getMovieId(int id) {
        List<MovieVO> movies = getAllMovies();
        for (MovieVO movie : movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }

    public List<MovieVO> getMoviesByRating(double minRating) {
        List<MovieVO> filteredMovies = new ArrayList<>();
        for (MovieVO movie : getAllMovies()) {
            if (movie.getMovieRating() >= minRating) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }

    private String formatMovieToString(MovieVO movie) {
        StringBuilder sb = new StringBuilder();
        sb.append(movie.getId()).append(", ")
                .append(movie.getMovieTitle()).append(", ")
                .append(movie.getMovieGenre()).append(", ")
                .append(movie.getMovieRating()).append(", ")
                .append(movie.getMinAge()).append(", ");

        List<String> days = new ArrayList<>(movie.getSchedule().keySet());
        sb.append(String.join("/", days)).append(", ");

        List<String> times = new ArrayList<>();
        for (String day : days) {
            times.add(String.join(";", movie.getSchedule().get(day)));
        }
        sb.append(String.join("/", times));

        return sb.toString();
    }
}
