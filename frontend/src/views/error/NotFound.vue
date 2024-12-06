<template>
<div class="container">
    <el-col :sm="12" :lg="6">
        <el-result icon="error" title="Error Tip" sub-title="Please follow the instructions">
            <template #extra>
                <el-button type="primary">Back</el-button>
            </template>
        </el-result>
    </el-col>
</div>
</template>

<script lang="js" setup></script>

<style scoped>
.container {
    display: flex;
    justify-content: center;
    align-content: center;
    height: 100%;
}
.el-col {
    display: flex;
    justify-content: center;
    align-content: center;
    height: 70%;
}
</style>
