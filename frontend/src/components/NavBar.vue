<template>
    <div>
        <el-menu class="header" :default-active="activeIndex" mode="horizontal" background-color="#545c64"
            text-color="#fff" active-text-color="#ffd04b" :ellipsis=false>
            <el-menu-item index="1">King Of Bots</el-menu-item>
            <el-menu-item index="2">对战</el-menu-item>
            <el-menu-item index="3">对局列表</el-menu-item>
            <el-menu-item index="4">排行榜</el-menu-item>
            <el-sub-menu index="2">
                <template #title>个人中心</template>
                <el-menu-item index="2-1">item one</el-menu-item>
                <el-menu-item index="2-2">item two</el-menu-item>
                <el-menu-item index="2-3">item three</el-menu-item>
            </el-sub-menu>
        </el-menu>
    </div>
</template>

<script lang="js" setup>
import { ref } from "vue";

const activeIndex = ref('1')
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

.el-menu--horizontal>.el-menu-item:nth-child(4) {
    margin-right: auto;
}
</style>