package server.controller;

import junit.framework.TestCase;
import org.junit.Assert;
import server.MockClient;
import server.Server;
import server.model.ServerModel;
import xml.Message;


public class TestProtocolHandler extends TestCase {
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
    }

    public void testHandler() {
        ProtocolHandler ph=new ProtocolHandler(model);
        String xmlString = Message.requestHeader() + "<connectRequest/></request>";
        Message request = new Message(xmlString);
        assertNull(ph.process(client1, request));
    }
}

