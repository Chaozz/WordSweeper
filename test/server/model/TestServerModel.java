package server.model;

import junit.framework.TestCase;
import server.MockClient;

public class TestServerModel extends TestCase {

    public void testServerModel() {
        ServerModel m = new ServerModel();
        MockClient mc=new MockClient();

        String gameID = m.createGame("p1",mc);
        assertNotNull(m.getGame(gameID));
        assertNull(m.getGame("asd"));
        assertTrue(m.joinGame("p2", mc,gameID));

        assertTrue(m.removeGame(gameID));
        assertFalse(m.removeGame(gameID));
        assertFalse(m.joinGame("p3", mc,gameID));
        assertEquals(0, m.getGames().size());
    }
}
