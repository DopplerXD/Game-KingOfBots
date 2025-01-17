<template>
    <div>
        <PlayGround v-if="$store.state.pk.status === 'playing'" />
        <MatchGround v-if="$store.state.pk.status === 'matching'" />
        <ResultBoard v-if="$store.state.pk.loser !== 'none'"/>
    </div>
</template>

<script lang="js" setup>
import PlayGround from "@/components/PlayGround.vue";
import MatchGround from "@/components/MatchGround.vue";
import ResultBoard from "@/components/ResultBoard.vue";
import { onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'

const store = useStore();
const socketUrl = `ws://localhost:8090/websocket/${store.state.user.token}`;

let socket = null;

onMounted(() => {
    store.commit("updateOpponent", {
        username: "我的对手",
        photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
    });
    socket = new WebSocket(socketUrl);

    // console.log(socket);

    socket.onopen = () => {
        console.log("connected!");
        store.commit("updateSocket", socket);
    }

    socket.onmessage = msg => {
        const data = JSON.parse(msg.data);
        if (data.event === "start-matching") { // 匹配成功
            console.log(data);
            store.commit("updateOpponent", {
                username: data.opponent_username,
                photo: data.opponent_photo,
            });
            setTimeout(() => {
                store.commit("updateStatus", "playing");
            }, 500);
            store.commit("updateGame", data.game);
        } else if (data.event === "move") {
            console.log(data);
            const game = store.state.pk.gameObject;
            const [snake1, snake2] = game.snakes;
            snake1.set_direction(data.dir1);
            snake2.set_direction(data.dir2);
            // eslint-disable-next-line no-empty
        } else if (data.event === "result") {
            console.log(data);
            const game = store.state.pk.gameObject;
            const [snake1, snake2] = game.snakes;
            if (data.loser === "draw") {
                snake1.status = "die";
                snake2.status = "die";
            } else if (data.loser === "player1") {
                snake1.status = "die";
            } else if (data.loser === "player2") {
                snake2.status = "die";
            }
            store.commit("updateLoser", data.loser);
        }
    }

    socket.onclose = () => {
        console.log("disconnected!");
    }
})

onUnmounted(() => {
    socket.close();
    store.commit("updateStatus", "matching");
})
</script>

<style scoped></style>
