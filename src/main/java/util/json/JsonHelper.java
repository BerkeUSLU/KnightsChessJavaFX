package util.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import knightschess.javafx.controller.GameController;
import knightschess.javafx.controller.HighScoresController;
import knightschess.model.ResultState;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class provides helper methods for writing to and reading from file with the Jackson library
 */
public class JsonHelper {

    /**
     * takes data.json file from resources directory
     * initializes list  of result state
     * checks if file is empty or not
     * if file is not empty reads from file and adds everything to list
     * after that adds {@param resultState} to the list of result state
     * writes list to file as json
     * @param resultState
     */
    public static void write(ResultState resultState) {
        File file = new File(GameController.class.getClassLoader().getResource("data.json").getFile());
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

        try {
            List<ResultState> resultStateList = new ArrayList<>();
            if (file.length() != 0) {
                resultStateList = mapper.readValue(file, new TypeReference<List<ResultState>>() {
                });
            }
            resultStateList.add(resultState);
            writer.writeValue(file, resultStateList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * finds file with help of {@param highScoresController} classLoader
     * @param highScoresController
     * @return data.json file
     */
    public static File read(HighScoresController highScoresController) {
        return new File(highScoresController.getClass().getClassLoader().getResource("data.json").getFile());
    }
}
