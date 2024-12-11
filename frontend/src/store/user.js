// import { createStore } from "vuex";
import request from "@/axios";

export default {
    state: {
        id: "",
        username: "",
        photo: "",
        token: "",
        is_login: false,
    },
    getters: {},
    mutations: {
        updateUser(state, user) {
            state.id = user.id;
            state.username = user.username;
            state.photo = user.photo;
            state.is_login = user.is_login;
        },
        updateToken(state, token) {
            state.token = token;
        },
        logout(state) {
            state.id = '';
            state.username = '';
            state.photo = '';
            state.token = '';
            state.is_login = false;
        }
    },
    actions: {
        login(context, data) {
            request({
                method: "POST",
                url: "/user/account/login",
                data: {
                    username: data.username,
                    password: data.password,
                },
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
            })
                .then((response) => {
                    if (response.data.error_message === "success") {
                        context.commit("updateToken", response.data.token);
                        data.success(response.data);
                    } else {
                        data.error();
                    }
                })
                .catch(() => {
                    data.error();
                });
        },
        getInfo(context, data) {
            request({
                method: "GET",
                url: "/user/account/info",
                headers: {
                    Authorization:
                        "Bearer " + context.state.token,
                },
            })
                .then((response) => {
                    if (response.data.error_message === "success") {
                        context.commit("updateUser", {
                            ...response.data,
                            is_login: true,
                        });
                        data.success(response);
                    } else {
                        data.error();
                    }
                })
                .catch(() => {
                    data.error();
                });
        },
        logout(context) {
            context.commit("logout");
        }
    },
    modules: {},
};
