import { createRouter, createWebHistory } from "vue-router";
import PKIndexView from "../views/pk/PKIndexView.vue";
import RanklistIndexView from "@/views/ranklist/RanklistIndexView.vue";
import RecordView from "@/views/record/RecordView.vue";
import UserBotIndexView from "@/views/user/bot/UserBotIndexView.vue";
import NotFound from "@/views/error/NotFound.vue";

const routes = [
  {
    path: "/",
    redirect: "/pk",
  },
  {
    path: "/pk",
    component: PKIndexView,
  },
  {
    path: "/ranklist",
    component: RanklistIndexView,
  },
  {
    path: "/record",
    component: RecordView,
  },
  {
    path: "/user/bot",
    component: UserBotIndexView,
  },
  {
    path: "/error",
    component: NotFound,
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/error",
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
