package com.kb.news.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@Api(tags = "뉴스 API", description = "네이버 뉴스 검색 API를 이용한 뉴스 조회")
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081"})
    @GetMapping("/api/news")
    @ApiOperation(value = "뉴스 검색", notes = "주어진 쿼리로 뉴스를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 뉴스를 조회함"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 내부 오류")
    })
    public ResponseEntity<String> getNews(
            @ApiParam(value = "검색할 뉴스 키워드", example = "금융", defaultValue = "금융")
            @RequestParam(value = "query", required = false, defaultValue = "금융") String query,
            @ApiParam(value = "표시할 뉴스 개수", example = "10", defaultValue = "10")
            @RequestParam(required = false, defaultValue = "10") int display,
            @ApiParam(value = "정렬 기준", example = "sim", defaultValue = "sim")
            @RequestParam(required = false, defaultValue = "sim") String sort) { // 추가된 sort 파라미터

        logger.info("Received request for news. Query: {}, Display: {}, Sort: {}", query, display, sort);

        // 쿼리 인코딩
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + encodedQuery +
                "&display=" + display + "&sort=" + sort; // sort 파라미터 추가

        // RestTemplate 사용
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        // HTTP 요청 엔티티 생성
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // URI 생성
            URI uri = new URI(apiURL);
            // API 호출
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            logger.info("API request successful. Status: {}", response.getStatusCode());
            return response;
        } catch (URISyntaxException e) {
            logger.error("URI Syntax error", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error occurred while fetching news", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 추가: Jsoup을 사용해 뉴스 페이지에서 이미지 추출
    @GetMapping("/api/news/image")
    @ApiOperation(value = "뉴스 이미지 가져오기", notes = "뉴스 링크에서 미리보기 이미지를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 이미지를 가져옴"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 내부 오류")
    })
    public ResponseEntity<byte[]> getNewsImage(
            @ApiParam(value = "뉴스 링크", example = "https://news.naver.com")
            @RequestParam(value = "newsUrl") String newsUrl) {

        logger.info("Received request for news image. News URL: {}", newsUrl);

        try {
            // URL 디코딩
            logger.info("Received raw News URL: {}", newsUrl);
            String decodedUrl = URLDecoder.decode(newsUrl, "UTF-8");
            logger.info("Decoded News URL: {}", decodedUrl);

            // URL 검증 (http 또는 https 포함 여부 확인)
            if (!decodedUrl.startsWith("http://") && !decodedUrl.startsWith("https://")) {
                logger.error("Invalid URL format: {}", decodedUrl);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Jsoup을 사용하여 뉴스 페이지를 로드
            Document doc = Jsoup.connect(decodedUrl).followRedirects(true).get();

            // meta 태그에서 og:image 속성으로 이미지 추출 (Open Graph Protocol)
            Element metaImage = doc.selectFirst("meta[property=og:image]");
            if (metaImage == null) {
                metaImage = doc.selectFirst("meta[name=image]");
            }
            String imageUrl = metaImage != null ? metaImage.attr("content") : "";


            if (imageUrl.isEmpty()) {
                logger.warn("No image found for URL: {}", decodedUrl);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            logger.info("Extracted image URL: {}", imageUrl);

            // RestTemplate을 사용하여 이미지 데이터를 가져옴
            RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

            byte[] imageBytes = restTemplate.getForObject(imageUrl, byte[].class);

            // 이미지 파일 타입 확인
            String imageType = imageUrl.endsWith(".png") ? MediaType.IMAGE_PNG_VALUE : MediaType.IMAGE_JPEG_VALUE;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(imageType));

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            logger.error("Error while connecting to URL: {}", newsUrl, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching news image", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
