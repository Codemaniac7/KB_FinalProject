<template>
  <section>
    <div class="newsBox">
      <div class="container">
        <br><br><br><br>
        <div class="headline">
          <h1>실시간 주요뉴스</h1>
          <button class="seeMore_btn" @click="seeMore">더보기 <i class="ai-chevron-right"></i></button>
        </div>
        <br>
        <div class="newsContent">
          <!-- 메인 뉴스 -->
          <div class="newsMain" v-if="mainNews">
            <b>{{ mainNews.title }}</b>
            <img :src="mainNews.image" @click="goToNews(mainNews.link)">
          </div>
          <!-- 사이드 뉴스 목록 -->
          <div class="sideNews">
            <table class="newsTable">
              <tbody>
                <tr v-for="newsItem in sideNews" :key="newsItem.link" @click="goToNews(newsItem.link)">
                  <td class="sideNewsTitle">{{ newsItem.title }}</td>
                  <td class="sideNewsDate">{{ newsItem.date }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <br><br><br><br>
    </div>
  </section>
</template>

<script setup>
import router from '@/router';
import axios from 'axios';
import { ref, onMounted } from 'vue';

const news = ref([]);
const mainNews = ref(null);
const sideNews = ref([]);

// 뉴스 API 호출
const fetchNews = async () => {
  try {
    const query = '금융투자';
    const response = await axios.get('/api/news', {
      params: { query: query, display: 10 }
    });

    if (response.data && response.data.items) {
      // 뉴스 항목 처리 => '금융투자' 키워드가 포함된 뉴스만 필터링
      const newsItems = response.data.items.map(processNewsItem);
      const filteredNews = filterByKeyword(newsItems, '금융');

      if (filteredNews.length > 0) {
        news.value = filteredNews;
        mainNews.value = news.value[0]; // 첫 번째 뉴스가 메인 뉴스
        sideNews.value = news.value.slice(1); // 나머지 뉴스가 사이드 뉴스
      } else {
        console.warn('금융투자 관련 뉴스를 찾지 못했습니다.');
      }
    } else {
      console.error('Unexpected API response:', response.data);
    }
  } catch (error) {
    console.error('뉴스 불러오기 오류:', error);
  }
};

// 뉴스 항목 처리
const processNewsItem = (newsData) => {
  return {
    title: decodeHtml(stripHtml(newsData.title)),
    description: decodeHtml(stripHtml(newsData.description)),
    date: new Date(newsData.pubDate).toLocaleDateString(),
    image: extractImageUrl(newsData.description) || '/img/news.jpg',
    link: newsData.link
  };
};

// HTML 태그 제거
const stripHtml = (text) => {
  return text.replace(/<[^>]+>/g, '');
};

// HTML 엔티티 디코딩
const decodeHtml = (text) => {
  const textArea = document.createElement('textarea');
  textArea.innerHTML = text;
  return textArea.value;
};

// 이미지 URL 추출
const extractImageUrl = (description) => {
  const imgRegex = /<img[^>]+src="?([^"\s]+)"?\s*\/?>/g;
  const match = imgRegex.exec(description);
  return match ? match[1] : null;
};

// 키워드로 뉴스 필터링
const filterByKeyword = (newsItems, keyword) => {
  return newsItems.filter(item => item.title.includes(keyword) || item.description.includes(keyword));
};

// 뉴스 링크로 이동
const goToNews = (link) => {
  window.open(link, '_blank');
};

const seeMore = () => {
  router.push('/news');
};

// 컴포넌트가 마운트될 때 API 호출
onMounted(fetchNews);
</script>


<style scoped>
.newsMain {
  position: relative;
  width: 900px;
  font-family: J5;
}

.newsMain b {
  z-index: 2;
  position: absolute;
  font-size: 40px;
  font-weight: 700;
  color: white;
  margin-top: 300px;
  max-width: 450px;
  margin-left: 15px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  text-overflow: ellipsis;
  white-space: normal;
}

.container{
  width: 1300px;
}
.newsBox {
  width: 100%;
  position: relative;
  background-color: rgba(249, 250, 251, 1);
  font-family: J2;
}

.newsBox h1 {
  padding: 0 80px;
  font-family: J5;
  font-size: 40px
}

.headline {
  display: flex;
  padding: 0 20px;
}

.headline button {
  margin-left: 40%
}

.newsContent {
  display: flex;
  padding: 0 20px;
}

.newsMain img {
  border-radius: 30px;
  width: 500px;
  height: 500px;
  transition: transform 0.3s ease;
}

.newsMain img:hover {
  transform: scale(1.05);
}

button.seeMore_btn {
  background: none;
  border: none;
  font-size: 20px;
  font-weight: bold;
  color: black;
  cursor: pointer;
  margin-left: 700px;
}

.sideNews {
  width: 90%;
  font-size: 30px;
  line-height: 2;
}

.sideNewsTitle {
  font-size: 25px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 550px;
  line-height: 2;
  padding-right: 10px;
  cursor: pointer;
}

.sideNewsTitle:hover {
  color: #438B73;
  font-weight: bold;
}

.sideNewsDate {
  font-size: 18px;
  color: gray;
  text-align: right;
}
</style>