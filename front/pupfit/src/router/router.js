import { createRouter, createWebHistory } from 'vue-router'
import HomeView from "@/views/HomeView.vue";
import PuppyList from "@/views/PuppyList.vue";
import WalkedNoteList from "@/views/WalkedNoteList.vue";
import MyPage from "@/views/MyPage.vue";

const routes = [

  {
    path: '/',
    name: 'HomeView',
    component: HomeView,
    meta: { requiresAuth: true },
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
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
