package server.controller;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import server.ClientState;
import server.IProtocolHandler;
import server.Server;
import server.model.*;
import xml.Message;

/**
 * Controller on server to package up the current state of the model
 * as an updateResponse message and send it back to the client.
 */
public class LockGameRequestController {
    ServerModel model;

    public LockGameRequestController(ServerModel model){
        this.model=model;
    }

    public Message process(ClientState client, Message request) {
        Node createRequest = request.contents.getFirstChild();
        NamedNodeMap map = createRequest.getAttributes();
        String gameId = map.getNamedItem("gameId").getNodeValue();
        if(!model.getGame(gameId).getManagingPlayerID().equals(client.id())){
            return null;
        }
        model.getGame(gameId).setLocked(true);

        String xmlString = Message.responseHeader(request.id()) +
                "<lockGameResponse gameId='" + gameId + "'/>" +
                "</response>";

        return new Message(xmlString);
    }
}
