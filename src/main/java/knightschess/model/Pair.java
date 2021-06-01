package knightschess.model;

import lombok.Data;

/**
 * Class representing the cell of the board as a row and column pair.
 */
@Data
public class Pair {
    private int row;
    private int column;

    public Pair(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
