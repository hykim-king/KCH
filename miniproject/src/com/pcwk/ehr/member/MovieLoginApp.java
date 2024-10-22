package com.pcwk.ehr.member;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import com.pcwk.ehr.admin.MovieDao;
import com.pcwk.ehr.admin.MovieDiv;
import com.pcwk.ehr.admin.MovieVO;

public class MovieLoginApp {
    // 유저와 관리자 아이디/패스워드 정보
    private static final String admId = "rkdcofls";
    private static final String admPw = "1030";
    private static final String userId01 = "yonjin";
    private static final String userPw01 = "1004";
    private static final String userId02 = "tjdals";
    private static final String userPw02 = "1103";

    public static void main(String[] args) {
        MovieDao movieDao = new MovieDao();
        ArrayList<MovieVO> allMovies = movieDao.doSelectAll();
        ArrayList<Movie> movieList2 = new ArrayList<>(); // movieList2 초기화

        Scanner scanner = new Scanner(System.in);
        boolean isLoggedIn = false;
        boolean isAdmin = false;

        while (!isLoggedIn) {
            System.out.println("아이디 입력:");
            String inputId = scanner.nextLine();

            // 관리자와 사용자 아이디 비교
            if (inputId.equals(userId01) || inputId.equals(admId) || inputId.equals(userId02)) {
                // 아이디가 일치하면 패스워드 입력으로 이동
                while (true) {
                    System.out.println("패스워드 입력: ");
                    String inputPw = scanner.nextLine();

                    // 패스워드 비교
                    if ((inputId.equals(userId01) && inputPw.equals(userPw01))
                            || (inputId.equals(admId) && inputPw.equals(admPw))
                            || (inputId.equals(userId02) && inputPw.equals(userPw02))) {

                        if (inputId.equals(admId)) {
                            System.out.println("관리자로 로그인 하셨습니다.");
                            isAdmin = true;
                        } else {
                            System.out.println("사용자로 로그인 하셨습니다.");
                        }
                        isLoggedIn = true;
                        break; // 패스워드가 맞으면 로그인 성공 후 반복 탈출
                    } else {
                        System.out.println("패스워드를 잘못 입력하셨습니다.");
                    }
                }
            } else {
                // 아이디가 일치하지 않으면 다시 입력
                System.out.println("아이디를 잘못 입력하셨습니다.");
            }
        }

        // 로그인 성공 후 메인 메뉴
        if (isAdmin) {
            adminMenu(scanner);
        } else {
            userMenu(scanner, movieList2); // movieList2를 전달
        }

        // 로그아웃 로직
        System.out.println("로그아웃 되었습니다.");
        scanner.close();
    }

    // 관리자 메인 메뉴
    private static void adminMenu(Scanner scanner) {
        System.out.println("\n===관리자 메인 메뉴===");
        System.out.println("1. 영화 목록 관리");
        System.out.println("2. 예매 관리");
        System.out.println("3. 로그아웃");
        System.out.println("선택: ");
        String choice = scanner.nextLine();

        if (choice.equals("3")) {
            System.out.println("관리자 로그아웃 중...");
        }
    }

    // 유저 메인 메뉴
    private static void userMenu(Scanner scanner, ArrayList<Movie> movieList2) { // movieList2를 매개변수로 받음
        System.out.println("\n===유저 메인 메뉴===");
        System.out.println("1. 영화 목록 조회");
        System.out.println("2. 로그아웃");
        System.out.println("선택: ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            try {
                loadMovies(movieList2); // movieList2에 영화 목록 로드
                displayMovies(movieList2);
            } catch (FileNotFoundException e) {
                System.out.println("영화 목록 파일을 찾을 수 없습니다.");
            }
        }
        if (choice.equals("2")) {
            System.out.println("유저 로그아웃 중...");
        }
    }

    // 영화 목록을 파일에서 읽어오기
    public static void loadMovies(ArrayList<Movie> movieList) throws FileNotFoundException {
        // CSV 파일에서 영화 목록 읽어옴
        String path = "D:\\JAP_20240909\\01_JAVA\\workspace\\miniproject\\movieList2.csv";
        File file = new File(path);
        Scanner fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] data = line.split(",");

            // 각 줄의 데이터를 출력하여 확인
            System.out.println("읽은 줄: " + line);

            try {
                String title = data[0].trim();
                String genre = data[1].trim();
                double rating = Double.parseDouble(data[2].trim());

                Movie movie = new Movie(title, genre, rating);
                movieList.add(movie);
            } catch (NumberFormatException e) {
                System.out.println("상영 등급 오류: " + data[2].trim()); // 오류 발생한 데이터 출력
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("잘못된 데이터 형식: " + line);
            }
        }
        fileScanner.close();
    }

    // 영화 목록 출력
    private static void displayMovies(ArrayList<Movie> movieList) {
        System.out.println("\n=== 영화 목록 ===");
        for (Movie movie : movieList) {
            System.out.println("제목: " + movie.getTitle() + ", 장르: " + movie.getGenre()
                    + ", 평점: " + movie.getRating());
        }
    }
}
