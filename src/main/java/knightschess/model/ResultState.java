package knightschess.model;

import lombok.Data;

/**
 * Class representing the state of the result of game.
 */
@Data
public class ResultState {
    private String firstPlayer;
    private String secondPlayer;
    private String winner;

}
