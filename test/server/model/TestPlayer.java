package server.model;

import junit.framework.TestCase;

public class TestPlayer extends TestCase {
    public void testPlayer() {
        Player m = new Player("p1");
        assertEquals("p1", m.getName());
        assertTrue(0 <= m.getOrigin().getRow());
        assertTrue(0 <= m.getOrigin().getCol());
        assertEquals(0, m.getScore());

        m.setScore(10);
        assertEquals(10, m.getScore());
    }
}