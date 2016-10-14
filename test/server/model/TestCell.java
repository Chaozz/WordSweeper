package server.model;

import junit.framework.TestCase;

public class TestCell extends TestCase {
    public void testCell() {
        Cell m = new Cell(new Letter("Qu", 0), false);
        assertEquals("Qu", m.getLetter().getCharacter());
        assertEquals(false, m.isMultiplier());

        m.setLetter(new Letter("X", 0));
        assertEquals("X", m.getLetter().getCharacter());

        m.setMultiplier(true);
        assertEquals(true, m.isMultiplier());
    }
}