package knightschess.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import knightschess.model.ResultState;
import util.javafx.ControllerHelper;

import java.io.IOException;

public class HighScoresController {
    private FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<ResultState, String> player;
    @FXML
    private TableColumn<ResultState, Integer> score;

    public void backAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        ControllerHelper.loadAndShowFXML(fxmlLoader,"/fxml/launch.fxml",stage);
    }
}
