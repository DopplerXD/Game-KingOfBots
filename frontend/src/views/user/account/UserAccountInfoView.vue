<template>
    <div>
        <ContentField>
            <el-descriptions title="我的信息" border>
                <el-descriptions-item :rowspan="2" :width="140" label="Photo" align="center">
                    <el-image style="width: 100px; height: 100px" :src=store.state.user.photo />
                </el-descriptions-item>
                <el-descriptions-item label="id">{{ store.state.user.id }}</el-descriptions-item>
                <el-descriptions-item label="Username">{{ store.state.user.username }}</el-descriptions-item>
            </el-descriptions>
        </ContentField>
    </div>
</template>

<script lang="js" setup>
import ContentField from "@/components/ContentField.vue"
import { useStore } from 'vuex';

const store = useStore();

</script>

<style scoped></style>
