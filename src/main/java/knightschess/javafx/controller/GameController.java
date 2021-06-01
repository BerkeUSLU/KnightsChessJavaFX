package knightschess.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import knightschess.model.ChessBoardState;
import knightschess.model.Pair;
import knightschess.model.PlayerState;
import knightschess.model.ResultState;
import org.tinylog.Logger;
import util.javafx.ControllerHelper;
import util.json.JsonHelper;

import java.io.IOException;
import java.util.List;

public class GameController {
    private ChessBoardState chessBoardState = new ChessBoardState();

    private PlayerState playerState = new PlayerState();

    private ResultState resultState = new ResultState();

    private FXMLLoader fxmlLoader = new FXMLLoader();

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

    private String backGroundStyle = "-fx-background-color: #6b3e2e;";

    public void initializeGameState(String player1, String player2) {
        resultState.setFirstPlayer(player1);
        resultState.setSecondPlayer(player2);
        player1Label.setText(player1);
        player2Label.setText(player2);
    }

    @FXML
    public void initialize(){
        chessBoardState.initializeBoard();
        chessBoardState.possibleMoves.add(new Pair(-1,-1));
    }

    public void handleClickOnCell(MouseEvent mouseEvent){
        var row= GridPane.getRowIndex((Node) mouseEvent.getSource());
        var column= GridPane.getColumnIndex((Node) mouseEvent.getSource());
        var state = chessBoardState.chessBoard.get(row).get(column);

        ImageView imageView = (ImageView) mouseEvent.getTarget();

        Logger.debug("Cell ({},{}) is pressed",row, column);

        if(!gameOver(playerState)) {
            errorLabel.setText("");

            if (checkTurn(state)){
                return;
            }

            if (playerState.getMoveList().isEmpty() && (state == 2 || state == 3)) {
                Pair pair = new Pair(row, column);
                playerState.getMoveList().add(pair);
                playerState.getImageViewList().add(imageView);

                Logger.debug(playerState.getMoveList());
                Logger.debug(playerState.getImageViewList());

                chessBoardState.possibleMoves = chessBoardState.showPossibleMoves(pair);
                showPossibleMovesOnBoard(chessBoardState.possibleMoves);

                if(gameOver(playerState)){
                    JsonHelper.write(resultState);
                }
            }
            else if (playerState.getMoveList().size() == 1 && state == 0) {

                if (chessBoardState.isKnightMoveValid(playerState, row, column)) {
                    clearPossibleMovesOnBoard(chessBoardState.possibleMoves);
                    moveKnight(imageView);
                    switchPlayer();
                }
                else {
                    errorLabel.setText("Invalid move!");
                    Logger.warn("Invalid move");
                }
            }
        }

    }

    private boolean checkTurn(Integer state) {
        if(playerState.isPlayer1Turn()){
            if(state == 3){
                errorLabel.setText("Invalid turn!");
                Logger.warn("Invalid turn");
                return true;
            }
        }
        else{
            if(state == 2){
                errorLabel.setText("Invalid turn!");
                Logger.warn("Invalid turn");
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
            Logger.debug("Switched to {} ",player2Label.getText());
        }
        else {
            playerState.setPlayer1Turn(true);
            Logger.debug("Switched to {} ",player1Label.getText());
        }
        changePlayerLabel(playerState);
    }

    private void changePlayerLabel(PlayerState playerState){
        if(!playerState.isPlayer1Turn()) {
            player1Label.setStyle(null);
            player1Label.setTextFill(Color.rgb(107,62,46));
            player2Label.setStyle(backGroundStyle);
            player2Label.setTextFill(Color.WHITE);
        }
        else {
            player2Label.setStyle(null);
            player2Label.setTextFill(Color.rgb(107,62,46));
            player1Label.setStyle(backGroundStyle);
            player1Label.setTextFill(Color.WHITE);
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
            Logger.warn("End Game!!!");
            return true;
        }
        return false;
    }

    private void showPossibleMovesOnBoard(List<Pair> possibleMoves) {
        Logger.debug(possibleMoves);
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

    public void mainMenuAction(ActionEvent actionEvent) throws IOException {
        Logger.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        ControllerHelper.loadAndShowFXML(fxmlLoader,"/fxml/launch.fxml",stage);
    }

    public void highScoresAction(ActionEvent actionEvent) throws IOException {
        Logger.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        ControllerHelper.loadAndShowFXML(fxmlLoader,"/fxml/highScores.fxml",stage);
    }
}
