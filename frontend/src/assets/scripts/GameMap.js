import { GameObject } from "./GameObect";
import { Wall } from "./Wall";

export class GameMap extends GameObject {
    constructor(ctx, parent) {
        super();

        this.ctx = ctx;
        this.parent = parent;
        this.L = 0;

        this.rows = 13;
        this.cols = 13;

        this.walls = [];
        this.inner_walls_count = 15;
    }

    // 检查连通性
    checkConnect(g, sx, sy, ex, ey) {
        if (sx == ex && sy == ey) return true;
        g[sx][sy] = true;

        let dx = [0, 0, 1, -1], dy = [1, -1, 0, 0];
        for (let k = 0; k < 4; k++) {
            let x = sx + dx[k], y = sy + dy[k];
            if (g[x][y]) continue;
            if (this.checkConnect(g, x, y, ex, ey)) return true;
        }
        return false;
    }

    create_walls() {
        const g = [];
        for (let r = 0; r < this.rows; r++) {
            g[r] = [];
            for (let c = 0; c < this.cols; c++) {
                g[r][c] = false;
            }
        }

        // 四周放墙
        for (let r = 0; r < this.rows; r++) {
            g[r][0] = g[r][this.cols - 1] = true;
        }
        for (let c = 0; c < this.cols; c++) {
            g[0][c] = g[this.rows - 1][c] = true;
        }

        // 内部随机障碍物
        for (let i = 0; i < this.inner_walls_count; i++) {
            for (let j = 0; j < 500; j++) {
                let r = parseInt(Math.random() * this.rows);
                let c=  parseInt(Math.random() * this.cols);
                if (g[r][c]) continue;
                if ((r == this.rows - 2 && c == 1) || (r == 1 && c == this.rows - 2)) continue;
                g[r][c] = g[c][r] = true;
                break;
            }
        }

        const copy_g = JSON.parse(JSON.stringify(g));
        if (!this.checkConnect(copy_g, this.rows - 2, 1, 1, this.cols - 2)) return false;

        for (let r = 0; r < this.rows; r++) {
            for (let c = 0; c < this.cols; c++) {
                if (g[r][c]) {
                    this.walls.push(new Wall(r, c, this));
                }
            }
        }

        return true;
    }

    start() {        
        for (let i = 0; i < 100; i++)
            if (this.create_walls())
                break;
    }

    update_size() {
        this.L = parseInt(Math.min(
            this.parent.clientWidth / this.cols,
            this.parent.clientHeight / this.rows
        ));
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;
    }

    update() {
        this.update_size();
        this.render();
    }

    render() {
        const color_even = "#aad751",
            color_odd = "#a2d048";
        for (let r = 0; r < this.rows; r++) {
            for (let c = 0; c < this.cols; c++) {
                if ((r + c) % 2 == 0) {
                    this.ctx.fillStyle = color_even;
                } else {
                    this.ctx.fillStyle = color_odd;
                }
                this.ctx.fillRect(c * this.L, r * this.L, this.L, this.L);
            }
        }
    }
}
