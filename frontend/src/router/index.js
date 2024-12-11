import { createRouter, createWebHistory } from "vue-router";
import HomePageView from "@/views/public/HomePageView.vue";
import PKIndexView from "@/views/pk/PKIndexView.vue";
import RanklistIndexView from "@/views/ranklist/RanklistIndexView.vue";
import RecordView from "@/views/record/RecordView.vue";
import UserBotIndexView from "@/views/user/bot/UserBotIndexView.vue";
import NotFound from "@/views/error/NotFound.vue";
import UserAccountLoginView from "@/views/user/account/UserAccountLoginView";
import UserAccountRegisterView from "@/views/user/account/UserAccountRegisterView";
import UserAccountInfoView from "@/views/user/account/UserAccountInfoView";

import store from '@/store/index'

const routes = [
    {
        path: "/",
        redirect: "/home",
    },
    {
        path: "/home",
        component: HomePageView,
        meta: {
            requestAuth: false,
        },
    },
    {
        path: "/pk",
        component: PKIndexView,
        meta: {
            requestAuth: true,
        },
    },
    {
        path: "/user/account/login",
        name: "user_account_login",
        component: UserAccountLoginView,
        meta: {
            requestAuth: false,
        },
    },
    {
        path: "/user/account/register",
        component: UserAccountRegisterView,
        meta: {
            requestAuth: false,
        },
    },
    {
        path: "/user/account/info",
        component: UserAccountInfoView,
        meta: {
            requestAuth: true,
        },
    },
    {
        path: "/ranklist",
        component: RanklistIndexView,
        meta: {
            requestAuth: true,
        },
    },
    {
        path: "/record",
        component: RecordView,
        meta: {
            requestAuth: true,
        },
    },
    {
        path: "/user/bot",
        component: UserBotIndexView,
        meta: {
            requestAuth: true,
        },
    },
    {
        path: "/error",
        component: NotFound,
        meta: {
            requestAuth: false,
        },
    },
    {
        path: "/:catchAll(.*)",
        redirect: "/error",
        meta: {
            requestAuth: false,
        },
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

router.beforeEach((to, from, next) => {
    if (to.meta.requestAuth && !store.state.user.is_login) {
        next({name: "user_account_login", query: { redirect: to.fullPath }});
    } else {
        next();
    }
});

export default router;
