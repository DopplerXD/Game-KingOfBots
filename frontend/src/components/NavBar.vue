<template>
    <div>
        <el-menu
            class="header"
            :default-active="activeIndex"
            mode="horizontal"
            background-color="#1f2123"
            text-color="#fff"
            :ellipsis="false"
            router="true"
            close-on-click-outside="true"
            style="--el-menu-active-color: #fff"
        >
            <!-- <el-menu-item index="/">King Of Bots</el-menu-item> -->
            <el-menu-item index="/home">主页</el-menu-item>
            <el-menu-item index="/pk">对战</el-menu-item>
            <el-menu-item index="/record">对局列表</el-menu-item>
            <el-menu-item index="/ranklist">排行榜</el-menu-item>
            <template v-if="store.state.user.is_login">
                <el-sub-menu index="2">
                    <template #title>{{ store.state.user.username }}</template>
                    <el-menu-item index="/user/account/info">个人信息</el-menu-item>
                    <el-menu-item index="/user/bot">我的 Bot</el-menu-item>
                    <el-menu-item @click="logoutConfirm">退出</el-menu-item>
                </el-sub-menu>
            </template>
            <template v-else-if="!store.getting_info">
                <el-menu-item index="/user/account/login">登录</el-menu-item>
                <el-menu-item index="/user/account/register">注册</el-menu-item>
            </template>
        </el-menu>
    </div>
</template>

<script lang="js" setup>
import { ref } from "vue";
import { useStore } from "vuex"
import { ElMessageBox } from 'element-plus';
import { useRouter } from 'vue-router';

const activeIndex = ref('1');
const store = useStore();
const router = useRouter();

const logoutConfirm = () => {
    ElMessageBox.confirm(
    '确定要退出登录吗?',
    'Warning',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(() => {
        store.dispatch("logout");
        router.push('/home');
    })
    .catch(() => {

    })
};
</script>

<style scoped>
.header {
    padding-left: 15%;
    padding-right: 15%;
    border: 0;
}

.el-menu--horizontal.el-menu {
    border: 0;
}

.el-menu--horizontal > .el-menu-item:nth-child(4) {
    margin-right: auto;
}
</style>
