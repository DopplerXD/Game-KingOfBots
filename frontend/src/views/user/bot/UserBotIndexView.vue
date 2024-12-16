<template>
    <div class="container">
        <el-row :gutter="20">
            <el-col :span="6">
                <el-card>
                    <img :src="$store.state.user.photo" alt="" style="width: 100%" />
                </el-card>
            </el-col>
            <el-col :span="18">
                <el-card style="width: 100%">
                    <template #header>
                        <div
                            style="
                                display: flex;
                                align-items: center;
                                justify-content: space-between;
                            "
                        >
                            <span style="font-size: 120%">我的 Bot</span>
                            <el-button type="primary" @click="botEditorVisible = true"
                                >创建 Bot</el-button
                            >
                        </div>
                    </template>
                    <el-table :data="bots" style="width: 100%">
                        <el-table-column prop="id" label="ID" width="40" align="center" />
                        <el-table-column prop="title" label="名称" width="200" />
                        <el-table-column prop="createTime" label="创建时间" width="200" />
                        <el-table-column prop="rating" label="rating" width="100" />
                        <el-table-column label="操作">
                            <template #defalut="scope">
                                <el-button type="primary" size="small" @click="updateBotInfo(scope.row.id)">修改</el-button>
                                <el-button type="danger" size="small" @click="deleteBot(scope.row.id)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>

                    <el-dialog
                        v-model="botEditorVisible"
                        title="设计你的 Bot"
                        width="45vw"
                        :before-close="handleClose"
                    >
                        <el-form
                            :model="form"
                            label-width="auto"
                            style="max-width: 100%"
                            label-position="top"
                        >
                            <el-form-item label="名称" :required="true">
                                <el-input
                                    v-model="newbot.title"
                                    :max-length="100"
                                    show-word-limit
                                    placeholder="请输入Bot名称"
                                />
                            </el-form-item>
                            <el-form-item label="描述">
                                <el-input
                                    v-model="newbot.description"
                                    type="textarea"
                                    :max-length="300"
                                    show-word-limit
                                    :rows="3"
                                    placeholder="请输入Bot描述"
                                />
                            </el-form-item>
                            <el-form-item label="代码">
                                <el-input
                                    v-model="newbot.content"
                                    type="textarea"
                                    :max-length="10000"
                                    show-word-limit
                                    :rows="7"
                                    placeholder="请输入Bot代码"
                                />
                            </el-form-item>
                        </el-form>
                        <template #footer>
                            <div class="bot-editor-footer">
                                <span class="error-message">{{ newbot.error_message }}</span>
                                <el-button
                                    @click="
                                        botEditorVisible = false;
                                        cleanNewBot();
                                    "
                                    >取消</el-button
                                >
                                <el-button type="primary" @click="addBot(newbot)"> 确定 </el-button>
                            </div>
                        </template>
                    </el-dialog>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script lang="js" setup>
import { ref, onMounted, reactive } from 'vue'
import { useStore } from 'vuex'
import request from '@/axios'

const store = useStore();
const botEditorVisible = ref(false);

let bots = ref([]);
const newbot = reactive({
    title: '',
    description: '',
    content: '',
    error_message: '',
});

onMounted(() => {
    getBotList();
});

const cleanNewBot = () => {
    newbot.title.value = '';
    newbot.title.description = '';
    newbot.title.content = '';
};

const getBotList = () => {
    request({
        method: "GET",
        url: "/user/bot/list",
        headers: {
            "Authorization": "Bearer " + store.state.user.token,
        },
    })
        .then((response) => {
            bots.value = response.data;
        })
        .catch(() => {
        });
};

const addBot = (bot) => {
    bot.error_message = '';
    request({
        method: "POST",
        url: "/user/bot/add",
        data: {
            title: bot.title,
            description: bot.description,
            content: bot.content,
        },
        headers: {
            "Authorization": "Bearer " + store.state.user.token,
            "Content-Type": "application/x-www-form-urlencoded",
        },
    })
        .then((response) => {
            if (response.data.error_message === "success") {
                getBotList();
                botEditorVisible.value = false;
                cleanNewBot();
            } else {
                newbot.error_message = response.data.error_message;
            }
        })
        .catch(() => {
        });
};

const updateBot = (bot, updateBot_id) => {
    bot.error_message = '';
    request({
        method: "POST",
        url: "/user/bot/update",
        data: {
            bot_id: updateBot_id,
            title: bot.title,
            description: bot.description,
            content: bot.content,
        },
        headers: {
            "Authorization": "Bearer " + store.state.user.token,
            "Content-Type": "application/x-www-form-urlencoded",
        },
    })
        .then((response) => {
            if (response.data.error_message === "success") {
                getBotList();
                botEditorVisible.value = false;
                cleanNewBot();
            } else {
                newbot.error_message = response.data.error_message;
            }
        })
        .catch(() => {
        });
};
</script>

<style scoped>
.container {
    width: 55vw;
    margin-top: 20px;
}
.error-message {
    color: red;
    margin-right: 12px;
}
</style>
