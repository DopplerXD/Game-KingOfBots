<template>
    <div>
        <ContentField style="max-width: 30vw; margin-top: 50px">
            <el-form ref="ruleFormRef" style="max-width: 20vw" label-position="top">
                <el-form-item label="用户名">
                    <el-input v-model="username" />
                </el-form-item>
                <el-form-item label="密码">
                    <el-input type="password" v-model="password" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm(ruleFormRef)"> 登录 </el-button>
                    <el-button @click="resetForm(ruleFormRef)"> 清空 </el-button>
                </el-form-item>
            </el-form>
            <!-- <div class="error_message">
                {{ error_message }}
            </div> -->
        </ContentField>
    </div>
</template>

<script lang="js" setup>
import ContentField from "@/components/ContentField.vue"
import { ref } from 'vue'
import { useStore } from "vuex"
import { ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router';

const router = useRouter();

const store = useStore();
const username = ref('');
const password = ref('');
const error_message = ref('');

const submitForm = () => {
    store.dispatch("login", {
        username: username.value,
        password: password.value,
        success(response) {
            console.log(response);
            error_message.value = '登录成功';
            loginSuccessMessageBox();
        },
        error() {
            console.log("用户名或密码错误");
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

const loginSuccessMessageBox = () => {
    ElMessageBox.alert(error_message.value, '', {
    confirmButtonText: 'OK',
    callback: () => {
      router.push('/pk');
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
