package server.model;

import junit.framework.TestCase;

public class TestPlayer extends TestCase {
    public void testPlayer() {
        Player m = new Player("p1");
        assertEquals("p1", m.getName());
        assertTrue(0 <= m.getOrigin().row);
        assertEquals(0, m.getScore());

        m.setScore(10);
        assertEquals(10, m.getScore());

//        for(int i=0; i<7; i++){
//            for(int j=0;j<7;j++){
//                System.out.println(m.);
//            }
//        }
    }
}