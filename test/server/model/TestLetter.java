package server.model;

import junit.framework.TestCase;

public class TestLetter extends TestCase{

    public void testLetter() {
        Letter m = new Letter("Qu", 10);
        assertEquals("Qu", m.getCharacter());
        assertEquals(10, m.getPoint());

        m.setCharacter("X");
        assertEquals("X", m.getCharacter());

        m.setPoint(0);
        assertEquals(0, m.getPoint());
    }

}