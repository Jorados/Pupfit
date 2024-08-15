import { createRouter, createWebHistory } from 'vue-router'
import HomeView from "@/views/HomeView.vue";
import PuppyList from "@/views/PuppyList.vue";
import WalkedNoteList from "@/views/WalkedNoteList.vue";
import MyPage from "@/views/MyPage.vue";
import UserLogin from "@/views/UserLogin.vue"; // UserLogin 뷰 추가
import store from "@/store/store";
import MyEdit from "@/views/MyEdit.vue"; // Vuex 스토어 임포트 (로그인 상태 관리)

const routes = [
  {
    path: '/',
    name: 'HomeView',
    component: HomeView,
    meta: { requiresAuth: true },
  },
  {
    path: '/login',
    name: 'UserLogin',
    component: UserLogin,
  },
  {
    path: '/puppy',
    name: 'PuppyList',
    component: PuppyList,
    meta: { requiresAuth: true },
  },
  {
    path: '/walkedNote',
    name: 'walkedNoteList',
    component: WalkedNoteList,
    meta: { requiresAuth: true },
  },
  {
    path: '/myPage',
    name: 'MyPage',
    component: MyPage,
    meta: { requiresAuth: true },
  },
  {
    path: '/myEdit',
    name: 'MyEdit',
    component: MyEdit,
    meta: { requiresAuth: true },
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

// 라우트 가드 -> 특정 라우트에 진입하거나 떠나기 전에 특정 로직을 실행하게 해준다
// 라우트 전환 시 인증 상태를 체크.
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');

  // 인증이 필요한 페이지 접근 시
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!token) {
      next({ name: 'UserLogin' });
    } else {
      next();
    }
  } else {
    if (to.name === 'UserLogin' && token) {
      next({ name: 'HomeView' });
    } else {
      next();
    }
  }
});


export default router
