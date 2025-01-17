export default {
    state: {
        status: "matching",
        socket: null,
        opponent_username: "",
        opponent_photo: "",
        gamemap: null,
        id1: 0,
        id2: 0,
        sx1: 0,
        sy1: 0,
        sx2: 0,
        sy2: 0,
        gameObject: null,
        loser: "none", // none, draw, player1, player2
    },
    getters: {

    },
    mutations: {
        updateSocket(state, socket) {
            state.socket = socket;
        },
        updateOpponent(state, opponent) {
            state.opponent_username = opponent.username;
            state.opponent_photo = opponent.photo;
        },
        updateStatus(state, status) {
            state.status = status;
        },
        updateGame(state, game) {
            state.gamemap = game.map;
            state.id1 = game.id1;
            state.id2 = game.id2;
            state.sx1 = game.sx1;
            state.sy1 = game.sy1;
            state.sx2 = game.sx2;
            state.sy2 = game.sy2;
        },
        updateGameObject(state, gameObject) {
            state.gameObject = gameObject;
        },
        updateLoser(state, loser) {
            state.loser = loser;
        },
    },
    actions: {

    },
    modules: {},
};
