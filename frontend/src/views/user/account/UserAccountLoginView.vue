<template>
    <div v-if="!store.state.user.getting_info">
        <ContentField style="max-width: 30vw; margin-top: 50px">
            <el-form style="max-width: 20vw" label-position="top">
                <el-form-item label="用户名">
                    <el-input v-model="username" />
                </el-form-item>
                <el-form-item label="密码">
                    <el-input type="password" v-model="password" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="login()"> 登录 </el-button>
                    <el-button @click="resetForm()"> 清空 </el-button>
                </el-form-item>
            </el-form>
        </ContentField>
    </div>
</template>

<script lang="js" setup>
import ContentField from "@/components/ContentField.vue"
import { ref } from 'vue'
import { useStore } from "vuex"
import { ElMessageBox } from 'element-plus'
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();
const redirectPath = route.query.redirect || '/home';

const store = useStore();
const username = ref('');
const password = ref('');
const error_message = ref('');

const jwt_token = localStorage.getItem("jwt_token");
if (jwt_token) {
    store.commit("updateToken", jwt_token);
    store.dispatch("getInfo", {
        success() {
            router.push(redirectPath);
            store.commit("updateGettingInfo", false);
        },
        error() {
            store.commit("updateGettingInfo", false);
        }
    })
} else {
    store.commit("updateGettingInfo", false);
}

const login = () => {
    store.dispatch("login", {
        username: username.value,
        password: password.value,
        success() {
            store.dispatch("getInfo", {
                success() {
                    error_message.value = '登录成功';
                    loginSuccessMessageBox(redirectPath);
                },
                error() {
                    error_message.value = '用户名或密码错误';
                    loginFailMessageBox();
                }
            })
        },
        error() {
            error_message.value = '用户名或密码错误';
            loginFailMessageBox();
        }
    })
};

const resetForm = () => {
    username.value = '';
    password.value = '';
    error_message.value = '';
};

const loginSuccessMessageBox = (redirectPath) => {
    ElMessageBox.alert(error_message.value, '', {
    confirmButtonText: 'OK',
    callback: () => {
      router.push(redirectPath);
    },
  })
};

const loginFailMessageBox = () => {
    ElMessageBox.alert(error_message.value, '', {
    confirmButtonText: 'OK',
  })
};
</script>

<style scoped>
.el-form {
    margin: auto;
    margin-top: 30px;
}
/* div.error_message {
    margin-top: 10px;
    display: flex;
    align-content: center;
    justify-content: center;
} */
</style>
