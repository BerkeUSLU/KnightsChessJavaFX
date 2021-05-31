package util.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import knightschess.javafx.controller.GameController;
import knightschess.model.ResultState;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {
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

}
