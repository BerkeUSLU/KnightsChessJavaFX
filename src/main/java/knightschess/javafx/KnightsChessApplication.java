package knightschess.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import util.javafx.ControllerHelper;

public class KnightsChessApplication extends Application {
    private FXMLLoader fxmlLoader = new FXMLLoader();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Flipping Coins");
        stage.setResizable(false);
        ControllerHelper.loadAndShowFXML(fxmlLoader,"/fxml/launch.fxml",stage);
    }
}
