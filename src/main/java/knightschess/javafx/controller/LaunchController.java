package knightschess.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        errorLabel.setText("");

        if(player1TextField.getText().isEmpty() && player2TextField.getText().isEmpty()){
            errorLabel.setText("Please, fill all the fields!");
        }
        else if(player1TextField.getText().isEmpty()){
            errorLabel.setText("Player1, please, fill the field!");
        }
        else if(player2TextField.getText().isEmpty()){
            errorLabel.setText("Player2, please, fill the field!");
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
        }
    }

    public void highScoresAction(ActionEvent actionEvent) throws IOException{

    }

}
