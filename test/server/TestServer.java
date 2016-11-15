package server;

import junit.framework.TestCase;
import server.controller.ProtocolHandler;
import server.model.ServerModel;
import xml.Message;

import java.io.IOException;

public class TestServer extends TestCase {

    public void testServer(){
        assertTrue(Message.configure("wordsweeper.xsd"));

        // Server-side model contains everything you need on the server.
        ServerModel serverModel = new ServerModel();

        // Start server and have ProtocolHandler be responsible for all XML messages.
        Server server = new Server(new ProtocolHandler(serverModel), 11425);
        try {
            server.bind();
        } catch (IOException ioe) {
            System.err.println("Unable to launch server:" + ioe.getMessage());
            System.exit(-1);
        }
    }
}
