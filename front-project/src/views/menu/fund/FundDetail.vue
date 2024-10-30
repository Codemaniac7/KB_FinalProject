<script setup>
import { useFundStore } from '@/stores/fundStore';

const fundStore = useFundStore();
const fundDetails = fundStore.fundDetails;
</script>

<template>
  <div class="bc">
    <br />
    <div class="container text-start">
      <div class="view-kv" style="background-color: rgb(0, 60, 220)">
        <div class="left" style="margin-left: 130px">
          <h2
            class="headline_64-42_b"
            style="
              font-size: 50px;
              font-weight: 600;
              color: white;
              margin-top: -10px;
              margin-bottom: 0;
              font-family: 'NB';
            "
          >
            한국형 TDF
          </h2>
          <span
            class="title_22-15_m"
            style="
              font-size: 17px;
              font-weight: 600;
              color: white;
              margin-left: 5px; /* 오른쪽으로 이동 */
              top: 10px; /* 아래로 이동 */
              position: relative; /* 위치 조정 가능 */
              font-family: 'NL';
            "
          >
            은퇴시기를 고려하여 생애주기에 맞는
          </span>
        </div>

        <div
          class="right"
          style="margin-right: 60px; margin-top: -20px; margin-left: -10px"
        >
          <!-- 위와 왼쪽으로 이동 -->
          <div
            class="img-wrap"
            style="width: 300px; height: 300px; overflow: hidden"
          >
            <img
              :src="require('@/assets/img/funddetail.png')"
              alt="펀드 이미지"
              style="width: 110%; height: 107%"
            />
          </div>
        </div>
      </div>
      <br />

      <div
        class="trend__chart chart --s"
        style="
          opacity: 1;
          transform: translateY(0) translateZ(0);
          margin-top: 130px;
        "
      >
        <h3
          class="detail__tit headline_48-35"
          style="
            font-size: 35px;
            text-align: left;
            margin-top: 20px;
            margin-left: -20px;
            font-family: 'NB';
          "
        >
          한국형 TDF 대표 펀드 수익률
        </h3>
        <p
          class="detail__sub-tit body_20-15"
          style="
            font-size: 15px;
            text-align: left;
            margin-top: 20px; /* 간격을 벌리기 위해 값 조정 */
            margin-left: -20px;
            margin-bottom: 80px;
            font-family: 'NR';
          "
        >
          실시간으로 가장 수익률이 좋은 펀드를 확인해보세요
        </p>

        <div
          style="
            display: flex; /* 박스를 가로로 배치 */
            justify-content: space-between; /* 박스 간격 조절 */
            margin-top: 20px; /* 박스와 텍스트 간의 간격 조정 */
          "
        >
          <a
            href="/fund/product/view.do?id=K55105DE7745"
            data-ga-label="product_list_K55105DE7745"
            style="width: 25%"
          >
            <div
              class="chart__wrap"
              style="
                padding: 16px;
                background-color: #fff;
                border-radius: 30px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                height: 340px;
                margin-left: -12px;
              "
            >
              <div class="info" style="display: flex; align-items: center">
                <span
                  class="main-category-2 num"
                  style="--fz: 20; color: black; font-family: 'NM'"
                  >1</span
                >
                <button
                  type="button"
                  class="like"
                  style="background: none; border: none; margin-left: auto"
                >
                  <div>
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      id="fill_no"
                      data-name="fill=no"
                      width="24"
                      height="24"
                      viewBox="0 0 24 24"
                      class="injected-svg"
                      data-src="/assets/icons/basic/heart-l-off.svg"
                      xmlns:xlink="http://www.w3.org/1999/xlink"
                    >
                      <path
                        d="M16.3,1.339a4.564,4.564,0,0,1,1.008,1.482,4.5,4.5,0,0,1,0,3.5A4.564,4.564,0,0,1,16.3,7.8l-.9.88L8.833,15.143,2.258,8.681l-.9-.88a4.514,4.514,0,0,1,0-6.462,4.708,4.708,0,0,1,6.575,0l.9.88.9-.88A4.655,4.655,0,0,1,11.237.348a4.724,4.724,0,0,1,3.559,0,4.655,4.655,0,0,1,1.508.991Z"
                        transform="translate(3.167 4)"
                        fill="none"
                        stroke="#888"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="1.5"
                      ></path>
                    </svg>
                  </div>
                </button>
              </div>

              <div
                class="product-icon sm"
                style="text-align: center; margin: 10px 0; position: relative"
              >
                <!-- 첫 번째 이미지 -->
                <div
                  class="icon-big"
                  style="
                    background-color: rgb(97, 72, 254);
                    width: 35px;
                    height: 35px;
                    border-radius: 50%;
                    overflow: hidden;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                  "
                >
                  <img
                    src="@/assets/img/20230208030410102.png"
                    alt="상품 이미지"
                    style="width: 100%; height: auto; max-height: 100%"
                  />
                </div>

                <!-- 두 번째 이미지 -->
                <div
                  class="icon-big"
                  style="
                    position: absolute; /* 절대 위치로 설정 */
                    top: 19px; /* 첫 번째 이미지의 위쪽에서 20px */
                    left: 23px; /* 첫 번째 이미지의 왼쪽에서 20px */
                    width: 20px;
                    height: 20px;
                    overflow: hidden;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                  "
                >
                  <img
                    src="@/assets/img/20230228160253569.png"
                    alt="상품 이미지"
                    style="width: 100%; height: auto; max-height: 100%"
                  />
                </div>
              </div>

              <strong
                class="product-tit title_21-14"
                style="
                  font-size: 16px; /* 삼성한국형TDF2050증권투자신탁UH 폰트 크기 키우기 */
                  display: block;
                  text-align: left; /* 왼쪽 정렬 */
                  color: black;
                  text-decoration: none; /* 기본 텍스트 장식 없음 */
                  margin: 5px 0;
                  font-family: 'NL';
                "
              >
                삼성한국형TDF2050증<br />
                권투자신탁UH
              </strong>

              <span
                class="product-desc graph_12-10"
                style="
                  font-size: 10px; /* [주식혼합-재간접형] 폰트 크기 줄이기 */
                  display: block;
                  text-align: left; /* 왼쪽 정렬 */
                  color: black;
                  text-decoration: none; /* 기본 텍스트 장식 없음 */
                  margin: 0;
                  font-family: 'NR';
                "
              >
                [주식혼합-재간접형]Ce(수수료미징구- <br />
                온라인)
              </span>

              <div
                class="detail false"
                style="margin-top: 5px; text-align: right"
              >
                <div class="detail--content">
                  <span
                    class="return-term graph_12-10"
                    style="
                      font-size: 10px;
                      color: black;
                      text-decoration: none;
                      font-family: 'NL';
                    "
                    >*1개월 수익률</span
                  >
                  <div
                    class="title_28-20 idx-"
                    style="
                      font-size: 20px;
                      color: red;
                      text-align: right;
                      font-family: 'NR';
                    "
                  >
                    <span>4.5</span>%
                  </div>
                </div>

                <div class="content" style="margin-top: 5px">
                  <dl
                    style="
                      display: flex;
                      justify-content: space-between;
                      margin-bottom: 1px;
                    "
                  >
                    <dt
                      class="price-data-3"
                      style="font-size: 11px; color: black; font-family: 'NL'"
                    >
                      기준가
                    </dt>
                    <dd
                      class="price-data-2"
                      style="font-size: 14px; color: black; font-family: 'NM'"
                    >
                      1,763.21 원
                    </dd>
                  </dl>
                  <dl
                    style="
                      display: flex;
                      justify-content: space-between;
                      margin-bottom: 1px;
                    "
                  >
                    <dt
                      class="price-data-3"
                      style="font-size: 11px; color: black; font-family: 'NL'"
                    >
                      순자산
                    </dt>
                    <dd
                      class="price-data-4"
                      style="font-size: 11px; color: black; font-family: 'NL'"
                    >
                      1258.9 억원
                    </dd>
                  </dl>
                </div>
              </div>
            </div>
          </a>

          <!-- 두 번째 박스 추가 -->
          <a
            href="/fund/product/view.do?id=K55105DE7746"
            data-ga-label="product_list_K55105DE7746"
            style="width: 25%"
          >
            <div
              class="chart__wrap"
              style="
                padding: 16px;
                background-color: #fff;
                border-radius: 30px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                height: 340px;
                margin-left: 15px;
              "
            >
              <div class="info" style="display: flex; align-items: center">
                <span
                  class="main-category-2 num"
                  style="--fz: 20; color: black; font-family: 'NM'"
                  >2</span
                >
                <button
                  type="button"
                  class="like"
                  style="background: none; border: none; margin-left: auto"
                >
                  <div>
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      id="fill_no"
                      data-name="fill=no"
                      width="24"
                      height="24"
                      viewBox="0 0 24 24"
                      class="injected-svg"
                      data-src="/assets/icons/basic/heart-l-off.svg"
                      xmlns:xlink="http://www.w3.org/1999/xlink"
                    >
                      <path
                        d="M16.3,1.339a4.564,4.564,0,0,1,1.008,1.482,4.5,4.5,0,0,1,0,3.5A4.564,4.564,0,0,1,16.3,7.8l-.9.88L8.833,15.143,2.258,8.681l-.9-.88a4.514,4.514,0,0,1,0-6.462,4.708,4.708,0,0,1,6.575,0l.9.88.9-.88A4.655,4.655,0,0,1,11.237.348a4.724,4.724,0,0,1,3.559,0,4.655,4.655,0,0,1,1.508.991Z"
                        transform="translate(3.167 4)"
                        fill="none"
                        stroke="#888"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="1.5"
                      ></path>
                    </svg>
                  </div>
                </button>
              </div>

              <div
                class="product-icon sm"
                style="text-align: center; margin: 10px 0; position: relative"
              >
                <!-- 첫 번째 이미지 -->
                <div
                  class="icon-big"
                  style="
                    background-color: rgb(97, 72, 254);
                    width: 35px;
                    height: 35px;
                    border-radius: 50%;
                    overflow: hidden;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                  "
                >
                  <img
                    src="@/assets/img/20230208030410102.png"
                    alt="상품 이미지"
                    style="width: 100%; height: auto; max-height: 100%"
                  />
                </div>

                <!-- 두 번째 이미지 -->
                <div
                  class="icon-big"
                  style="
                    position: absolute; /* 절대 위치로 설정 */
                    top: 19px; /* 첫 번째 이미지의 위쪽에서 20px */
                    left: 23px; /* 첫 번째 이미지의 왼쪽에서 20px */
                    width: 20px;
                    height: 20px;
                    overflow: hidden;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                  "
                >
                  <img
                    src="@/assets/img/20230228161718908.png"
                    alt="상품 이미지"
                    style="width: 100%; height: auto; max-height: 100%"
                  />
                </div>
              </div>

              <strong
                class="product-tit title_21-14"
                style="
                  font-size: 16px; /* 삼성한국형TDF2050증권투자신탁UH 폰트 크기 키우기 */
                  display: block;
                  text-align: left; /* 왼쪽 정렬 */
                  color: black;
                  text-decoration: none; /* 기본 텍스트 장식 없음 */
                  margin: 5px 0;
                  font-family: 'NL';
                "
              >
                삼성한국형TDF2055증<br />
                권투자신탁H
              </strong>

              <span
                class="product-desc graph_12-10"
                style="
                  font-size: 10px; /* [주식혼합-재간접형] 폰트 크기 줄이기 */
                  display: block;
                  text-align: left; /* 왼쪽 정렬 */
                  color: black;
                  text-decoration: none; /* 기본 텍스트 장식 없음 */
                  margin: 0;
                  font-family: 'NR';
                "
              >
                [주식혼합-재간접형]_Ce(수수료미징구- <br />
                온라인)
              </span>

              <div
                class="detail false"
                style="margin-top: 5px; text-align: right"
              >
                <div class="detail--content">
                  <span
                    class="return-term graph_12-10"
                    style="
                      font-size: 10px;
                      color: black;
                      text-decoration: none;
                      font-family: 'NL';
                    "
                    >*1개월 수익률</span
                  >
                  <div
                    class="title_28-20 idx-"
                    style="
                      font-size: 20px;
                      color: red;
                      text-align: right;
                      font-family: 'NR';
                    "
                  >
                    <span>4.0</span>%
                  </div>
                </div>

                <div class="content" style="margin-top: 5px">
                  <dl
                    style="
                      display: flex;
                      justify-content: space-between;
                      margin-bottom: 1px;
                    "
                  >
                    <dt
                      class="price-data-3"
                      style="font-size: 11px; color: black; font-family: 'NL'"
                    >
                      기준가
                    </dt>
                    <dd
                      class="price-data-2"
                      style="font-size: 14px; color: black; font-family: 'NM'"
                    >
                      1,204.63 원
                    </dd>
                  </dl>
                  <dl
                    style="
                      display: flex;
                      justify-content: space-between;
                      margin-bottom: 1px;
                    "
                  >
                    <dt
                      class="price-data-3"
                      style="font-size: 11px; color: black; font-family: 'NL'"
                    >
                      순자산
                    </dt>
                    <dd
                      class="price-data-4"
                      style="font-size: 11px; color: black; font-family: 'NL'"
                    >
                      340.6 억원
                    </dd>
                  </dl>
                </div>
              </div>
            </div>
          </a>

          <a
            href="/fund/product/view.do?id=K55105DE7746"
            data-ga-label="product_list_K55105DE7746"
            style="width: 25%"
          >
            <div
              class="chart__wrap"
              style="
                padding: 16px;
                background-color: #fff;
                border-radius: 30px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                height: 340px;
                margin-left: 15px;
              "
            >
              <div class="info" style="display: flex; align-items: center">
                <span
                  class="main-category-2 num"
                  style="--fz: 20; color: black; font-family: 'NM'"
                  >3</span
                >
                <button
                  type="button"
                  class="like"
                  style="background: none; border: none; margin-left: auto"
                >
                  <div>
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      id="fill_no"
                      data-name="fill=no"
                      width="24"
                      height="24"
                      viewBox="0 0 24 24"
                      class="injected-svg"
                      data-src="/assets/icons/basic/heart-l-off.svg"
                      xmlns:xlink="http://www.w3.org/1999/xlink"
                    >
                      <path
                        d="M16.3,1.339a4.564,4.564,0,0,1,1.008,1.482,4.5,4.5,0,0,1,0,3.5A4.564,4.564,0,0,1,16.3,7.8l-.9.88L8.833,15.143,2.258,8.681l-.9-.88a4.514,4.514,0,0,1,0-6.462,4.708,4.708,0,0,1,6.575,0l.9.88.9-.88A4.655,4.655,0,0,1,11.237.348a4.724,4.724,0,0,1,3.559,0,4.655,4.655,0,0,1,1.508.991Z"
                        transform="translate(3.167 4)"
                        fill="none"
                        stroke="#888"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="1.5"
                      ></path>
                    </svg>
                  </div>
                </button>
              </div>

              <div
                class="product-icon sm"
                style="text-align: center; margin: 10px 0; position: relative"
              >
                <!-- 첫 번째 이미지 -->
                <div
                  class="icon-big"
                  style="
                    background-color: rgb(97, 72, 254);
                    width: 35px;
                    height: 35px;
                    border-radius: 50%;
                    overflow: hidden;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                  "
                >
                  <img
                    src="@/assets/img/20230208030410102.png"
                    alt="상품 이미지"
                    style="width: 100%; height: auto; max-height: 100%"
                  />
                </div>

                <!-- 두 번째 이미지 -->
                <div
                  class="icon-big"
                  style="
                    position: absolute; /* 절대 위치로 설정 */
                    top: 19px; /* 첫 번째 이미지의 위쪽에서 20px */
                    left: 23px; /* 첫 번째 이미지의 왼쪽에서 20px */
                    width: 20px;
                    height: 20px;
                    overflow: hidden;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                  "
                >
                  <img
                    src="@/assets/img/20230228160253569.png"
                    alt="상품 이미지"
                    style="width: 100%; height: auto; max-height: 100%"
                  />
                </div>
              </div>

              <strong
                class="product-tit title_21-14"
                style="
                  font-size: 16px; /* 삼성한국형TDF2050증권투자신탁UH 폰트 크기 키우기 */
                  display: block;
                  text-align: left; /* 왼쪽 정렬 */
                  color: black;
                  text-decoration: none; /* 기본 텍스트 장식 없음 */
                  margin: 5px 0;
                  font-family: 'NL';
                "
              >
                삼성한국형TDF2050증<br />
                권투자신탁H
              </strong>

              <span
                class="product-desc graph_12-10"
                style="
                  font-size: 10px; /* [주식혼합-재간접형] 폰트 크기 줄이기 */
                  display: block;
                  text-align: left; /* 왼쪽 정렬 */
                  color: black;
                  text-decoration: none; /* 기본 텍스트 장식 없음 */
                  margin: 0;
                  font-family: 'NR';
                "
              >
                [주식혼합-재간접형]Ce(수수료미징구- <br />
                온라인)
              </span>

              <div
                class="detail false"
                style="margin-top: 5px; text-align: right"
              >
                <div class="detail--content">
                  <span
                    class="return-term graph_12-10"
                    style="
                      font-size: 10px;
                      color: black;
                      text-decoration: none;
                      font-family: 'NL';
                    "
                    >*1개월 수익률</span
                  >
                  <div
                    class="title_28-20 idx-"
                    style="
                      font-size: 20px;
                      color: red;
                      text-align: right;
                      font-family: 'NR';
                    "
                  >
                    <span>3.9</span>%
                  </div>
                </div>

                <div class="content" style="margin-top: 5px">
                  <dl
                    style="
                      display: flex;
                      justify-content: space-between;
                      margin-bottom: 1px;
                    "
                  >
                    <dt
                      class="price-data-3"
                      style="font-size: 11px; color: black; font-family: 'NL'"
                    >
                      기준가
                    </dt>
                    <dd
                      class="price-data-2"
                      style="font-size: 14px; color: black; font-family: 'NM'"
                    >
                      1,416.23 원
                    </dd>
                  </dl>
                  <dl
                    style="
                      display: flex;
                      justify-content: space-between;
                      margin-bottom: 1px;
                    "
                  >
                    <dt
                      class="price-data-3"
                      style="font-size: 11px; color: black; font-family: 'NL'"
                    >
                      순자산
                    </dt>
                    <dd
                      class="price-data-4"
                      style="font-size: 11px; color: black; font-family: 'NL'"
                    >
                      660.7 억원
                    </dd>
                  </dl>
                </div>
              </div>
            </div>
          </a>

          <a
            href="/fund/product/view.do?id=K55105DE7746"
            data-ga-label="product_list_K55105DE7746"
            style="width: 25%"
          >
            <div
              class="chart__wrap"
              style="
                padding: 16px;
                background-color: #fff;
                border-radius: 30px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                height: 340px;
                margin-left: 15px;
              "
            >
              <div class="info" style="display: flex; align-items: center">
                <span
                  class="main-category-2 num"
                  style="--fz: 20; color: black; font-family: 'NM'"
                  >4</span
                >
                <button
                  type="button"
                  class="like"
                  style="background: none; border: none; margin-left: auto"
                >
                  <div>
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      id="fill_no"
                      data-name="fill=no"
                      width="24"
                      height="24"
                      viewBox="0 0 24 24"
                      class="injected-svg"
                      data-src="/assets/icons/basic/heart-l-off.svg"
                      xmlns:xlink="http://www.w3.org/1999/xlink"
                    >
                      <path
                        d="M16.3,1.339a4.564,4.564,0,0,1,1.008,1.482,4.5,4.5,0,0,1,0,3.5A4.564,4.564,0,0,1,16.3,7.8l-.9.88L8.833,15.143,2.258,8.681l-.9-.88a4.514,4.514,0,0,1,0-6.462,4.708,4.708,0,0,1,6.575,0l.9.88.9-.88A4.655,4.655,0,0,1,11.237.348a4.724,4.724,0,0,1,3.559,0,4.655,4.655,0,0,1,1.508.991Z"
                        transform="translate(3.167 4)"
                        fill="none"
                        stroke="#888"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="1.5"
                      ></path>
                    </svg>
                  </div>
                </button>
              </div>

              <div
                class="product-icon sm"
                style="text-align: center; margin: 10px 0; position: relative"
              >
                <!-- 첫 번째 이미지 -->
                <div
                  class="icon-big"
                  style="
                    background-color: rgb(97, 72, 254);
                    width: 35px;
                    height: 35px;
                    border-radius: 50%;
                    overflow: hidden;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                  "
                >
                  <img
                    src="@/assets/img/20230208030410102.png"
                    alt="상품 이미지"
                    style="width: 100%; height: auto; max-height: 100%"
                  />
                </div>

                <!-- 두 번째 이미지 -->
                <div
                  class="icon-big"
                  style="
                    position: absolute; /* 절대 위치로 설정 */
                    top: 19px; /* 첫 번째 이미지의 위쪽에서 20px */
                    left: 23px; /* 첫 번째 이미지의 왼쪽에서 20px */
                    width: 20px;
                    height: 20px;
                    overflow: hidden;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                  "
                >
                  <img
                    src="@/assets/img/20230228161718908.png"
                    alt="상품 이미지"
                    style="width: 100%; height: auto; max-height: 100%"
                  />
                </div>
              </div>

              <strong
                class="product-tit title_21-14"
                style="
                  font-size: 16px; /* 삼성한국형TDF2050증권투자신탁UH 폰트 크기 키우기 */
                  display: block;
                  text-align: left; /* 왼쪽 정렬 */
                  color: black;
                  text-decoration: none; /* 기본 텍스트 장식 없음 */
                  margin: 5px 0;
                  font-family: 'NL';
                "
              >
                삼성ETF를담은TDF<br />2055증권투자신탁
              </strong>

              <span
                class="product-desc graph_12-10"
                style="
                  font-size: 10px; /* [주식혼합-재간접형] 폰트 크기 줄이기 */
                  display: block;
                  text-align: left; /* 왼쪽 정렬 */
                  color: black;
                  text-decoration: none; /* 기본 텍스트 장식 없음 */
                  margin: 0;
                  font-family: 'NR';
                "
              >
                [혼합-재간접형]_Ce(수수료미징구- <br />
                온라인)
              </span>

              <div
                class="detail false"
                style="margin-top: 5px; text-align: right"
              >
                <div class="detail--content">
                  <span
                    class="return-term graph_12-10"
                    style="
                      font-size: 10px;
                      color: black;
                      text-decoration: none;
                      font-family: 'NL';
                    "
                    >*1개월 수익률</span
                  >
                  <div
                    class="title_28-20 idx-"
                    style="
                      font-size: 20px;
                      color: red;
                      text-align: right;
                      font-family: 'NR';
                    "
                  >
                    <span>3.9</span>%
                  </div>
                </div>

                <div class="content" style="margin-top: 5px">
                  <dl
                    style="
                      display: flex;
                      justify-content: space-between;
                      margin-bottom: 1px;
                    "
                  >
                    <dt
                      class="price-data-3"
                      style="font-size: 11px; color: black; font-family: 'NL'"
                    >
                      기준가
                    </dt>
                    <dd
                      class="price-data-2"
                      style="font-size: 14px; color: black; font-family: 'NM'"
                    >
                      1,091.71 원
                    </dd>
                  </dl>
                  <dl
                    style="
                      display: flex;
                      justify-content: space-between;
                      margin-bottom: 1px;
                    "
                  >
                    <dt
                      class="price-data-3"
                      style="font-size: 11px; color: black; font-family: 'NL'"
                    >
                      순자산
                    </dt>
                    <dd
                      class="price-data-4"
                      style="font-size: 11px; color: black; font-family: 'NL'"
                    >
                      359.3 억원
                    </dd>
                  </dl>
                </div>
              </div>
            </div>
          </a>
        </div>
      </div>

      <div class="detail">
        <h3
          class="detail__tit headline_48-35"
          style="font-size: 35px; margin-top: 150px; font-family: 'NB'"
        >
          TDF(Target Date Fund)란?
        </h3>
        <p
          class="detail__sub-tit body_20-15 mb-64"
          style="font-size: 14px; margin-top: 25px; font-family: 'J2'"
        >
          TDF는 현재 미국에서 가장 각광받는 은퇴준비 상품으로 투자자의
          은퇴시점을 목표로 하여 생애주기에 따라 적극적인<br />투자에서 안정적인
          투자로 포트폴리오를 알아서 조정하는 자산배분 펀드입니다.
        </p>
        <div
          class="box-container"
          style="display: flex; gap: 20px; margin-top: 50px"
        >
          <div
            class="box"
            style="
              flex: 1;
              background-color: #ffffff;
              padding: 30px;
              border-radius: 20px;
              height: 210px;
            "
          >
            <div class="desc-box">
              <img
                src="@/assets/img/fund-detail-icon-01.png"
                alt="icon-01"
                style="max-width: 14%; margin-bottom: 15px"
              />
              <p
                class="desc-box__tit title_22-15_m"
                style="font-size: 16px; font-family: 'J3'"
              >
                생애주기 반영한 Glide Path
              </p>
              <p
                class="desc-box__sub-tit body_16-12"
                style="font-size: 10.5px; font-family: 'NR'"
              >
                생애주기에 따라 점점 위험자산의 비중을 줄여<br />가며
                관리합니다. 이러한 자산 배분 전략을 비행<br />기가 착륙하는
                모습과 닮았다고 하여 Glide <br />path 라고 합니다.
              </p>
            </div>
          </div>

          <div
            class="box"
            style="
              flex: 1;
              background-color: #ffffff;
              padding: 30px;
              border-radius: 20px;
              height: 210px;
              margin-left: -10px;
            "
          >
            <div class="desc-box">
              <img
                src="@/assets/img/fund-detail-icon-02.png"
                alt="icon-01"
                style="max-width: 14%; margin-bottom: 15px"
              />
              <p
                class="desc-box__tit title_22-15_m"
                style="font-size: 16px; font-family: 'J3'"
              >
                글로벌 자산배분
              </p>
              <p
                class="desc-box__sub-tit body_16-12"
                style="font-size: 10.5px; font-family: 'NR'"
              >
                연금투자는 미래의 시장상황을 고려하여 <br />진행되어야하기
                때문에 다양한 지역과 자산에 <br />분산투자하여 불확실성을
                관리하면서도 추가 <br />수익을 창출할 수 있도록 합니다.
              </p>
            </div>
          </div>

          <div
            class="box"
            style="
              flex: 1;
              background-color: #ffffff;
              padding: 30px;
              border-radius: 20px;
              height: 210px;
              margin-left: -10px;
            "
          >
            <div class="desc-box">
              <img
                src="@/assets/img/fund-detail-icon-03.png"
                alt="icon-01"
                style="max-width: 14%; margin-bottom: 15px"
              />
              <p
                class="desc-box__tit title_22-15_m"
                style="font-size: 16px; font-family: 'J3'"
              >
                주기적 리밸런싱
              </p>
              <p
                class="desc-box__sub-tit body_16-12"
                style="font-size: 10.5px; font-family: 'NR'"
              >
                주기적으로 투자 포트폴리오를 조정하여 <br />시장상황이나
                운용자금 변화에 효율적으로 <br />대응하고, 미래 수익을 극대화할
                수 있도록 <br />합니다.
              </p>
            </div>
          </div>
        </div>
        <!-- 이하 생략, 필요한 내용을 계속 추가 -->
      </div>

      <div style="margin-top: 130px"></div>
      <div class="stack-xl"></div>
      <h3
        class="detail__tit headline_48-35"
        style="font-size: 35px; margin-bottom: 30px; font-family: NB"
      >
        삼성 한국형 TDF(Target Date Fund) 펀드
      </h3>
      <p
        class="detail__sub-tit body_20-15"
        style="font-size: 14px; font-family: NR"
      >
        삼성 한국형 TDF(Target Date Fund) 펀드는 한국인의 평균수명 및 근무기간,
        임금 상승률 등을 고려하여<br />
        한국인의 생애주기에 맞는 자산배분 프로그램(Glide Path)를 적용한 연금
        솔루션 상품입니다.
      </p>
      <div class="stack-s"></div>

      <div style="margin-top: 50px"></div>
      <div class="container" style="margin-right: 400px; margin-left: -70px">
        <div
          class="round-box"
          style="
            background-color: #ffffff;
            width: 921.6px;
            height: 1374.35px;
            padding: 57.6046px 76.8061px 76.8061px; /* 상, 우, 하, 좌 */
            border-radius: 30px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            margin: 40px;
          "
        >
          <div class="round-box__numbering">
            <div
              class="round-box__title"
              style="display: flex; align-items: center; margin-bottom: 20px"
            >
              <div
                class="num"
                style="
                  width: 19.2px;
                  height: 19.2px;
                  background-color: #cfe9ff;
                  margin: 0px 9.6007px 0px 0px; /* 상, 우, 하, 좌 */
                  display: flex;
                  align-items: center;
                  justify-content: center;
                  border-radius: 50%;
                "
              >
                <span
                  class="category_14-11"
                  style="--fz: 14; color: #1454ff; font-family: 'NM'"
                  >1</span
                >
              </div>

              <p
                class="headline_24-16"
                style="font-size: 19.2px; margin: 0; font-family: 'J3'"
              >
                한국인의 생애주기에 맞춘 자산배분 프로그램(Glide Path)
              </p>
            </div>

            <div style="margin-top: 20px"></div>
            <!-- 위쪽 여백 20px -->

            <div class="round-box__info mb-48">
              <p
                class="body_20-15"
                style="font-size: 16.0005px; margin: 0; font-family: 'J2'"
              >
                투자자가 젊을수록 성장자산의 비중을 높여 자산증식을 적극적으로
                수행하고, 은퇴시점이 가까워질수록<br />
                안전자산 비중을 늘려 수익을 확보하면서도 노후자산을 안정적으로
                마련할 수 있도록 자산배분합니다.
              </p>
            </div>

            <div style="margin-top: 50px"></div>
            <!-- 위쪽 여백 50px -->

            <p
              class="round-box__desc-md body_18-14 mb-24"
              style="font-size: 14.4px; margin: 0; font-family: 'J3'"
            >
              삼성 한국형 TDF 자산배분 프로그램 (Glide Path)
            </p>

            <div style="margin-bottom: 19.2015px"></div>
            <!-- 아래 여백 19.2015px -->

            <div class="img-wrap mb-48">
              <img
                src="@/assets/img/fund-detail-03.png"
                alt="그래프 이미지"
                style="max-width: 100%; height: auto"
              />
            </div>

            <div style="margin-bottom: 38.4031px"></div>
            <!-- 아래 여백 38.4031px -->

            <p
              class="round-box__desc-md body_18-14"
              style="font-size: 14.4px; margin: 0; font-family: 'J3'"
            >
              삼성 한국형 TDF 펀드 9개 상품별 자산배분 프로그램 (Glide Path)
              개요
            </p>

            <div class="img-wrap">
              <img
                src="@/assets/img/fund-detail-02.png"
                alt="표 이미지"
                style="max-width: 100%; height: auto"
              />
            </div>

            <ul class="noti__list mt-1">
              <li
                class="noti noti--s"
                style="font-size: 10.4003px; margin: 0; font-family: 'J2'"
              >
                상기 글라이드패스 관련 내용은 단순 참고자료이며, 분기 단위 점검
                등 필요시 수시로 조정될 수 있습니다.
              </li>
            </ul>

            <div style="margin-bottom: 6.40016px"></div>
            <!-- 아래 여백 6.40016px -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
