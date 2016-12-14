package server.model;

/**
 * Entity class of Position.
 */
public class Position {
    int row;
    int col;

    public Position(int col, int row) {
        this.row = row;
        this.col = col;
    }

    @Override
    public int hashCode() {
        return row * 12387 + col;
    }

    /**
     * @param o
     * @return if the two positions are in the same place
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof Position) {
            Position other = (Position) o;
            return row == other.row && col == other.col;
        }
        return false;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return (col+1) + " ," + (row+1);
    }
}
