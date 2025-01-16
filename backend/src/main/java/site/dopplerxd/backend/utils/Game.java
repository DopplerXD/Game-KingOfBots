package site.dopplerxd.backend.utils;

import java.util.Random;

public class Game {
    final private Integer rows;
    final private Integer cols;
    final private Integer innerWallsCount;
    final private int[][] gameMap;
    final private static int[] dx = {0, 1, 0, -1}, dy = {1, 0, -1, 0};

    public Game(Integer rows, Integer cols, Integer innerWallsCount) {
        this.rows = rows;
        this.cols = cols;
        this.innerWallsCount = innerWallsCount;
        this.gameMap = new int[rows][cols];
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
}
