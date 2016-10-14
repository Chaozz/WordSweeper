package server.model;

import java.util.Hashtable;
import java.util.Random;

/**
 * Entity class of Board.
 */
public class Board {
    final String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "k", "L", "M", "N", "O", "P", "Qu",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public Hashtable<Position, Cell> cells = new Hashtable<Position, Cell>();
    int size;

    /**
     * construct the Board with fixed sized and random letters.
     *
     * @param size
     */
    public Board(int size) {
        this.size = size;
        Random r = new Random();
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                cells.put(new Position(i, j), new Cell(new Letter(alphabet[r.nextInt(alphabet.length)], 0), false));
            }
        }
    }

    /**
     * reset the board.
     */
    public void resetBoard() {
        Random r = new Random();
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                cells.put(new Position(i, j), new Cell(new Letter(alphabet[r.nextInt(alphabet.length)], 0), false));
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Hashtable<Position, Cell> getCells() {
        return cells;
    }

    public void setCells(Position position, Cell cell) {
        cells.put(position, cell);
    }
}
