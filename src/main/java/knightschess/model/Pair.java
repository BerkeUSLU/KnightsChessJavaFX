package knightschess.model;

import lombok.Data;

@Data
public class Pair {
    private int row;
    private int column;

    public Pair(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
