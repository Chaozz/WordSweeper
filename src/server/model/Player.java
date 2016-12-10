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
        setPlayer();
    }

    public void setPlayer() {
        this.score = 0;
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

    public void setOrigin(Position origin) {
        this.origin = origin;
    }

    public String getName() {
        return name;
    }
}
