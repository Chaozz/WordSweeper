package server.controller;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import server.ClientState;
import server.IProtocolHandler;
import server.model.Game;
import server.model.ServerModel;
import xml.Message;

/**
 * Created by tianhao on 11/14/16.
 */
public class ResetGameRequestController implements IProtocolHandler{
    ServerModel model;

    public ResetGameRequestController(ServerModel model) {
        this.model = model;
    }

    @Override
    public Message process(ClientState state, Message request) {
        Node resetGameRequest = request.contents.getFirstChild();
        NamedNodeMap map = resetGameRequest.getAttributes();

        String gameId = map.getNamedItem("gameId").getNodeValue();
        Game game = model.getGame(gameId);
        game.resetGame();
        String xmlStringReset = Message.responseHeader(request.id()) +
                "<resetGameResponse gameId='" + gameId + "'</resetGameResponse>" +
                "</response>";
        Message resetResponse = new Message(xmlStringReset);
//        String xmlStringBoardResponse = Message.responseHeader(); Need to implement boardResponse, how Server.ids work
        return resetResponse;
    }
}
