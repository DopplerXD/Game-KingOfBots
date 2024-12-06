<template>
  <div>
    <el-container>
      <el-header>
        <NavBar />
      </el-header>
      <el-main><router-view /></el-main>
    </el-container>
  </div>
</template>

<script lang="js" setup>
import NavBar from "@/components/NavBar.vue";
</script>

<style>
body {
  background-image: url("./assets/images/background.png");
  background-size: cover;
}
</style>
