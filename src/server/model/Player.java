package server.model;

import java.util.Random;

/**
 * Entity class of Player.
 */
public class Player {
    Position origin;
    String name;
    int score;

    /**assertNotNull(m.getMultiplier());

     m.resetMultiplier();
     assertNotNull(m.getMultiplier());
     * constructor
     *
     * @param name
     */
    public Player(String name) {
        this.name = name;
        this.score=0;
        Random r = new Random();
        origin = new Position(r.nextInt(4), r.nextInt(4));
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Position getOrigin() {
        return origin;
    }

    public String getName() {
        return name;
    }
}
