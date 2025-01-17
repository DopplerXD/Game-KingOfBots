package site.dopplerxd.backend.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Integer id;
    private Integer sx;
    private Integer sy;
    private List<Integer> steps;
    private List<Cell> snake;
    final private static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    public Player(Integer id, Integer sx, Integer sy, List<Integer> steps) {
        this.id = id;
        this.sx = sx;
        this.sy = sy;
        this.steps = steps;
        this.snake = new ArrayList<>();
        snake.add(new Cell(sx, sy));
    }

    private boolean checkTailIncreasing() {
        return this.steps.size() <= 10 || this.steps.size() % 3 == 1;
    }

    public void updateSnake() {
        int x = this.sx + dx[steps.get(steps.size() - 1)];
        int y = this.sy + dy[steps.get(steps.size() - 1)];
        snake.add(new Cell(x, y));
        if (!checkTailIncreasing()) {
            snake.remove(0);
        }
        this.sx = x;
        this.sy = y;
    }

    public String stepsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i : steps) {
            stringBuilder.append(i);
        }
        return stringBuilder.toString();
    }
}
