package knightschess.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardStateTest {

    @Test
    void initializeBoard() {
        ChessBoardState chessBoardState = new ChessBoardState();

        chessBoardState.initializeBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!(i == 0 && j == 0) && !(i == 7 && j == 7)) {
                    assertEquals(0, chessBoardState.chessBoard.get(i).get(j));
                }
            }
        }
        assertEquals(2, chessBoardState.chessBoard.get(0).get(0));
        assertEquals(3, chessBoardState.chessBoard.get(7).get(7));
    }

    @Test
    void isGameFinished() {
        ChessBoardState chessBoardState = new ChessBoardState();

        assertTrue(chessBoardState.isGameFinished());

        chessBoardState.possibleMoves.add(new Pair(0, 0));
        assertFalse(chessBoardState.isGameFinished());

    }

    @Test
    void showPossibleMoves() {
        ChessBoardState chessBoardState = new ChessBoardState();

        Pair pair = new Pair(3, 3);
        chessBoardState.initializeBoard();

        chessBoardState.chessBoard.get(3).set(3, 2);
        chessBoardState.chessBoard.get(2).set(1, 1);
        chessBoardState.chessBoard.get(5).set(4, 3);

        chessBoardState.possibleMoves = chessBoardState.showPossibleMoves(pair);
        List<Pair> expectedPairs = new ArrayList<>();
        expectedPairs.add(new Pair(1, 2));
        expectedPairs.add(new Pair(1, 4));
        expectedPairs.add(new Pair(2, 5));
        expectedPairs.add(new Pair(4, 1));
        expectedPairs.add(new Pair(4, 5));
        expectedPairs.add(new Pair(5, 2));

        assertTrue(chessBoardState.possibleMoves.containsAll(expectedPairs));
    }
    @Test
    void changeKnightState() {
        ChessBoardState chessBoardState = new ChessBoardState();
        PlayerState playerState = new PlayerState();
        chessBoardState.initializeBoard();

        Pair posPair = new Pair(3,3);
        int row = 1;
        int col = 2;
        chessBoardState.changeKnightState(playerState,row,col,posPair);

        assertEquals(2,chessBoardState.chessBoard.get(row).get(col));
        assertEquals(1,chessBoardState.chessBoard.get(posPair.getRow()).get(posPair.getColumn()));

        playerState.setPlayer1Turn(false);
        posPair = new Pair(4,4);
        row = 3;
        col = 6;
        chessBoardState.changeKnightState(playerState,row,col,posPair);

        assertEquals(3,chessBoardState.chessBoard.get(row).get(col));
        assertEquals(1,chessBoardState.chessBoard.get(posPair.getRow()).get(posPair.getColumn()));

    }

    @Test
    void isKnightMoveValid() {
        ChessBoardState chessBoardState = new ChessBoardState();
        PlayerState playerState = new PlayerState();
        chessBoardState.initializeBoard();
        chessBoardState.possibleMoves.add(new Pair(3,3));
        playerState.getMoveList().add(new Pair(1,2));

        int row = 3;
        int col = 3;
        assertTrue(chessBoardState.isKnightMoveValid(playerState,row,col));

        assertFalse(chessBoardState.isKnightMoveValid(playerState,row,col));

        chessBoardState.chessBoard.get(3).set(3,1);
        assertFalse(chessBoardState.isKnightMoveValid(playerState,row,col));

        chessBoardState.chessBoard.get(3).set(3,3);
        assertFalse(chessBoardState.isKnightMoveValid(playerState,row,col));

        row = 2;
        col = 2;
        assertFalse(chessBoardState.isKnightMoveValid(playerState,row,col));
    }
}