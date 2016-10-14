package server.model;

/**
 * Entity class of Letter
 */
public class Letter {
    String character;
    int point;

    /**
     * Letter constructor
     *
     * @param character the letter
     * @param point     the score
     */
    public Letter(String character, int point) {
        this.character = character;
        this.point = point;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPoint() {
        return point;
    }
}

