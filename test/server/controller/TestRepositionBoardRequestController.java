package server.controller;

import org.w3c.dom.NamedNodeMap;

import server.MockClient;
import server.Server;
import server.model.ServerModel;
import xml.Message;
import junit.framework.TestCase;

// validate that server generates appropriate modelResponse for each modelRequest.
public class TestRepositionBoardRequestController extends TestCase {

    /**
     * Become a placeholder for responses sent to the (only connected) client.
     */
    MockClient client1;

    ServerModel model;

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

    public void testSimple() {
        // client1 creates game
        String xmlString = Message.requestHeader() + "<createGameRequest name='test'/></request>";
        Message request = new Message(xmlString);

        // get response after processing this request
        Message response = new CreateGameRequestController(model).process(client1, request);

        // make sure model is well-represented
        assertTrue(response.success());

        NamedNodeMap map = response.contents.getFirstChild().getAttributes();
        String gameID=map.getNamedItem("gameId").getNodeValue();

        xmlString = Message.requestHeader() + "<repositionBoardRequest gameId='"+gameID+"' name='test' " +
                "rowChange='-1' colChange='0'/></request>";
        request = new Message(xmlString);

        Message Response = new ProtocolHandler(model).process(client1, request);

        xmlString = Message.requestHeader() + "<repositionBoardRequest gameId='"+gameID+"' name='test' " +
                "rowChange='1' colChange='0'/></request>";
        request = new Message(xmlString);

        Response = new RepositionBoardRequestController(model).process(client1, request);
    }
}
