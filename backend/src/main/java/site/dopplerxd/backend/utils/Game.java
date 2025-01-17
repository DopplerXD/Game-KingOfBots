package site.dopplerxd.backend.utils;

import com.alibaba.fastjson2.JSONObject;
import site.dopplerxd.backend.consumer.WebSocketServer;
import site.dopplerxd.backend.pojo.Record;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Game extends Thread {
    final private Integer rows;
    final private Integer cols;
    final private Integer innerWallsCount;
    final private int[][] gameMap;
    final private static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
    final private Player player1, player2;
    private Integer nextStep1 = null;
    private Integer nextStep2 = null;
    private ReentrantLock lock = new ReentrantLock();
    private String status = "playing";
    private String loser = ""; // 取值：duel, player1, player2

    public Game(Integer rows, Integer cols, Integer innerWallsCount, Integer id1, Integer id2) {
        this.rows = rows;
        this.cols = cols;
        this.innerWallsCount = innerWallsCount;
        this.gameMap = new int[rows][cols];
        player1 = new Player(id1, rows - 2, 1, new ArrayList<>());
        player2 = new Player(id2, 1, cols - 2, new ArrayList<>());
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setNextStep1(Integer nextStep1) {
        lock.lock();
        try {
            this.nextStep1 = nextStep1;
        } finally {
            lock.unlock();
        }
    }

    public void setNextStep2(Integer nextStep2) {
        lock.lock();
        try {
            this.nextStep2 = nextStep2;
        } finally {
            lock.unlock();
        }
    }

    public int[][] getGameMap() {
        return gameMap;
    }

    public void createGameMap() {
        for (int i= 0; i < 1000; i++) {
            if (this.createWalls()) {
                break;
            }
        }
    }

    private boolean createWalls() {
        boolean[][] g = new boolean[rows][cols];

        // 初始化边界
        for (int i = 0; i < rows; i++) {
            g[i][0] = g[i][cols - 1] = true;
        }
        for (int j = 0; j < cols; j++) {
            g[0][j] = g[rows - 1][j] = true;
        }

        // 随机生成内部墙
        Random random = new Random();
        for (int i = 0; i < innerWallsCount; i++) {
            for (int j = 0; j < 500; j++) {
                int r = random.nextInt(rows - 2) + 1;
                int c = random.nextInt(cols - 2) + 1;
                if (!g[r][c] || g[rows - r - 1][cols - c - 1]) {
                    g[r][c] = g[rows - r - 1][cols - c - 1] = true;
                    break;
                }
            }
        }

        // 检查连通性
        boolean copyG[][] = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                copyG[i][j] = g[i][j];
            }
        }
        if (!checkConnectivity(copyG, rows - 2, 1, 1, cols - 2)) {
            return false;
        }

        // 复制到gameMap
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gameMap[i][j] = g[i][j] ? 1 : 0;
            }
        }
        return true;
    }

    private boolean checkConnectivity(boolean[][] g, int sx, int sy, int ex, int ey) {
        if (sx == ex && sy == ey) {
            return true;
        }
        g[sx][sy] = true;
        for (int i = 0; i < 4; i++) {
            int nx = sx + dx[i], ny = sy + dy[i];
            if (!g[nx][ny] && checkConnectivity(g, nx, ny, ex, ey)) {
                return true;
            }
        }
        return false;
    }

    private boolean nextStep() { // 等待两名玩家的下一步操作
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(100);
                lock.lock();
                try {
                    if (nextStep1 != null && nextStep2 != null) {
                        player1.getSteps().add(nextStep1);
                        player2.getSteps().add(nextStep2);
                        player1.updateSnake();
                        player2.updateSnake();
                        return true;
                    }
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean nextCellValid(Cell cell, List<Cell> snake1, List<Cell> snake2) {
        if (this.gameMap[cell.x][cell.y] == 1) {
            return false;
        }
        for (int i = 0; i < snake1.size() - 1; i++) {
            if (cell.equals(snake1.get(i))) {
                return false;
            }
        }
        for (Cell body : snake2) {
            if (cell.equals(body)) {
                return false;
            }
        }
        return true;
    }

    private void judge() { // 判断两名玩家的下一步操作是否合法
        boolean valid1 = true, valid2 = true;
        List<Cell> snake1 = player1.getSnake();
        List<Cell> snake2 = player2.getSnake();
        Cell nextCell1 = snake1.get(snake1.size() - 1);
        Cell nextCell2 = snake2.get(snake2.size() - 1);
        valid1 = nextCellValid(nextCell1, snake1, snake2);
        valid2 = nextCellValid(nextCell2, snake2, snake1);

        if (!valid1 && !valid2) {
            status = "finished";
            loser = "draw";
        } else if (!valid1) {
            status = "finished";
            loser = "player1";
        } else if (!valid2) {
            status = "finished";
            loser = "player2";
        }
    }

    private boolean check_next_valid(int x, int y) {
        return gameMap[x][y] != 1;
    }

    private void sendAllMessage(String message) {
        WebSocketServer.users.get(player1.getId()).sendMessage(message);
        WebSocketServer.users.get(player2.getId()).sendMessage(message);
    }

    private void sendMove() { // 向两名玩家发送下一步操作
        lock.lock();
        try {
            JSONObject res = new JSONObject();
            res.put("event", "move");
            res.put("dir1", nextStep1);
            res.put("dir2", nextStep2);
            sendAllMessage(res.toJSONString());
            nextStep1 = nextStep2 = null;
        } finally {
            lock.unlock();
        }
    }

    private String gameMapToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                stringBuilder.append(gameMap[i][j]);
            }
        }
        return stringBuilder.toString();
    }

    private void saveToDatabase() {
        Record record = new Record(
                null,
                player1.getId(),
                player2.getId(),
                player1.getSnake().get(0).x,
                player1.getSnake().get(0).y,
                player2.getSnake().get(0).x,
                player2.getSnake().get(0).y,
                player1.stepsToString(),
                player2.stepsToString(),
                gameMapToString(),
                this.loser,
                new Date()
        );
        WebSocketServer.recordMapper.insert(record);
    }

    private void sendResult() { // 向两个玩家发送游戏结果
        JSONObject res = new JSONObject();
        res.put("event", "result");
        res.put("loser", loser);
        saveToDatabase();
        sendAllMessage(res.toJSONString());
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) { // 是否获取了两名玩家的下一步操作
            if (nextStep()) {
                judge();
                if ("playing".equals(status)) {
                    sendMove();
                } else {
                    sendResult();
                    break;
                }
            } else {
                status = "finished";
                lock.lock();
                try {
                    if (nextStep1 == null && nextStep2 == null) {
                        loser = "draw";
                    } else if (nextStep1 == null) {
                        loser = "player1";
                    } else {
                        loser = "player2";
                    }
                } finally {
                    lock.unlock();
                }
                sendResult();
                break;
            }

        }
    }
}
