<template>
    <div class="match-ground">
        <el-row>
            <el-col :span="12">
                <div class="user-photo">
                    <img :src="$store.state.user.photo" />
                </div>
                <div class="user-username">
                    {{ $store.state.user.username }}
                </div>
            </el-col>
            <el-col :span="12">
                <div class="user-photo">
                    <img :src="$store.state.pk.opponent_photo" />
                </div>
                <div class="user-username">
                    {{ $store.state.pk.opponent_username }}
                </div>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <div class="start-button">
                    <el-button type="warning" round @click="changeMatchingStatus">
                        {{ matchingStatus }}
                    </el-button>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script lang="js" setup>
import { ref } from "vue";
import { useStore } from 'vuex';

const matchingStatus = ref("开始匹配");
const store = useStore();

const changeMatchingStatus = () => {
    if (matchingStatus.value === "开始匹配") {
        matchingStatus.value = "取消匹配";
        store.state.pk.socket.send(JSON.stringify({
            event: "start-matching",
        }));
    } else {
        matchingStatus.value = "开始匹配";
        store.state.pk.socket.send(JSON.stringify({
            event: "stop-matching",
        }));
    }
};
</script>

<style scoped>
div.match-ground {
    width: 60vw;
    height: 70vh;
    margin: 50px auto;
    background-color: rgba(50, 50, 50, 0.5);
}
div.user-photo {
    text-align: center;
    padding-top: 10vh;
}
div.user-photo > img {
    border-radius: 50%;
    width: 20vh;
}
div.user-username {
    text-align: center;
    font-size: 24px;
    font-weight: 600;
    color: white;
    padding-top: 2vh;
}
div.start-button {
    text-align: center;
    padding-top: 10vh;
}
</style>
