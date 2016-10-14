package server.model;

import java.util.Random;

/**
 * Entity class of Player.
 */
public class Player {
    Position origin;
    String name;
    int score;

    /**
     * constructor
     *
     * @param name
     */
    public Player(String name) {
        this.name = name;
        this.score=0;
        Random r = new Random();
        origin = new Position(r.nextInt(7), r.nextInt(7));
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
