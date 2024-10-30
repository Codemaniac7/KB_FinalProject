package com.kb.exchange.service;

import com.kb.common.util.WebDriverFactory;
import com.kb.exchange.dto.*;
import com.kb.exchange.dto.Currency;
import com.kb.exchange.mapper.ExchangeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;  // java.sql.Date 임포트
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

@Log4j
@Service
@RequiredArgsConstructor
@PropertySource({"classpath:/application.properties"})
public class ExchangeService {
    private static final String API_URL = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON";
    @Value("${EXCHANGE_API_KEY}") String API_KEY;
    @Value("${EXCHANGE_DATA_TYPE}") String DATA_TYPE;
    private final ExchangeMapper mapper;

    @Transactional
    public void fetchAndSaveExchangeRates() {
        RestTemplate restTemplate = new RestTemplate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        List<Exchange> list = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        for (int i = 0; i < 365; i++) {
            String searchDate = dateFormat.format(calendar.getTime());

            String apiUrl = API_URL + "?authkey=" + API_KEY + "&searchdate=" + searchDate + "&data=" + DATA_TYPE;
            ExchangeApiResponse[] responses = restTemplate.getForObject(apiUrl, ExchangeApiResponse[].class);

            if (responses != null && responses.length > 0) {
                for (ExchangeApiResponse response : responses) {
                    String curUnit = response.getCurUnit();
                    if(curUnit != null){
                        Exchange exchange = new Exchange();
                        exchange.setCurrencyId(mapper.getCurrenyIdByCurUnit(curUnit));
                        exchange.setReceivingPrice(response.getTtb());
                        exchange.setSendingPrice(response.getTts());
                        exchange.setBasePrice(response.getDealBasR());
                        exchange.setExchangeRateDate(new Date(calendar.getTimeInMillis()));
                        list.add(exchange);
                    }
                    else {
                        log.warn("일치하는 환율 없음");
                    }
                }
            } else {
                log.warn("데이터 없음");
            }
            calendar.add(Calendar.DATE, -1);
        }
        mapper.insertExchangeRate(list);
    }

