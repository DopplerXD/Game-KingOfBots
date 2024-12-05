import { createRouter, createWebHistory } from "vue-router";
import App from "../App.vue";
import NavBar from "../components/NavBar.vue";

const routes = [
  {
    path: "/",
    component: App,
  },
  {
    path: '/home',
    component: NavBar
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
