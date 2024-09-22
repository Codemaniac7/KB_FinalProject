<template>
  <div class="content fs-6 d-flex flex-column flex-column-fluid" id="kt_content">
    <div class="post fs-6 d-flex flex-column-fluid" id="kt_post">
      <div class="container-xxl">
        <!-- Navbar -->
        <div class="card mb-5 mb-xxl-8">
          <div class="card-body pt-9 pb-0">
            <!-- Details -->
            <div class="d-flex flex-wrap flex-sm-nowrap mb-3">
              <!-- Pic -->
              <div class="me-7 mb-4">
                <div class="symbol symbol-100px symbol-lg-160px symbol-fixed position-relative">
                  <img :src="require('@/assets/media/avatars/300-1.jpg')" alt="image" />
                  <div class="position-absolute translate-middle bottom-0 start-100 mb-6 bg-success rounded-circle border border-4 border-body h-20px w-20px"></div>
                </div>
              </div>
              <!-- Info -->
              <div class="flex-grow-1">
                <div class="d-flex justify-content-between align-items-start flex-wrap mb-2">
                  <div class="d-flex flex-column">
                    <div class="d-flex align-items-center mb-2">
                      <a href="#" class="text-gray-900 text-hover-primary fs-2 fw-bold me-1">Max Smith</a>
                      <a href="#">
                        <i class="ki-duotone ki-verify fs-1 text-primary">
                          <span class="path1"></span>
                          <span class="path2"></span>
                        </i>
                      </a>
                    </div>
                    <div class="d-flex flex-wrap fw-semibold fs-6 mb-4 pe-2">
                      <a href="#" class="d-flex align-items-center text-gray-500 text-hover-primary me-5 mb-2">
                        <i class="ki-duotone ki-profile-circle fs-4 me-1">
                          <span class="path1"></span>
                          <span class="path2"></span>
                          <span class="path3"></span>
                        </i>Developer
                      </a>
                      <a href="#" class="d-flex align-items-center text-gray-500 text-hover-primary me-5 mb-2">
                        <i class="ki-duotone ki-geolocation fs-4 me-1">
                          <span class="path1"></span>
                          <span class="path2"></span>
                        </i>SF, Bay Area
                      </a>
                      <a href="#" class="d-flex align-items-center text-gray-500 text-hover-primary mb-2">
                        <i class="ki-duotone ki-sms fs-4 me-1">
                          <span class="path1"></span>
                          <span class="path2"></span>
                        </i>max@kt.com
                      </a>
                    </div>
                  </div>
                  <!-- Actions -->
                  <div class="d-flex my-4">
                    <a href="#" class="btn btn-sm btn-light me-2" id="kt_user_follow_button">
                      <i class="ki-duotone ki-check fs-3 d-none"></i>
                      <span class="indicator-label">Follow</span>
                      <span class="indicator-progress">Please wait... 
                        <span class="spinner-border spinner-border-sm align-middle ms-2"></span>
                      </span>
                    </a>
                    <a href="#" class="btn btn-sm btn-primary me-3" data-bs-toggle="modal" data-bs-target="#kt_modal_offer_a_deal">Hire Me</a>
                    <div class="me-0">
                      <button class="btn btn-sm btn-icon btn-bg-light btn-active-color-primary" data-kt-menu-trigger="click" data-kt-menu-placement="bottom-end">
                        <i class="ki-solid ki-dots-horizontal fs-2x me-1"></i>
                      </button>
                      <!-- Menu -->
                      <div class="menu menu-sub menu-sub-dropdown menu-column menu-rounded menu-gray-800 menu-state-bg-light-primary fw-semibold w-200px" :data-kt-menu="true">
                        <!-- Menu content -->
                      </div>
                    </div>
                  </div>
                </div>
                <!-- Stats -->
                <div class="d-flex flex-wrap flex-stack">
                  <div class="d-flex flex-column flex-grow-1 pe-8">
                    <div class="d-flex flex-wrap">
                      <div class="border border-gray-300 border-dashed rounded min-w-125px py-3 px-4 me-6 mb-3">
                        <div class="d-flex align-items-center">
                          <i class="ki-duotone ki-arrow-up fs-3 text-success me-2">
                            <span class="path1"></span>
                            <span class="path2"></span>
                          </i>
                          <div class="fs-2 fw-bold" :data-kt-countup="true" :data-kt-countup-value="4500" data-kt-countup-prefix="$">$4,500</div>
                        </div>
                        <div class="fw-semibold fs-6 text-gray-500">Earnings</div>
                      </div>
                      <div class="border border-gray-300 border-dashed rounded min-w-125px py-3 px-4 me-6 mb-3">
                        <div class="d-flex align-items-center">
                          <i class="ki-duotone ki-arrow-down fs-3 text-danger me-2">
                            <span class="path1"></span>
                            <span class="path2"></span>
                          </i>
                          <div class="fs-2 fw-bold" :data-kt-countup="true" :data-kt-countup-value="80">80</div>
                        </div>
                        <div class="fw-semibold fs-6 text-gray-500">Projects</div>
                      </div>
                      <div class="border border-gray-300 border-dashed rounded min-w-125px py-3 px-4 me-6 mb-3">
                        <div class="d-flex align-items-center">
                          <i class="ki-duotone ki-arrow-up fs-3 text-success me-2">
                            <span class="path1"></span>
                            <span class="path2"></span>
                          </i>
                          <div class="fs-2 fw-bold" :data-kt-countup="true" :data-kt-countup-value="60" data-kt-countup-prefix="%">%60</div>
                        </div>
                        <div class="fw-semibold fs-6 text-gray-500">Success Rate</div>
                      </div>
                    </div>
                  </div>
                  <div class="d-flex align-items-center w-200px w-sm-300px flex-column mt-3">
                    <div class="d-flex justify-content-between w-100 mt-auto mb-2">
                      <span class="fw-semibold fs-6 text-gray-500">Profile Compleation</span>
                      <span class="fw-bold fs-6">50%</span>
                    </div>
                    <div class="h-5px mx-3 w-100 bg-light mb-3">
                      <div class="bg-success rounded h-5px" role="progressbar" style="width: 50%;" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- Tabs -->
            <ul class="nav nav-stretch nav-line-tabs nav-line-tabs-2x border-transparent fs-5 fw-bold">
              <li v-for="(tab, index) in tabs" :key="index" class="nav-item mt-2">
                <a :class="['nav-link', 'text-active-primary', 'ms-0', 'me-10', 'py-5', { active: tab.active }]" :href="tab.href">{{ tab.text }}</a>
              </li>
            </ul>
          </div>
        </div>
        <!-- Content -->
        <div class="row g-5 g-xxl-8">
          <div class="col-xl-6">
            <!-- Feeds widget 1 -->
            <div class="card mb-5 mb-xxl-8">
              <div class="card-body pb-0">
                <div class="d-flex align-items-center">
                  <div class="symbol symbol-45px me-5">
                    <img :src="require('@/assets/media/avatars/300-25.jpg')" alt="" />
                  </div>
                  <div class="d-flex flex-column">
                    <a href="#" class="text-gray-800 text-hover-primary mb-1 fs-6 fw-bolder">Brad Dennis</a>
                    <span class="text-gray-500 fw-semibold">Yestarday at 5:06 PM</span>
                  </div>
                </div>
                <div class="pt-5">
                  <p class="text-gray-800 fs-6 fw-normal mb-3">Outlines keep you honest. They stop you from indulging in poorly thought-out metaphors about driving and keep you focused on the overall structure of your post</p>
                  <div class="d-flex align-items-center">
                    <a href="#" class="btn btn-sm btn-color-muted btn-active-light-primary fw-bolder fs-6 py-1 px-2 me-4">
                      <i class="ki-duotone ki-message-text-2 fs-2">
                        <span class="path1"></span>
                        <span class="path2"></span>
                        <span class="path3"></span>
                      </i>24
                    </a>
                    <a href="#" class="btn btn-sm btn-color-muted btn-active-light-danger fw-bold fs-6 py-1 px-2">
                      <i class="ki-duotone ki-heart fs-2">
                        <span class="path1"></span>
                        <span class="path2"></span>
                      </i>75
                    </a>
                  </div>
                </div>
                <div class="separator pt-5 mb-3"></div>
                <form class="position-relative pb-3">
                  <textarea data-kt-autosize="true" class="form-control border-0 p-0 pe-10 resize-none min-h-25px" rows="1" placeholder="Reply.."></textarea>
                  <div class="position-absolute top-0 end-0 me-n5">
                    <span class="btn btn-icon btn-sm btn-active-color-primary pe-0">
                      <i class="ki-duotone ki-paper-clip fs-2 mb-3"></i>
                    </span>
                    <span class="btn btn-icon btn-sm btn-active-color-primary ps-0">
                      <i class="ki-duotone ki-geolocation fs-2 mb-3">
                        <span class="path1"></span>
                        <span class="path2"></span>
                      </i>
                    </span>
                  </div>
                </form>
              </div>
            </div>
            <!-- Feeds Widget 2 -->
            <div class="card mb-5 mb-xxl-8">
              <div class="card-body pb-0">
                <div class="d-flex align-items-center">
                  <div class="symbol symbol-45px me-5">
                    <img :src="require('@/assets/media/avatars/300-11.jpg')" alt="" />
                  </div>
                  <div class="d-flex flex-column">
                    <a href="#" class="text-gray-800 text-hover-primary mb-1 fs-6 fw-bolder">Kristaps Zumman</a>
                    <span class="text-gray-500 fw-semibold">Yestarday at 3:23 PM</span>
                  </div>
                </div>
                <div class="pt-5">
                  <div class="bgi-no-repeat bgi-size-cover rounded min-h-250px mb-5" :style="{ backgroundImage: `url(${require('@/assets/media/stock/900x600/20.jpg')})` }"></div>
                  <p class="text-gray-800 fs-6 fw-normal mb-3">Outlines keep you honest. They stop you from indulging in poorly thought-out metaphors about driving and keep you focused on the overall structure of your post</p>
                  <div class="d-flex align-items-center">
                    <a href="#" class="btn btn-sm btn-color-muted btn-active-light-primary fw-bolder fs-6 py-1 px-2 me-4">
                      <i class="ki-duotone ki-message-text-2 fs-2">
                        <span class="path1"></span>
                        <span class="path2"></span>
                        <span class="path3"></span>
                      </i>24
                    </a>
                    <a href="#" class="btn btn-sm btn-color-muted btn-active-light-danger fw-bold fs-6 py-1 px-2">
                      <i class="ki-duotone ki-heart fs-2 text-danger">
                        <span class="path1"></span>
                        <span class="path2"></span>
                      </i>75
                    </a>
                  </div>
                </div>
                <div class="separator pt-4 mb-3"></div>
                <form class="position-relative pb-3">
                  <textarea data-kt-autosize="true" class="form-control border-0 p-0 pe-10 resize-none min-h-25px" rows="1" placeholder="Reply.."></textarea>
                  <div class="position-absolute top-0 end-0 me-n5">
                    <span class="btn btn-icon btn-sm btn-active-color-primary pe-0">
                      <i class="ki-duotone ki-paper-clip fs-2 mb-3"></i>
                    </span>
                    <span class="btn btn-icon btn-sm btn-active-color-primary ps-0">
                      <i class="ki-duotone ki-geolocation fs-2 mb-3">
                        <span class="path1"></span>
                        <span class="path2"></span>
                      </i>
                    </span>
                  </div>
                </form>
              </div>
            </div>
            <!-- Feeds Widget 3 -->
            <div class="card mb-5 mb-xxl-8">
              <div class="card-body pb-0">
                <div class="d-flex align-items-center mb-5">
                  <div class="symbol symbol-45px me-5">
                    <img :src="require('@/assets/media/avatars/300-14.jpg')" alt="" />
                  </div>
                  <div class="d-flex flex-column">
                    <a href="#" class="text-gray-800 text-hover-primary mb-1 fs-6 fw-bolder">Carles Puyol</a>
                    <span class="text-gray-500 fw-semibold">Yestarday at 5:06 PM</span>
                  </div>
                </div>
                <div class="mb-5">
                  <p class="text-gray-800 fs-6 fw-normal mb-2">Outlines keep you honest. They stop you from indulging in poorly thought-out metaphors about driving and keep you focused on the overall structure of your post</p>
                </div>
                <div class="d-flex align-items-center mb-5">
                  <a href="#" class="btn btn-sm btn-light btn-color-muted btn-active-light-success px-4 py-2 me-4">
                    <i class="ki-duotone ki-message-text-2 fs-2">
                      <span class="path1"></span>
                      <span class="path2"></span>
                      <span class="path3"></span>
                    </i>120
                  </a>
                  <a href="#" class="btn btn-sm btn-light btn-color-muted btn-active-light-danger px-4 py-2">
                    <i class="ki-duotone ki-heart fs-2">
                      <span class="path1"></span>
                      <span class="path2"></span>
                    </i>15
                  </a>
                </div>
                <div class="separator mb-4"></div>
                <div class="d-flex align-items-center">
                  <div class="symbol symbol-35px me-3">
                    <img :src="require('@/assets/media/avatars/300-20.jpg')" alt="" />
                  </div>
                  <div class="d-flex flex-column flex-row-fluid">
                    <div class="d-flex align-items-center flex-wrap mb-1">
                      <a href="#" class="text-gray-800 text-hover-primary fw-bolder me-2">Alice Danchik</a>
                      <span class="text-gray-400 fw-semibold fs-7">1 day</span>
                    </div>
                    <span class="text-gray-800 fs-7 fw-normal pt-1">Long before you sit dow to put digital pen to paper you need to make sure you have to sit down and write.</span>
                  </div>
                </div>
                <div class="separator mb-4"></div>
                <form class="position-relative mb-6">
                  <textarea class="form-control border-0 p-0 pe-10 resize-none min-h-25px" data-kt-autosize="true" rows="1" placeholder="Reply.."></textarea>
                  <div class="position-absolute top-0 end-0 me-n5">
                    <span class="btn btn-icon btn-sm btn-active-color-primary pe-0 me-2">
                      <i class="ki-duotone ki-paper-clip fs-2 mb-3"></i>
                    </span>
                    <span class="btn btn-icon btn-sm btn-active-color-primary ps-0">
                      <i class="ki-duotone ki-geolocation fs-2 mb-3">
                        <span class="path1"></span>
                        <span class="path2"></span>
                      </i>
                    </span>
                  </div>
                </form>
              </div>
            </div>
            <!-- Feeds widget 4 -->
            <div class="card mb-5 mb-xxl-8">
              <div class="card-body pb-0">
                <div class="d-flex align-items-center mb-5">
                  <div class="symbol symbol-45px me-5">
                    <img :src="require('@/assets/media/avatars/300-5.jpg')" alt="" />
                  </div>
                  <div class="d-flex flex-column">
                    <a href="#" class="text-gray-800 text-hover-primary fw-bolder fs-6">Sam Stone</a>
                    <span class="text-gray-400 fw-semibold fs-7">Yestarday at 5:06 PM</span>
                  </div>
                </div>
                <div class="mb-5">
                  <p class="text-gray-800 fs-6 fw-normal mb-2">Outlines keep you honest. They stop you from indulging in poorly thought-out metaphors about driving and keep you focused on the overall structure of your post</p>
                </div>
                <div class="d-flex align-items-center mb-5">
                  <a href="#" class="btn btn-sm btn-light btn-color-muted btn-active-light-success px-4 py-2 me-4">
                    <i class="ki-duotone ki-message-text-2 fs-2">
                      <span class="path1"></span>
                      <span class="path2"></span>
                      <span class="path3"></span>
                    </i>22
                  </a>
                  <a href="#" class="btn btn-sm btn-light btn-color-muted btn-active-light-danger px-4 py-2">
                    <i class="ki-duotone ki-heart fs-2">
                      <span class="path1"></span>
                      <span class="path2"></span>
                    </i>9
                  </a>
                </div>
                <div class="separator mb-4"></div>
                <form class="position-relative mb-6">
                  <textarea class="form-control border-0 p-0 pe-10 resize-none min-h-25px" data-kt-autosize="true" rows="1" placeholder="Reply.."></textarea>
                  <div class="position-absolute top-0 end-0 me-n5">
                    <span class="btn btn-icon btn-sm btn-active-color-primary pe-0 me-2">
                      <i class="ki-duotone ki-paper-clip fs-2 mb-3"></i>
                    </span>
                    <span class="btn btn-icon btn-sm btn-active-color-primary ps-0">
                      <i class="ki-duotone ki-geolocation fs-2 mb-3">
                        <span class="path1"></span>
                        <span class="path2"></span>
                      </i>
                    </span>
                  </div>
                </form>
              </div>
            </div>
            <!-- Feeds widget 5 -->
            <div class="card mb-5 mb-xxl-8 d-none" id="kt_widget_5">
              <div class="card-body pb-0">
                <div class="d-flex align-items-center mb-5">
                  <div class="symbol symbol-45px me-5">
                    <img :src="require('@/assets/media/avatars/300-20.jpg')" alt="" />
                  </div>
                  <div class="d-flex flex-column">
                    <a href="#" class="text-gray-800 text-hover-primary fw-bolder fs-6">Nancy Miller</a>
                    <span class="text-gray-400 fw-semibold fs-7">Today at 5:06 PM</span>
                  </div>
                </div>
                <div class="mb-5">
                  <div class="bgi-no-repeat bgi-size-cover rounded min-h-250px mb-5" :style="{ backgroundImage: `url(${require('@/assets/media/stock/900x600/19.jpg')})` }"></div>
                  <p class="text-gray-800 fs-6 fw-normal mb-2">Outlines keep you honest. They stop you from indulging in poorly thought-out metaphors about driving and keep you focused on the overall structure of your post</p>
                </div>
                <div class="d-flex align-items-center mb-5">
                  <a href="#" class="btn btn-sm btn-light btn-color-muted btn-active-light-success px-4 py-2 me-4">
                    <i class="ki-duotone ki-message-text-2 fs-2">
                      <span class="path1"></span>
                      <span class="path2"></span>
                      <span class="path3"></span>
                    </i>56
                  </a>
                  <a href="#" class="btn btn-sm btn-light btn-color-muted btn-active-light-danger px-4 py-2">
                    <i class="ki-duotone ki-heart fs-2">
                      <span class="path1"></span>
                      <span class="path2"></span>
                    </i>114
                  </a>
                </div>
                <div class="separator mb-4"></div>
                <form class="position-relative mb-6">
                  <textarea class="form-control border-0 p-0 pe-10 resize-none min-h-25px" data-kt-autosize="true" rows="1" placeholder="Reply.."></textarea>
                  <div class="position-absolute top-0 end-0 me-n5">
                    <span class="btn btn-icon btn-sm btn-active-color-primary pe-0 me-2">
                      <i class="ki-duotone ki-paper-clip fs-2 mb-3"></i>
                    </span>
                    <span class="btn btn-icon btn-sm btn-active-color-primary ps-0">
                      <i class="ki-duotone ki-geolocation fs-2 mb-3">
                        <span class="path1"></span>
                        <span class="path2"></span>
                      </i>
                    </span>
                  </div>
                </form>
              </div>
            </div>
            <!-- Feeds widget 4, 5 load more -->
            <button class="btn btn-primary w-100 text-center" id="kt_widget_5_load_more_btn" @click="loadMoreFeeds">
              <span class="indicator-label">More Feeds</span>
              <span class="indicator-progress">Loading... 
              <span class="spinner-border spinner-border-sm align-middle ms-2"></span></span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Analysis',
  data() {
    return {
      tabs: [
        { text: 'Overview', href: '#', active: true },
        { text: 'Projects', href: '#', active: false },
        { text: 'Campaigns', href: '#', active: false },
        { text: 'Documents', href: '#', active: false },
        { text: 'Followers', href: '#', active: false },
        { text: 'Activity', href: '#', active: false },
      ]
    }
  },
  methods: {
    loadMoreFeeds() {
      const widget5 = document.getElementById('kt_widget_5');
      const loadMoreBtn = document.getElementById('kt_widget_5_load_more_btn');
      
      widget5.classList.remove('d-none');
      loadMoreBtn.classList.add('d-none');
    }
  }
}
</script>

<style scoped>
.card.mb-5.mb-xxl-8 {
    border-radius: 30px !important;
}
</style>