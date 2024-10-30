package com.kb.financialTerms.service;

import com.kb.financialTerms.dto.TermsDTO;
import com.kb.financialTerms.mapper.TermsMapper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FinancialTermsService {

    private final TermsMapper termsMapper;

    @Transactional
    public void crawlAndSaveTerms() {
        List<TermsDTO> termsList = new ArrayList<>();
        String baseUrl = "https://www.fsc.go.kr/in090301?curPage=";
        List<CompletableFuture<List<TermsDTO>>> futures = new ArrayList<>();

        for (int page = 1; ; page++) {
            final int currentPage = page;
            CompletableFuture<List<TermsDTO>> future = CompletableFuture.supplyAsync(() -> {
                List<TermsDTO> pageTermsList = new ArrayList<>();
                String url = baseUrl + currentPage;

                try {
                    Document doc = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0")
                            .get();

                    Elements termElements = doc.select("div.cont > div.subject");

                    if (termElements.isEmpty()) {
                        return pageTermsList;
                    }

                    for (Element termElement : termElements) {
                        // 제목 가져오기
                        Element linkElement = termElement.select("a").first();
                        String termName = linkElement != null ? linkElement.text() : null;

                        // 형제 요소에서 내용 가져오기
                        Element descriptionElement = termElement.nextElementSibling();
                        String termDescription = descriptionElement != null && "info2".equals(descriptionElement.className()) ?
                                descriptionElement.text() : null;

                        // TermsDTO 객체 생성
                        TermsDTO termsDTO = new TermsDTO();
                        termsDTO.setTermName(termName);
                        termsDTO.setTermDescription(termDescription);

                        // 리스트에 추가
                        pageTermsList.add(termsDTO);
                    }

                    return pageTermsList;
                } catch (Exception e) {
                    e.printStackTrace();
                    return pageTermsList;
                }
            });

            futures.add(future);

            if (page >= 30) {
                break;
            }
        }

        for (CompletableFuture<List<TermsDTO>> future : futures) {
            List<TermsDTO> pageTermsList = future.join();
            termsList.addAll(pageTermsList);
        }
        // DB에 저장
        termsMapper.insertTermsBatch(termsList);
    }

    public List<TermsDTO> getTermList(){
        return termsMapper.getTermsBatch();
    }
}
