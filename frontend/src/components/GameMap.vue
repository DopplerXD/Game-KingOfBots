<template>
    <div ref="parent" class="gamemap">
        <canvas ref="canvas" tabindex="0"> </canvas>
    </div>
</template>

<script lang="js" setup>
import { ref, onMounted } from "vue";
import { GameMap } from "@/assets/scripts/GameMap";
import { useStore } from 'vuex'

let parent = ref(null);
let canvas = ref(null);
const store = useStore();

onMounted(() => {
    store.commit("updateGameObject",
        new GameMap(canvas.value.getContext('2d'), parent.value, store)
    );
})
</script>

<style scoped>
div.gamemap {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>
