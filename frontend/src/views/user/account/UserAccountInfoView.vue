<template>
    <div>
        <ContentField> Get Info </ContentField>
    </div>
</template>

<script lang="js" setup>
import ContentField from "@/components/ContentField.vue"
import { onMounted } from 'vue'
import request from '@/axios'

onMounted(() => {
    request({
        method: "GET",
        url: "/user/account/info",
    }).then(response => {
        console.log(response);
    }).catch(error => {
        console.log("get info failed " + error);
    })
});
</script>

<style scoped></style>
