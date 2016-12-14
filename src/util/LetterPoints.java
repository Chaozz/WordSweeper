package util;

import java.util.Hashtable;

/**
 * Table of points of letters. Using singleton pattern.
 */
public class LetterPoints {
    private static Hashtable<Character,Integer> pointTable = new Hashtable<>();
    private static LetterPoints instance;
    private LetterPoints() {
        pointTable.put('E',1);
        pointTable.put('T',1);
        pointTable.put('A',2);
        pointTable.put('O',2);
        pointTable.put('I',2);
        pointTable.put('N',2);
        pointTable.put('S',2);
        pointTable.put('H',2);
        pointTable.put('R',2);
        pointTable.put('D',3);
        pointTable.put('L',3);
        pointTable.put('C',3);
        pointTable.put('U',3);
        pointTable.put('M',3);
        pointTable.put('W',3);
        pointTable.put('F',4);
        pointTable.put('G',4);
        pointTable.put('Y',4);
        pointTable.put('P',4);
        pointTable.put('B',4);
        pointTable.put('V',5);
        pointTable.put('K',5);
        pointTable.put('J',7);
        pointTable.put('X',7);
        pointTable.put('Q',8);
        pointTable.put('Z',8);
    }

    public static int getPoint(char c){
        if (instance==null){
            instance = new LetterPoints();
        }
        return pointTable.get(c);
    }
}
