package server.model;

/**
 * Entity class of Cell.
 */
public class Cell {
    Letter letter;
    boolean isMultiplier;

    /**
     * constructor
     *
     * @param letter
     * @param isMultiplier
     */
    public Cell(Letter letter, boolean isMultiplier) {
        this.letter = letter;
        this.isMultiplier = isMultiplier;
    }

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public boolean isMultiplier() {
        return isMultiplier;

    }

    public void setMultiplier(boolean multiplier) {
        isMultiplier = multiplier;
    }
}
