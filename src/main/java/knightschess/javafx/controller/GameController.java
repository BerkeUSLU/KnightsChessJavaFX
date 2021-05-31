package knightschess.javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import knightschess.model.ChessBoardState;
import knightschess.model.Pair;
import knightschess.model.PlayerState;
import knightschess.model.ResultState;
import util.json.JsonHelper;

import java.util.List;

public class GameController {
    private ChessBoardState chessBoardState = new ChessBoardState();

    private PlayerState playerState = new PlayerState();

    private ResultState resultState = new ResultState();

    @FXML
    private GridPane gridPane;

    @FXML
    private Label player1Label;

    @FXML
    private Label player2Label;

    @FXML
    private Label errorLabel;

    private Image knightBlack = new Image(getClass().getResource("/images/knightBlack.png").toExternalForm());

    private Image knightWhite = new Image(getClass().getResource("/images/knightWhite.png").toExternalForm());

    private Image moveImage = new Image(getClass().getResource("/images/move.png").toExternalForm());

    private Image restrictImage =  new Image(getClass().getResource("/images/restrict.png").toExternalForm());

    public void initializeGameState(String player1, String player2) {
        resultState.setFirstPlayer(player1);
        resultState.setSecondPlayer(player2);
        player1Label.setText(player1);
        player2Label.setText(player2);
    }

    @FXML
    public void initialize(){
        chessBoardState.initializeBoard();
        ChessBoardState.possibleMoves.add(new Pair(-1,-1)); // dummy value for beginning of the game
        System.out.println(playerState.getMoveList());
    }

    public void handleClickOnCell(MouseEvent mouseEvent){
        var row= GridPane.getRowIndex((Node) mouseEvent.getSource());
        var column= GridPane.getColumnIndex((Node) mouseEvent.getSource());
        var state = ChessBoardState.chessBoard.get(row).get(column);

        ImageView imageView = (ImageView) mouseEvent.getTarget();

        if(!gameOver(playerState)) {
            errorLabel.setText("");

            if (checkTurn(state)){
                return;
            }

            if (playerState.getMoveList().isEmpty() && (state == 2 || state == 3)) {
                Pair pair = new Pair(row, column);
                playerState.getMoveList().add(pair);
                playerState.getImageViewList().add(imageView);
                System.out.println(playerState.getMoveList());
                System.out.println(playerState.getImageViewList());
                ChessBoardState.possibleMoves = chessBoardState.showPossibleMoves(pair);
                showPossibleMovesOnBoard(ChessBoardState.possibleMoves);

               if(gameOver(playerState)) {
                   JsonHelper.write(resultState);
               }
            }
            else if (playerState.getMoveList().size() == 1 && state == 0) {

                if (chessBoardState.isKnightMoveValid(playerState, row, column)) {
                    clearPossibleMovesOnBoard(ChessBoardState.possibleMoves);
                    moveKnight(imageView);
                    switchPlayer();
                }
                else {
                    errorLabel.setText("Invalid move!");
                }
            }
        }

    }

    private boolean checkTurn(Integer state) {
        if(playerState.isPlayer1Turn()){
            if(state == 3){
                errorLabel.setText("Invalid turn!");
                return true;
            }
        }
        else{
            if(state == 2){
                errorLabel.setText("Invalid turn!");
                return true;
            }
        }
        return false;
    }

    private void moveKnight(ImageView im) {
        playerState.getImageViewList().get(0).setImage(restrictImage);
        if(playerState.isPlayer1Turn()){
            im.setImage(knightWhite);
        }
        else {
            im.setImage(knightBlack);
        }
    }

    private void switchPlayer() {
        playerState.getMoveList().clear();
        playerState.getImageViewList().clear();
        if(playerState.isPlayer1Turn()) {
            playerState.setPlayer1Turn(false);
        }
        else {
            playerState.setPlayer1Turn(true);
        }
    }

    public boolean gameOver(PlayerState playerState){
        if(chessBoardState.isGameFinished()){
            if(playerState.isPlayer1Turn()){
                resultState.setWinner(resultState.getSecondPlayer());
                errorLabel.setText(resultState.getSecondPlayer() + " won the game!");
            }
            else {
                resultState.setWinner(resultState.getFirstPlayer());
                errorLabel.setText(resultState.getFirstPlayer() + " won the game!");
            }
            return true;
        }
        return false;
    }

    private void showPossibleMovesOnBoard(List<Pair> possibleMoves) {
        System.out.println(possibleMoves);
        for(Pair p: possibleMoves){
            ImageView imageView = getImageViewFromGridPane(gridPane,p.getRow(),p.getColumn());
            imageView.setImage(moveImage);
        }
    }

    private void clearPossibleMovesOnBoard(List<Pair> possibleMoves) {
        System.out.println(possibleMoves);
        for(Pair p: possibleMoves){
            ImageView imageView = getImageViewFromGridPane(gridPane,p.getRow(),p.getColumn());
            imageView.setImage(null);
        }
    }

    private ImageView getImageViewFromGridPane(GridPane gridPane, int row, int col) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                if(node instanceof Pane) {
                    Pane pane = (Pane) node;
                    Node imageNode = pane.getChildren().get(0);
                    ImageView imageView = (ImageView) imageNode;
                    return imageView;
                }
                else{
                    ImageView imageView = (ImageView) node;
                    return imageView;
                }
            }
        }
        return null;
    }
}
