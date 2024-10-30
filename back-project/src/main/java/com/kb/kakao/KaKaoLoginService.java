package com.kb.kakao;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@PropertySource({"classpath:/application.properties"})
public class KaKaoLoginService {

    // kakao API 토큰 - REST API 키
    @Value("${KAKAO_REST_API_KEY}") String KAKAO_REST_API_KEY;

    public String getToken(String code, String redirectUrl) {
        // 인가코드로 토큰받기
        String token = "";
        try {
            String host = "https://kauth.kakao.com/oauth/token";
            URL url = new URL(host);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true); // 데이터 기록 알려주기

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            String body = "grant_type=authorization_code" +
                    "&client_id=" + KAKAO_REST_API_KEY +
                    "&redirect_uri=" + redirectUrl +
                    "&code=" + code;
            System.out.println(body);
            bw.write(body);
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            // json parsing
            JSONParser parser = new JSONParser();
            JSONObject elem = (JSONObject) parser.parse(result.toString());

            String access_token = elem.get("access_token").toString();
            String refresh_token = elem.get("refresh_token").toString();
            token = access_token;
            br.close();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }


    public Map<String, Object> getUserInfo(String access_token)  {
        String host = "https://kapi.kakao.com/v2/user/me";
        Map<String, Object> map = new HashMap<>();
        try {
            URL url = new URL(host);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);
            urlConnection.setRequestMethod("GET");

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            System.out.println("res = " + sb.toString());
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(sb.toString());
            JSONObject properties = (JSONObject) obj.get("properties");
            JSONObject kakaoAccount = (JSONObject) obj.get("kakao_account");

            String id = obj.get("id").toString();
            String nickname = properties.get("nickname").toString();
            String email = kakaoAccount.get("email").toString();

            map.put("id", id);
            map.put("nickname", nickname);
            map.put("email", email);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public String getAgreementInfo(String access_token) {
        StringBuilder result = new StringBuilder();
        String host = "https://kapi.kakao.com/v2/user/scopes";
        try {
            URL url = new URL(host);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close();

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);
            if(responseCode != 200){
                return null;
            }
            // result is json format

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}