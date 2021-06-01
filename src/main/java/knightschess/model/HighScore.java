package knightschess.model;

import lombok.Data;

/**
 * Class representing the high score of the player.
 */
@Data
public class HighScore {
    private String player;
    private int score;

}