package server.model;

import junit.framework.TestCase;

public class TestServerModel extends TestCase {

    public void testServerModel() {
        ServerModel m = new ServerModel();

        String gameID = m.createGame("p1");
        assertNotNull(m.getGame(gameID));
        assertNull(m.getGame("asd"));
        assertTrue(m.joinGame("p2", gameID));

        assertTrue(m.removeGame(gameID));
        assertFalse(m.removeGame(gameID));
        assertFalse(m.joinGame("p3", gameID));
    }
}
