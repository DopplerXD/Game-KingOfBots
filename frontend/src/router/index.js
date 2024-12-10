import { createRouter, createWebHistory } from "vue-router";
import PKIndexView from "../views/pk/PKIndexView.vue";
import RanklistIndexView from "@/views/ranklist/RanklistIndexView.vue";
import RecordView from "@/views/record/RecordView.vue";
import UserBotIndexView from "@/views/user/bot/UserBotIndexView.vue";
import NotFound from "@/views/error/NotFound.vue";
import UserAccountLoginView from "@/views/user/account/UserAccountLoginView";
import UserAccountRegisterView from "@/views/user/account/UserAccountRegisterView";
import UserAccountInfoView from "@/views/user/account/UserAccountInfoView";

const routes = [
  {
    path: "/",
    redirect: "/pk",
  },
  {
    path: "/user/account/login",
    component: UserAccountLoginView,
  },
  {
    path: "/user/account/register",
    component: UserAccountRegisterView,
  },
  {
    path: "/user/account/info",
    component: UserAccountInfoView,
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
