<template>
  <div>
    <el-menu
      class="header"
      :default-active="activeIndex"
      mode="horizontal"
      background-color="#1f2123"
      text-color="#fff"
      active-text-color="#ffd04b"
      :ellipsis="false"
      router="true"
      close-on-click-outside="true"
    >
      <!-- <el-menu-item index="/">King Of Bots</el-menu-item> -->
      <el-menu-item index="/pk">对战</el-menu-item>
      <el-menu-item index="/record">对局列表</el-menu-item>
      <el-menu-item index="/ranklist">排行榜</el-menu-item>
      <el-sub-menu index="2">
        <template #title>个人中心</template>
        <el-menu-item index="/user/bot">我的 Bot</el-menu-item>
        <el-menu-item index="2-2">退出</el-menu-item>
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

.el-menu--horizontal > .el-menu-item:nth-child(3) {
  margin-right: auto;
}
</style>
