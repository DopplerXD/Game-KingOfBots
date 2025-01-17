<template>
  <div>

  </div>
</template>

<script lang="js" setup>
import {onMounted, ref} from "vue";
import { ElMessageBox } from 'element-plus'
import { useStore } from 'vuex'

const store = useStore();
let result = ref('');

onMounted(() => {
    if (store.state.pk.loser === 'draw') {
        result.value = 'Draw!';
    } else if (store.state.pk.loser === 'player1' && parseInt(store.state.user.id) === store.state.pk.id1) {
        result.value = 'Lose!';
    } else if (store.state.pk.loser === 'player2' && parseInt(store.state.user.id) === store.state.pk.id2) {
        result.value = 'Lose!';
    } else {
        result.value = 'Win!';
    }

    ElMessageBox.confirm('对局结束，是否要再来一局？', result.value, {
        distinguishCancelAndClose: false,
        cancelButtonText: '取消',
        confirmButtonText: '再来一局',
    })
        .then(() => {
            store.commit("updateStatus", "matching"); // 再来一局
            store.commit("updateOpponent", {
                username: "我的对手",
                photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
            });
            store.commit("updateGame", null);
            store.commit("updateLoser", 'none');
        })
        .catch(() => {
            store.commit("updateLoser", 'none');
        })
});
</script>

<style scoped>
</style>