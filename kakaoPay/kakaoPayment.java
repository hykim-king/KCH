package com.pcwk.ehr.kakaoPay;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class kakaoPayment {

    private static final String ADMIN_KEY = "b4b2c04ae7e82f90dd52b235a41d8c24";  // 여러분의 카카오페이 Admin Key

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("결제할 금액을 입력하세요: ");
        String amount = scanner.next();

        try {
            String readyResponse = preparePayment(amount);

            // JSON 응답 파싱
            JsonObject jsonResponse = JsonParser.parseString(readyResponse).getAsJsonObject();
            String redirectUrl = jsonResponse.get("next_redirect_pc_url").getAsString();

            System.out.println("결제 페이지로 이동하세요: " + redirectUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String preparePayment(String amount) throws Exception {
        // 카카오페이 결제 준비 API URL
        String apiUrl = "https://kapi.kakao.com/v1/payment/ready";

        // 결제 요청 파라미터 설정
        String params = "cid=TC0ONETIME" + // 테스트용 CID
                "&partner_order_id=1001" +
                "&partner_user_id=test_user" +
                "&item_name=테스트상품" +
                "&quantity=1" +
                "&total_amount=" + amount +
                "&tax_free_amount=0" +
                "&approval_url=https://a459-118-222-74-115.ngrok-free.app/success" + // 테스트용 URL
                "&cancel_url=https://a459-118-222-74-115.ngrok-free.app/cancel" +    // 테스트용 URL
                "&fail_url=https://a459-118-222-74-115.ngrok-free.app/fail";         // 테스트용 URL

        // URL 연결 설정
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "KakaoAK " + ADMIN_KEY);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        conn.setDoOutput(true);

        // 요청 파라미터 전송
        OutputStream os = conn.getOutputStream();
        os.write(params.getBytes("UTF-8"));
        os.flush();
        os.close();

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // 응답 받기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            br.close();
            return response.toString();
        } else {
            // 오류 응답 받기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            br.close();
            throw new IOException("HTTP error code : " + responseCode + " " + response.toString());
        }
    }
}
