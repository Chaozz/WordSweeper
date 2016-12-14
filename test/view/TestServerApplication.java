package view;

import junit.framework.TestCase;
import org.w3c.dom.NamedNodeMap;
import server.MockClient;
import server.Server;
import server.controller.ProtocolHandler;
import server.model.ServerModel;
import server.view.ServerApplication;
import xml.Message;

public class TestServerApplication extends TestCase {
    ServerModel model;
    MockClient client1;
    protected void setUp() {
        // FIRST thing to do is register the protocol being used.
        if (!Message.configure("wordsweeper.xsd")) {
            fail("unable to configure protocol");
        }

        client1 = new MockClient("c1");
        Server.register("c1", client1);
        model = new ServerModel();
    }

    protected void tearDown() {
        Server.unregister("c1");
        Server.unregister("c2");
    }
    public void testApp() {
        ServerApplication app=new ServerApplication(model);
        String xmlString = Message.requestHeader() + "<createGameRequest name='test'/></request>";
        Message request = new Message(xmlString);

        // get response after processing this request
        Message response = new ProtocolHandler(model).process(client1, request);

        // make sure model is well-represented
        assertTrue(response.success());
        NamedNodeMap map = response.contents.getFirstChild().getAttributes();
        String gameID=map.getNamedItem("gameId").getNodeValue();
        app.selectedGame=model.getGame(gameID);
        app.players=app.selectedGame.getPlayers();
        System.out.println(app.selectedGame);
        app.updateBoardTable();
        app.updateGameBriefTable();
        app.updateGameStateTable();
        app.setVisible(true);
    }
}

