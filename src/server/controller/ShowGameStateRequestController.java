package server.controller;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import server.ClientState;
import server.IProtocolHandler;
import server.model.Game;
import server.model.ServerModel;
import xml.Message;

/**
 * Controller on server to handle the show game state request of admin client and return board response to client.
 */
public class ShowGameStateRequestController implements IProtocolHandler{
    ServerModel model;

    public ShowGameStateRequestController(ServerModel model) {
        this.model = model;
    }

    @Override
    public Message process(ClientState client, Message request) {
        Node showRequest = request.contents.getFirstChild();
        NamedNodeMap map = showRequest.getAttributes();
        String gameId = map.getNamedItem("gameId").getNodeValue();
        Game game = model.getGame(gameId);
        String boardResponseXml = BoardResponseHandler.getBoardResponse(game, gameId, request.id());
        Message boardResponseMsg = new Message(boardResponseXml);
        return boardResponseMsg;
    }
}
