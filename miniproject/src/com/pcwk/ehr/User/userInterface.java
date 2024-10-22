package com.pcwk.ehr.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.pcwk.ehr.admin.MovieDaoMain;
import com.pcwk.ehr.genre.GenreMain;
import com.pcwk.ehr.rating.RatingMain;

public class userInterface {
    public void showMenu() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean running = true;

        while (running) {
            try {
                System.out.println("\n******영화 예매 시스템******");
                System.out.println("\n로그인을 먼저 해주세요!\n");
                System.out.println("1. 영화 목록 조회");
                System.out.println("2. 장르별 영화 목록 조회");
                System.out.println("3. 평점별 영화 목록 조회");
                System.out.println("4. 프로그램 종료");
                System.out.print("메뉴를 선택하세요: \n");

                int choice = Integer.parseInt(reader.readLine());

                switch (choice) {
                    case 1:
                        MovieDaoMain.showAllMovies();
                        break;

                    case 2:
                        GenreMain genreMain = new GenreMain();
                        genreMain.showMenu();
                        break;

                    case 3:
                        RatingMain ratingMain = new RatingMain();
                        ratingMain.showMenu();
                        break;

                    case 4:
                        System.out.println("프로그램을 종료합니다.");
                        running = false;
                        break;

                    default:
                        System.out.println("잘못된 선택입니다. 다시 시도하세요.");
                }

            } catch (IOException | NumberFormatException e) {
                System.out.println("입력 오류: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        userInterface ui = new userInterface();
        ui.showMenu();
    }
}
