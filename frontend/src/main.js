import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import ElementPlus from "element-plus";
import request from "./axios";
import store from "./store";
import "element-plus/dist/index.css";
import "./assets/css/global.css";

const app = createApp(App);

app.use(router);
app.use(ElementPlus);
app.use(store);
app.config.globalProperties.$request = request;

app.mount("#app");
