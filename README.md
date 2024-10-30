# KB_FinalProject


# 📈 iNVeTI
- KB국민은행에서 주관한 KB IT's Your Life 교육과정에서 진행한 금융 웹프로젝트

### iNVeTI
: 투자 입문자들을 위한 통합된 금융 정보와 각 성향에 맞춘 투자 상품 제공

## 📆 개발 기간
2024.08.28 ~ 2024.10.16

## 👨‍👨‍👧‍👦 팀 구성
웹 프론트엔드 3, 웹 백엔드 3

## 📌 주요 기능
1. 개인 투자성향 분석 기반 투자상품 추천 서비스
2. 10개의 질문을 바탕으로 16가지 금융 mbti 분석
3. 성향 기반으로 예적금, 주식, 펀드, ISA 등 다양한 금융상품 맞춤 추천
4. 커뮤니티, 용어사전, 금, 환율에 대한 부가적인 정보 제공


## 🛠 기술 스택
![image](https://github.com/user-attachments/assets/cbe3a4b5-2c42-45e9-9e4b-4ce4c1c61df4)


## 💡 구현 설명
1. 국민은행 WMTI 기반으로 질문 구성
2. 한국투자증권 API를 사용하여 웹소켓 10개는 실시간 변동, 나머지는 DB에 저장 후 업데이트
3. FinanceDataReader 사용하여 5년치 주가 차트 제공 
4. 네이버 뉴스 API를 사용하여 각 금융 상품에 대한 뉴스 제공
5. 각 주가지수 크롤링
6. 금융감독원 API를 사용하여 예적금 정보제공
7. 예적금을 정렬할 때 동일한 투자 성향을 가진 사람들이 많이 조회한 순서대로 정렬
8. 환율 크롤링 데이터를 각 나라 별로 제공, 각 은행별로 환전수수료율 제공

## 📲 업데이트 예정
- [ ] 투자성향테스트 결과 페이지에 금융 상품 추가
- [ ] 주가 지수 차트 수정
- [ ] 네이버 뉴스 성향 별로 제공
- [ ] 펀드 크롤링 방식 수정
- [ ] 카카오 로그인 수정

## 📱 실행 화면
![image](https://github.com/user-attachments/assets/f0f06de1-08e4-466e-af7b-9f41bd5eb106)
![image](https://github.com/user-attachments/assets/859410f7-c5cd-47df-8f7f-565208b9ac0a)
![image](https://github.com/user-attachments/assets/fff40f9f-f34a-4bab-9106-e48c28df63f2)
![image](https://github.com/user-attachments/assets/65983cdd-cc81-4e70-b8e7-8137f8a60cce)