p.detail__sub-tit.body_20-15 {
  font-family: J3;
}
a {
  text-decoration: none; /* 모든 링크의 기본 장식 없애기 */
  border: none; /* 링크의 경계 없애기 */
}
.no-underline {
  text-decoration: none !important;
}

.down {
  color: blue;
}

.page-link {
  color: rgba(68, 140, 116, 1);
}

.active > .page-link {
  background-color: rgba(68, 140, 116, 1);
  border: none;
}

.page-link:hover {
  color: white;
}

.pagination {
  margin-right: 40px;
  --bs-pagination-color: rgba(68, 140, 116, 1);
  --bs-pagination-hover-color: rgba(68, 140, 116, 1);
}

.themeList {
  display: block;
  border: 1px solid rgba(153, 153, 153, 0.6);
  border-radius: 20px;
  width: 150%;
  height: 70px;
  background-color: white;
  margin: 10px;
  padding: 20px;
  align-content: space-between;
}

.bc {
  background-color: rgba(241, 243, 248, 1);
  top: 0;
  left: 0;
  height: 100%;
  padding-top: 80px;
}

.container {
  width: 60%;
}

.fundDetailBox {
  width: 300px; /* 원래 크기로 유지 */
  height: 300px; /* 원래 크기로 유지 */
  border: 1px solid rgba(153, 153, 153, 0.6);
  background-color: white;
  border-radius: 30px;
  margin: 20px;
  padding: 40px;
}

.view-kv {
  border-radius: 40px;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  margin: 11px 0 0; /* 상단과 하단 여백 추가 */
  width: 137.5%; /* 가로 크기 1.5배 증가 */
  height: 320px; /* 세로 크기 조정 */
  transform: translateX(-13.81%); /* 가운데 기준으로 이동 */
}

.view-kv .left {
  color: white;
}

.view-kv .right .img-wrap img {
  width: 100px; /* 이미지 크기 조정 */
  height: auto; /* 비율 유지 */
}

p.desc-box__sub-tit.body_16-12 {
  font-size: 12px !important;
}

p.detail__sub-tit.body_20-15 {
  font-size: 15px !important;
}
</style>
