package server.model;

import junit.framework.TestCase;

public class TestPosition extends TestCase {
    public void testPosition() {
        Position m = new Position(2, 3);
        assertEquals(2, m.row);
        assertEquals(3, m.col);

        Position m2 = new Position(2, 3);
        assertTrue(m2.equals(m));

        Position m3 = new Position(2, 4);
        assertFalse(m3.equals(m));
    }
}