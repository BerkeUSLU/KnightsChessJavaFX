package knightschess.model;

import java.util.ArrayList;
import java.util.List;

public class ChessBoardState {
    public static List<List<Integer>> chessBoard = new ArrayList<List<Integer>>(64);

    public void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            chessBoard.add(new ArrayList<>());
            for (int j = 0; j < 8; j++) {
                chessBoard.get(i).add(0);
            }
        }
        chessBoard.get(0).set(0, 2);
        chessBoard.get(7).set(7, 3);
    }
}
