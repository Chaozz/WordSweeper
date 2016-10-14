package server.model;

import junit.framework.TestCase;

public class TestServerModel extends TestCase {

    public void testServerModel() {
        ServerModel m = new ServerModel();
        assertEquals(0, m.getNumPlayers());

        m.joinGame();
        assertEquals(1, m.getNumPlayers());

        m.joinGame();
        assertEquals(2, m.getNumPlayers());

        String gameID = m.createGame("p1");
        assertNotNull(m.getGame(gameID));
        assertNull(m.getGame("asd"));
        assertTrue(m.joinGame("p2", gameID));

        assertTrue(m.removeGame(gameID));
        assertFalse(m.removeGame(gameID));
        assertFalse(m.joinGame("p3", gameID));
    }
}
