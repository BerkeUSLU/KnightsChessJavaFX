package knightschess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the state of the chess board.
 */
public class ChessBoardState {
    public List<List<Integer>> chessBoard =  new ArrayList<List<Integer>>(64);

    public List<Pair> possibleMoves = new ArrayList<>();

    /**
     * initializes all cells of the chess board
     */
    public void initializeBoard(){
        for(int i = 0; i < 8; i++){
            chessBoard.add(new ArrayList<>());
            for(int j = 0; j < 8; j++){
                chessBoard.get(i).add(0);
            }
        }
        chessBoard.get(0).set(0,2);
        chessBoard.get(7).set(7,3);
    }

    /**
     * shows possible moves for knight
     * initializes List of pairs possibleMoves
     * checks if row +- 1 is in boundaries then checks column +-2 is in boundaries
     * checks also if row +- 2 is in boundaries then checks column +-1 is in boundaries
     * if passes successfully from these checks after that checks state of pair in chess board is 0
     * if passes successfully adds pair to List of pairs possibleMoves
     * @param pair
     * @return List of pairs possibleMoves
     */
    public List<Pair> showPossibleMoves(Pair pair) {
        List<Pair> possibleMoves = new ArrayList<>();
        int row = pair.getRow();
        int col = pair.getColumn();


        if(row - 1 >= 0 && row - 1 <= 7) {
            if(col - 2 >= 0 && col - 2 <= 7){
                if(chessBoard.get(row-1).get(col-2) == 0) {
                    possibleMoves.add(new Pair(row - 1,col - 2));
                }
            }
            if(col + 2 >= 0 && col + 2 <= 7){
                if(chessBoard.get(row-1).get(col+2) == 0) {
                    possibleMoves.add(new Pair(row - 1,col + 2));
                }
            }
        }
        if(row + 1 >= 0 && row + 1 <= 7) {
            if(col - 2 >= 0 && col - 2 <= 7){
                if(chessBoard.get(row+1).get(col-2) == 0) {
                    possibleMoves.add(new Pair(row + 1,col - 2));
                }
            }
            if(col + 2 >= 0 && col + 2 <= 7){
                if(chessBoard.get(row+1).get(col+2) == 0) {
                    possibleMoves.add(new Pair(row + 1,col + 2));
                }
            }
        }
        if(col - 1 >= 0 && col - 1 <= 7) {
            if(row - 2 >= 0 && row - 2 <= 7){
                if(chessBoard.get(row-2).get(col-1) == 0) {
                    possibleMoves.add(new Pair(row - 2,col - 1));
                }
            }
            if(row + 2 >= 0 && row + 2 <= 7){
                if(chessBoard.get(row+2).get(col-1) == 0) {
                    possibleMoves.add(new Pair(row + 2,col - 1));
                }
            }
        }
        if(col + 1 >= 0 && col + 1 <= 7) {
            if(row - 2 >= 0  && row - 2 <= 7){
                if(chessBoard.get(row-2).get(col+1) == 0) {
                    possibleMoves.add(new Pair(row - 2,col + 1));
                }
            }
            if(row + 2 >= 0 && row + 2 <= 7){
                if(chessBoard.get(row+2).get(col+1) == 0) {
                    possibleMoves.add(new Pair(row + 2,col + 1));
                }
            }
        }
        return possibleMoves;
    }

    /**
     * initializes position pair with first element of moveList
     * initializes destination pair with {@param row} and {@param col}
     * initializes chess board state with {@param row} and {@param col}
     * checks list of possible moves contains destination pair
     * checks state is not equal to 2 and not equal to 3 and not equal to 1
     * if passes successfully from these checks then calls {@see changeKnightState()}
     * @param playerState
     * @param row
     * @param col
     * @return true or false based on checks
     */
    public boolean isKnightMoveValid(PlayerState playerState,int row, int col) {
        Pair posPair = playerState.getMoveList().get(0);
        Pair destPair = new Pair(row, col);
        int state = chessBoard.get(row).get(col);
        if (possibleMoves.contains(destPair) && state != 2 && state != 3  && state != 1) {
            changeKnightState(playerState, row, col, posPair);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * checks if first player's turn changes state of board to 2
     * otherwise changes state of board to 3
     * after all changes state of position pair coordinate to 1
     * @param playerState
     * @param row
     * @param col
     * @param posPair
     */
    public void changeKnightState(PlayerState playerState, int row, int col, Pair posPair) {
        if(playerState.isPlayer1Turn()) {
            chessBoard.get(row).set(col, 2);
        }
        else {
            chessBoard.get(row).set(col, 3);
        }
        chessBoard.get(posPair.getRow()).set(posPair.getColumn(),1);
    }

    /**
     * if list of possible moves is empty it means game is finished
     * @return true or false based on game finish
     */
    public boolean isGameFinished() {
        if(possibleMoves.isEmpty()){
            return true;
        }
        return false;
    }
}
