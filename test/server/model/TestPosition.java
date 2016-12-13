package server.model;

import junit.framework.TestCase;

public class TestPosition extends TestCase {
    public void testPosition() {
        Position m = new Position(3, 2);
        assertEquals(2, m.row);
        assertEquals(3, m.col);

        Position m2 = new Position(3, 2);
        assertTrue(m2.equals(m));

        Position m3 = new Position(4, 2);
        assertFalse(m3.equals(m));

        assertFalse(m3.equals(null));
        assertFalse(m3.equals(new String("1")));
    }
}