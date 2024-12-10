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
import { onMounted } from 'vue'
// import qs from 'qs';
import request from "./axios";

// const user = ref({
//     username: "su",
//     password: "123"
// });

onMounted(() => {
//     request.post('/user/account/token', qs.stringify(user.value), {
//         headers: {
//             'Content-Type': 'application/x-www-form-urlencoded',
//         }
//     })
//         .then(response => {
//             console.log("success: ", response.data);
//         })
//         .catch(error => {
//             console.log("failed: ", error);
//         });
    request({
        method: "POST",
        url: "/user/account/token",
        data: {
            username: "su",
            password: "123",
        },
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        }
    }).then(response => {
        console.log(response);
    });
});
</script>

<style>
body {
    background-image: url("./assets/images/background.png");
    background-size: cover;
}
</style>
