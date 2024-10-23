package com.pcwk.ehr.book;

import com.pcwk.ehr.movie.MovieDao;

import java.util.Scanner;

public class BookingMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MovieDao movieDao = new MovieDao();
        BookingMovie book = new BookingMovie(movieDao);

        while (true){
            System.out.println("1. 영화 예매 2. 프로그램 종료");
            System.out.print("선택> ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    book.bookMovie(scanner);
                    break;

                case 2:
                    System.out.println("프로그램 종료 !");
                    scanner.close();
                    return;

                default:
                    System.out.println("올바른 번호를 선택해주세요 !");
            }
        }
    }
}
