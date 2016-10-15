package server.model;

import junit.framework.TestCase;

import java.util.*;

public class TestBoard extends TestCase {
    public void testBoard() {
        Board m = new Board(7);
        assertNotNull(m.getMultiplier());
        m.resetMultiplier();
        assertNotNull(m.getMultiplier());

        Set<String> VALUES = new HashSet<String>(Arrays.asList(m.alphabet));
        for (int i = 0; i < m.size; i++) {
            for (int j = 0; j < m.size; j++) {
                System.out.print(m.getCells().get(new Position(i, j)).getLetter().getCharacter());
                assertTrue(VALUES.contains(m.getCells().get(new Position(i, j)).getLetter().getCharacter()));
            }
        }

        m.resetBoard();
        for (int i = 0; i < m.size; i++) {
            for (int j = 0; j < m.size; j++) {
                assertTrue(VALUES.contains(m.getCells().get(new Position(i, j)).getLetter().getCharacter()));
            }
        }

        Position p = new Position(1, 1);
        Cell cell = new Cell(new Letter("Qu", 0), false);
        m.setCells(p, cell);
        assertEquals(cell, m.getCells().get(p));

        m.resizeBoard(8);
        assertEquals(8, m.getSize());
        assertEquals(m.getBoardContent().substring(0, 1), m.getCells().get(new Position(0,0)).getLetter().getCharacter());
    }
}