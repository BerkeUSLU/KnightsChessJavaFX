package knightschess.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;
import util.javafx.ControllerHelper;

import java.io.IOException;

public class LaunchController {

    private FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private TextField player1TextField;

    @FXML
    private TextField player2TextField;

    @FXML
    private Label errorLabel;

    public void startAction(ActionEvent actionEvent) throws IOException {
        Logger.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        errorLabel.setText("");

        if(player1TextField.getText().isEmpty() && player2TextField.getText().isEmpty()){
            errorLabel.setText("Please, fill all the fields!");
            Logger.warn("Empty text fields!!!");
        }
        else if(player1TextField.getText().isEmpty()){
            errorLabel.setText("Player1, please, fill the field!");
            Logger.warn("Empty text field!!!");
        }
        else if(player2TextField.getText().isEmpty()){
            errorLabel.setText("Player2, please, fill the field!");
            Logger.warn("Empty text field!!!");
        }
        else{
            fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/game.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            fxmlLoader.<GameController>getController()
                    .initializeGameState(player1TextField.getText(),player2TextField.getText());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            Logger.info("1st player name is set to {} and 2nd player name is set to {}, " +
                    "loading game scene", player1TextField.getText(),player2TextField.getText());
        }
    }

    public void highScoresAction(ActionEvent actionEvent) throws IOException{
        Logger.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        ControllerHelper.loadAndShowFXML(fxmlLoader,"/fxml/highScores.fxml",stage);
    }

}
