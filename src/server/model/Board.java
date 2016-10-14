package server.model;

import java.util.Hashtable;
import java.util.Random;

/**
 * Entity class of Board.
 */
public class Board {
    final String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Qu",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    public Hashtable<Position, Cell> cells = new Hashtable<Position, Cell>();
    int size;
    StringBuffer content;


    /**
     * construct the Board with fixed sized and random letters.
     *
     * @param size
     */

    public int expandSize(int size) {
        this.size = size;
        return size;
    }

    public Board(int size) {
        this.size = size;
        resetBoard();
        initMultiplier();
    }

    /**
     * reset the board.
     */
    public void resetBoard() {
        content = new StringBuffer();
        Random r = new Random();
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                String rAlphabet = alphabet[r.nextInt(alphabet.length)];
                content.append(rAlphabet);
                content.append(",");
                cells.put(new Position(i, j), new Cell(new Letter(rAlphabet, 0), false));
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

    public Position getMultiplier(){
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                Position position = new Position(i,j);
                if (cells.get(position).isMultiplier()){
                    return position;
                }
            }
        }
        return null;
    }

    /**
     * Init the unique multiplier.
     */
    public void initMultiplier(){
        Random r =new Random();
        cells.get(new Position(r.nextInt(size), r.nextInt(size))).setMultiplier(true);
    }

    /**
     * Reset the multiplier.
     */
    public void resetMultiplier(){
        Position p=getMultiplier();
        if(p!=null){
            cells.get(p).setMultiplier(false);
        }
        initMultiplier();
    }

    public String getLocalBoardContent(Position position){
        content = new StringBuffer();
        for (int i = position.getRow(); i < 4+position.getRow(); i++) {
            for (int j = position.getCol(); j < 4+position.getCol(); j++) {
                Position p = new Position(i,j);
                content.append(cells.get(p).getLetter().getCharacter());
                content.append(',');
            }
        }
        return content.toString();
    }

    public String getBoardContent(){
        return content.toString();
    }
}
