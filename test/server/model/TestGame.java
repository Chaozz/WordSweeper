package server.model;

import junit.framework.TestCase;
import server.MockClient;

public class TestGame extends TestCase {
    public void testGame() {
        Game m = new Game("g1");
        MockClient mc = new MockClient();
        assertEquals("g1", m.getGameID());
        assertFalse(m.isLocked());
        assertEquals(7, m.getBoard().getSize());

        m.addPlayer(new Player("p1"),mc);
        assertEquals("p1", m.getPlayers().get(0).getName());
        assertEquals("p1", m.getManagingPlayerName());
        assertFalse(m.removePlayer("p2"));

        m.addPlayer(new Player("p2"),mc);
        assertTrue(m.removePlayer("p1"));
        assertEquals("p2", m.getManagingPlayerName());
        m.removePlayer("p2");
        assertNull(m.getManagingPlayerName());

        assertNull(m.getPlayer("p1"));

        m.setLocked(true);
        assertTrue(m.isLocked());
        assertFalse(m.addPlayer(new Player("p1"),mc));
    }
}
