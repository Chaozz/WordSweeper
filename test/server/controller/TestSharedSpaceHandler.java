package server.controller;

import junit.framework.TestCase;
import server.MockClient;
import server.model.Game;
import server.model.Player;

public class TestSharedSpaceHandler extends TestCase {
    public void testSharedSpaceHandler() {
        Game m = new Game("g1");
        MockClient mc = new MockClient();
        SharedSpaceHandler ss=new SharedSpaceHandler();
        assertEquals("g1", m.getGameID());
        assertFalse(m.isLocked());
        assertEquals(7, m.getBoard().getSize());

        m.addPlayer(new Player("p1"),mc);
        assertEquals("p1", m.getPlayers().get(0).getName());
        assertEquals("p1", m.getManagingPlayerName());
        assertEquals(1, SharedSpaceHandler.numOfSharedPlayers(m,m.getPlayer("p1").getOrigin()));
        m.addPlayer(new Player("p2"),mc);
        m.addPlayer(new Player("p3"),mc);
        assertTrue(SharedSpaceHandler.numOfSharedPlayers(m,m.getPlayer("p1").getOrigin())>0);
    }
}
