package site.dopplerxd.backend.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cell {
    int x, y;

    public boolean equals(Cell cell) {
        return cell.x == this.x && cell.y == this.y;
    }
}
