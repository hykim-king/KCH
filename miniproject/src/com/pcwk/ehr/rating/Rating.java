package com.pcwk.ehr.rating;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pcwk.ehr.admin.MovieDao;
import com.pcwk.ehr.admin.MovieVO;

public class Rating {

    public List<MovieVO> rating() {
        MovieDao movieDao = new MovieDao();
        ArrayList<MovieVO> allMovies = movieDao.doSelectAll();
        
        // 영화 제목과 평점을 저장할 HashMap
        Map<String, Double> movieRatings = new HashMap<>();
        
        // 중복된 제목을 가진 영화는 마지막으로 추가된 평점만 남긴다.
        for (MovieVO movie : allMovies) {
            movieRatings.put(movie.getTitle(), movie.getRating());
        }

        // 영화 제목과 평점 리스트 생성
        List<Map.Entry<String, Double>> movieRatingList = new ArrayList<>(movieRatings.entrySet());
        
        // 평점 기준으로 정렬 (내림차순)
        Collections.sort(movieRatingList, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        System.out.println("\n************ 평점순으로 영화 목록 나열 ************\n");

        // 정렬된 데이터를 출력
        for (int i = 0; i < movieRatingList.size(); i++) {
            Map.Entry<String, Double> entry = movieRatingList.get(i);
            System.out.printf("%d. %s의 평점은 %.1f 입니다.\n", (i + 1), entry.getKey(), entry.getValue());
        }

        return allMovies;
    }
}
