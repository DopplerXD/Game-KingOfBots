<template>
    <div>
        <ContentField style="max-width: 30vw; margin-top: 50px">
            <el-form style="max-width: 20vw" label-position="top">
                <el-form-item label="用户名">
                    <el-input v-model="username" />
                </el-form-item>
                <el-form-item label="密码">
                    <el-input type="password" v-model="password" />
                </el-form-item>
                <el-form-item label="确认密码">
                    <el-input type="password" v-model="confirmedPassword" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="register"> 注册 </el-button>
                    <el-button @click="resetForm"> 清空 </el-button>
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

const store = useStore();
const username = ref('');
const password = ref('');
const confirmedPassword = ref('');
const error_message = ref('');

const register = () => {
    store.dispatch("register", {
        username: username.value,
        password: password.value,
        confirmedPassword: confirmedPassword.value,
        success() {
            store.dispatch("getInfo", {
                success() {
                    const redirectPath = route.query.redirect || '/home';
                    error_message.value = '注册成功';
                    registerSuccessMessageBox(redirectPath);
                }
            })
        },
        error(res_error_message) {
            error_message.value = res_error_message;
            registerFailMessageBox();
        }
    })
};

const resetForm = () => {
    username.value = '';
    password.value = '';
    confirmedPassword.value = '';
};

const registerSuccessMessageBox = (redirectPath) => {
    ElMessageBox.alert(error_message.value, '', {
    confirmButtonText: 'OK',
    callback: () => {
      router.push(redirectPath);
    },
  })
};

const registerFailMessageBox = () => {
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
</style>
