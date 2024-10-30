package com.kb.exchange.service;

import java.util.HashMap;
import java.util.Map;

public class CurrencyBankMapping {

    private static final Map<String, Integer> currencyIdMap = new HashMap<>();
    private static final Map<String, String> bankIdMap = new HashMap<>();

    static {
        currencyIdMap.put("AED", 1);
        currencyIdMap.put("AUD", 2);
        currencyIdMap.put("BHD", 3);
        currencyIdMap.put("BND", 4);
        currencyIdMap.put("CAD", 5);
        currencyIdMap.put("CHF", 6);
        currencyIdMap.put("CNH", 7);
        currencyIdMap.put("DKK", 8);
        currencyIdMap.put("EUR", 9);
        currencyIdMap.put("GBP", 10);
        currencyIdMap.put("HKD", 11);
        currencyIdMap.put("IDR", 12);
        currencyIdMap.put("JPY", 13);
        currencyIdMap.put("KRW", 14);
        currencyIdMap.put("KWD", 15);
        currencyIdMap.put("MYR", 16);
        currencyIdMap.put("NOK", 17);
        currencyIdMap.put("NZD", 18);
        currencyIdMap.put("SAR", 19);
        currencyIdMap.put("SEK", 20);
        currencyIdMap.put("SGD", 21);
        currencyIdMap.put("THB", 22);
        currencyIdMap.put("USD", 23);

        bankIdMap.put("우리은행", "0010001");
        bankIdMap.put("SC제일은행", "0010002");
        bankIdMap.put("iM뱅크(구 대구은행)", "0010016");
        bankIdMap.put("BNK부산은행", "0010017");
        bankIdMap.put("광주은행", "0010019");
        bankIdMap.put("제주은행", "0010020");
        bankIdMap.put("전북은행", "0010022");
        bankIdMap.put("BNK경남은행", "0010024");
        bankIdMap.put("IBK기업은행", "0010026");
        bankIdMap.put("KDB산업은행", "0010030");
        bankIdMap.put("애큐온저축은행", "0010345");
        bankIdMap.put("오에스비저축은행", "0010346");
        bankIdMap.put("디비저축은행", "0010349");
        bankIdMap.put("스카이저축은행", "0010350");
        bankIdMap.put("민국저축은행", "0010354");
        bankIdMap.put("푸른상호저축은행", "0010356");
        bankIdMap.put("HB저축은행", "0010358");
        bankIdMap.put("키움예스저축은행", "0010359");
        bankIdMap.put("더케이저축은행", "0010363");
        bankIdMap.put("조은저축은행", "0010366");
        bankIdMap.put("에스비아이저축은행", "0010370");
        bankIdMap.put("바로저축은행", "0010378");
        bankIdMap.put("다올저축은행", "0010388");
        bankIdMap.put("유안타저축은행", "0010389");
        bankIdMap.put("고려저축은행", "0010390");
        bankIdMap.put("국제저축은행", "0010391");
        bankIdMap.put("디에이치저축은행", "0010404");
        bankIdMap.put("흥국저축은행", "0010416");
        bankIdMap.put("우리저축은행", "0010418");
        bankIdMap.put("인성저축은행", "0010419");
        bankIdMap.put("금화저축은행", "0010421");
        bankIdMap.put("인천저축은행", "0010426");
        bankIdMap.put("모아저축은행", "0010430");
        bankIdMap.put("대백저축은행", "0010437");
        bankIdMap.put("유니온상호저축은행", "0010438");
        bankIdMap.put("엠에스상호저축은행", "0010439");
        bankIdMap.put("안국저축은행", "0010448");
        bankIdMap.put("남양저축은행", "0010449");
        bankIdMap.put("부림저축은행", "0010453");
        bankIdMap.put("키움저축은행", "0010456");
        bankIdMap.put("삼정저축은행", "0010457");
        bankIdMap.put("평택저축은행", "0010460");
        bankIdMap.put("안양저축은행", "0010463");
        bankIdMap.put("영진저축은행", "0010464");
        bankIdMap.put("융창저축은행", "0010467");
        bankIdMap.put("세람상호저축은행", "0010468");
        bankIdMap.put("페퍼저축은행", "0010471");
        bankIdMap.put("상상인저축은행", "0010473");
        bankIdMap.put("한화저축은행", "0010477");
        bankIdMap.put("CK저축은행", "0010478");
        bankIdMap.put("대명상호저축은행", "0010485");
        bankIdMap.put("우리금융저축은행", "0010488");
        bankIdMap.put("청주저축은행", "0010489");
        bankIdMap.put("한성저축은행", "0010492");
        bankIdMap.put("상상인플러스저축은행", "0010508");
        bankIdMap.put("아산저축은행", "0010509");
        bankIdMap.put("오투저축은행", "0010510");
        bankIdMap.put("스타저축은행", "0010521");
        bankIdMap.put("대한저축은행", "0010526");
        bankIdMap.put("동양저축은행", "0010527");
        bankIdMap.put("더블저축은행", "0010528");
        bankIdMap.put("센트럴저축은행", "0010533");
        bankIdMap.put("스마트저축은행", "0010534");
        bankIdMap.put("한국투자저축은행", "0010537");
        bankIdMap.put("라온저축은행", "0010550");
        bankIdMap.put("드림저축은행", "0010551");
        bankIdMap.put("대아상호저축은행", "0010553");
        bankIdMap.put("머스트삼일저축은행", "0010556");
        bankIdMap.put("참저축은행", "0010560");
        bankIdMap.put("오성저축은행", "0010562");
        bankIdMap.put("대원상호저축은행", "0010568");
        bankIdMap.put("에스앤티저축은행", "0010569");
        bankIdMap.put("솔브레인저축은행", "0010572");
        bankIdMap.put("동원제일저축은행", "0010574");
        bankIdMap.put("조흥저축은행", "0010575");
        bankIdMap.put("진주저축은행", "0010576");
        bankIdMap.put("KB국민은행", "0010927");
        bankIdMap.put("예가람저축은행", "0011551");
        bankIdMap.put("신한은행", "0011625");
        bankIdMap.put("제이티저축은행", "0011767");
        bankIdMap.put("삼호저축은행", "0012120");
        bankIdMap.put("엔에이치저축은행", "0012711");
        bankIdMap.put("대신저축은행", "0012840");
        bankIdMap.put("아이비케이저축은행", "0012889");
        bankIdMap.put("비엔케이저축은행", "0013002");
        bankIdMap.put("케이비저축은행", "0013127");
        bankIdMap.put("하나저축은행", "0013166");
        bankIdMap.put("NH농협은행", "0013175");
        bankIdMap.put("제이티친애저축은행", "0013308");
        bankIdMap.put("신한저축은행", "0013313");
        bankIdMap.put("웰컴저축은행", "0013350");
        bankIdMap.put("오케이저축은행", "0013351");
        bankIdMap.put("하나은행", "0013909");
        bankIdMap.put("주식회사 케이뱅크", "0014674");
        bankIdMap.put("Sh수협은행", "0014807");
        bankIdMap.put("주식회사 카카오뱅크", "0014985");
        bankIdMap.put("한국씨티은행", "1111111");
    }

    public static int getCurrencyId(String currencyCode) {
        return currencyIdMap.getOrDefault(currencyCode, -1);
    }

    public static String getBankId(String bankName) {
        return bankIdMap.getOrDefault(bankName, "Unknown");
    }
}
