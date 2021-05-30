package knightschess.model;

import javafx.scene.image.ImageView;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlayerState {
    private List<Pair> moveList;
    private List<ImageView> imageViewList;
    private boolean isPlayer1Turn;

    public PlayerState(){
        moveList = new ArrayList<>();
        imageViewList = new ArrayList<>();
        isPlayer1Turn = true;
    }
}
