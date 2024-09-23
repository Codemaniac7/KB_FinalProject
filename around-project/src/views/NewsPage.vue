<template>
    <div class="news-view">
        <!-- 메인 뉴스 -->
        <div class="main-news" v-if="mainNews">
            <img :src="mainNews.image" alt="Main News Image" class="main-news-image" />
            <p class="main-news-title">{{ mainNews.title }}</p>
            <p class="main-news-date">{{ mainNews.date }}</p>
        </div>

        <!-- 주요 뉴스 카드 -->
        <div class="news-cards" v-if="newsList.length">
            <div v-for="news in newsList" :key="news.id" class="news-card" @click="goToNews(news.link)">
                <img :src="news.image" alt="News Image" class="news-image" />
                <p class="news-title">{{ news.title }}</p>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            mainNews: {
                title: "24년 9월 글로벌 매크로 및 자산시장",
                date: "2024.08.30",
                image: require("@/assets/img/news/main-news.jpg"),
            },  // mainNews를 객체로 설정
            newsList: [
                {
                    id: 1,
                    title: "글로벌 시장 동향 및 전망",
                    image: require("@/assets/img/news/news1.jpg"),
                    link: "/news/1"
                },
                {
                    id: 2,
                    title: "미국 금리 인상 예측",
                    image: require("@/assets/img/news/news2.jpg"),
                    link: "/news/2"
                },
                {
                    id: 3,
                    title: "중국 경제 회복 신호",
                    image: require("@/assets/img/news/news3.jpg"),
                    link: "/news/3"
                },
                {
                    id: 4,
                    title: "기후 변화가 미치는 경제적 영향",
                    image: require("@/assets/img/news/news4.jpg"),
                    link: "/news/4"
                },
            ]  // newsList의 초기값을 예시로 설정
        };
    },

    methods: {
        fetchNewsData() {
            axios.get('https://api.example.com/news') // 실제 API 엔드포인트로 변경해야 함
                .then(response => {
                    const newsData = response.data;
                    this.mainNews = newsData.mainNews;    // 메인 뉴스 데이터
                    this.newsList = newsData.newsList;    // 뉴스 리스트 데이터
                })
                .catch(error => {
                    console.error('뉴스 데이터를 가져오는 데 실패했습니다:', error);
                });
        },
        goToNews(link) {
            this.$router.push(link); // 라우팅을 통해 페이지 이동
        },
    },
    mounted() {
        this.fetchNewsData();  // 컴포넌트가 마운트될 때 API 호출
    },
};
</script>

<style scoped>
.news-view {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.main-news {
    width: 100%;
    text-align: center;
    margin-bottom: 20px;
}

.main-news-image {
    width: 100%;
    max-width: 700px;
}

.main-news-title {
    font-size: 24px;
    font-weight: bold;
}

.main-news-date {
    font-size: 14px;
    color: gray;
}

.news-cards {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 20px;
}

.news-card {
    cursor: pointer;
    text-align: center;
}

.news-image {
    width: 100%;
    height: auto;
}

.news-title {
    margin-top: 10px;
    font-size: 16px;
}
</style>