    @Scheduled(cron = "0 0 12 * * *")
    @Transactional
    public void fetchAndSaveExchangeRatesDaily() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            return;
        }

        if (dayOfWeek == Calendar.MONDAY) {
            for (int i = 0; i < 3; i++) {
                fetchAndSaveExchangeRatesForSpecificDay(calendar);
                calendar.add(Calendar.DATE, -1);
            }
        } else {
            fetchAndSaveExchangeRatesForSpecificDay(calendar);
        }
    }

    public void fetchAndSaveExchangeRatesForSpecificDay(Calendar calendar) {
        RestTemplate restTemplate = new RestTemplate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String searchDate = dateFormat.format(calendar.getTime());
        List<Exchange> list = new ArrayList<>();

        String apiUrl = API_URL + "?authkey=" + API_KEY + "&searchdate=" + searchDate + "&data=" + DATA_TYPE;

        try {
            ExchangeApiResponse[] responses = restTemplate.getForObject(apiUrl, ExchangeApiResponse[].class);

            if (responses != null && responses.length > 0) {
                for (ExchangeApiResponse response : responses) {
                    String curUnit = response.getCurUnit();
                    if (curUnit != null) {
                        Exchange exchange = new Exchange();
                        exchange.setCurrencyId(mapper.getCurrenyIdByCurUnit(curUnit));
                        exchange.setReceivingPrice(response.getTtb());
                        exchange.setSendingPrice(response.getTts());
                        exchange.setBasePrice(response.getDealBasR());
                        exchange.setExchangeRateDate(new Date(calendar.getTimeInMillis()));
                        list.add(exchange);
                    } else {
                        log.warn("일치하는 환율 없음");
                    }
                }
            } else {
                log.warn("응답 데이터가 없음: 날짜");
            }

        } catch (RestClientException e) {
            log.error("환율 API 호출 중 오류 발생 : " + e.getMessage());

        } catch (Exception e) {
            log.error("오류 발생 : " + e.getMessage());
        }
        mapper.insertExchangeRate(list);
    }

    public ExchangeDailyDTO getDailyExchange(int currencyId){
        return mapper.getDailyExchange(currencyId);
    }

    public List<ExchangeListByTerm> getExchangeListByTerm(ExchangeParam exchangeParam){
        return mapper.getExchangeDetailByTerm(exchangeParam);
    }

    public List<ExchangeFee> getExchangeFeeList(int currenyId){
        return mapper.getExchangeFeeByCurrency(currenyId);
    }

    @Transactional
    public void crawlExchangeFee() {
        WebDriver driver = WebDriverFactory.createWebDriver();
        List<ExchangeFee> updateList = new ArrayList<>();
        List<ExchangeFee> saveList = new ArrayList<>();

        try {
            driver.get("https://portal.kfb.or.kr/compare/commission_spread.php");

            List<WebElement> liElements = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#Content > div.contentArea > form > div > div.bankSel.mt25 ul li")));

            for (WebElement liElement : liElements) {
                try {
                    WebElement radioButton = liElement.findElement(By.tagName("input"));
                    radioButton.click();

                    WebElement searchButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                            .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#Content > div.contentArea > form > div > div.btnArea > span > a")));
                    searchButton.click();

                    Thread.sleep(2000); // 페이지 로드 대기

                    WebElement element = driver.findElement(By.cssSelector("#SearchResult > div.Resultitle.mt40 > ul > li.leftArea > span.title"));
                    String fullText = element.getText();
                    String bracketText = fullText.substring(fullText.indexOf("(") + 1, fullText.indexOf(")"));

                    // Currency code (앞 3글자만 추출)
                    String currencyCode = bracketText.length() > 3 ? bracketText.substring(0, 3) : bracketText;
                    log.info(currencyCode);

                    if (mapper.checkCurrencyExists(currencyCode) == 0) {
                        log.warn("해당 통화 존재하지 않음: " + currencyCode);
                        continue;
                    }

                    List<WebElement> rows = driver.findElements(By.cssSelector("#SearchResult > div:nth-child(3) > table > tbody > tr"));
                    for (WebElement row : rows) {
                        List<WebElement> cells = row.findElements(By.tagName("td"));

                        try {
                            ExchangeFee exchangeFee = new ExchangeFee();
                            exchangeFee.setCurrencyId(CurrencyBankMapping.getCurrencyId(currencyCode));
                            exchangeFee.setBankId(CurrencyBankMapping.getBankId(cells.get(0).getText().trim()));
                            exchangeFee.setBuyingFee(Double.parseDouble(cells.get(1).getText().trim()));
                            exchangeFee.setSellingFee(Double.parseDouble(cells.get(2).getText().trim()));
                            exchangeFee.setBaseDate(Date.valueOf(cells.get(3).getText().trim()));
                            if (mapper.checkExistExchangeFee(exchangeFee) > 0) {
                                if (mapper.checkSameExchangeFee(exchangeFee) == 0) {
                                    updateList.add(exchangeFee);
                                }
                            }
                            else {
                                saveList.add(exchangeFee);
                            }
                        } catch (Exception e) {
                            log.error("데이터 파싱 중 오류 발생: " + e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    log.error("요소 처리 중 오류 발생: " + e.getMessage());
                }
            }
            if(!updateList.isEmpty())
                mapper.batchUpdateExchangeFees(updateList);
            if(!saveList.isEmpty())
                mapper.batchInsertExchangeFees(saveList);

        } catch (Exception e) {
            log.error("전체 프로세스에서 오류 발생: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }

    public List<Currency> getExchangeBankCategory(){
        return mapper.getExchangeBankCategory();
    }

    public List<Currency> getCurrencyCategory(){
        return mapper.getCurrencyCategory();
    }

    public ExchangeCategory getCategory(){
        return new ExchangeCategory(getCurrencyCategory(), getExchangeBankCategory());
    }
}
