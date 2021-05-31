package knightschess.javafx.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import knightschess.model.HighScore;
import knightschess.model.ResultState;
import util.javafx.ControllerHelper;
import util.json.JsonHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HighScoresController {

    private FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<ResultState, String> player;
    @FXML
    private TableColumn<ResultState, Integer> score;

    @FXML
    public void initialize(){
        player.setCellValueFactory(new PropertyValueFactory<>("player"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));

        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

        List<ResultState> resultStateList;
        ObservableList<HighScore> highScoreObservableList = FXCollections.observableArrayList();

        File file = JsonHelper.read(this);

        try{
            if(file.length() != 0){
                resultStateList = mapper.readValue(file, new TypeReference<List<ResultState>>() {
                });
                Map<String,Long> map = resultStateList.stream()
                        .collect(Collectors.groupingBy(ResultState::getWinner,Collectors.counting()));

                map.entrySet().stream()
                        .sorted(Map.Entry.<String,Long>comparingByValue().reversed())
                        .limit(5)
                        .forEach(entry -> {
                            HighScore highScore = new HighScore();
                            highScore.setPlayer(entry.getKey());
                            highScore.setScore(entry.getValue().intValue());
                            highScoreObservableList.add(highScore);
                        });
            }
            tableView.setItems(highScoreObservableList);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void backAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        ControllerHelper.loadAndShowFXML(fxmlLoader,"/fxml/launch.fxml",stage);
    }
}
