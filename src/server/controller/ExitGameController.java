package server.controller;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import server.ClientState;
import server.IProtocolHandler;
import server.Server;
import server.model.Game;
import server.model.Player;
import server.model.ServerModel;
import xml.Message;

/**
 * Controller on server to handle the exit game request and return exit response to client.
 */
public class ExitGameController implements IProtocolHandler {
    ServerModel model;

    public ExitGameController(ServerModel model) {
        this.model = model;
    }

    @Override
    public Message process(ClientState client, Message request) {
        Node exitRequest = request.contents.getFirstChild();
        NamedNodeMap map = exitRequest.getAttributes();
        String pname = map.getNamedItem("name").getNodeValue();
        String gameId = map.getNamedItem("gameId").getNodeValue();
        Game game = model.getGame(gameId);
        Player player = game.getPlayer(pname);
        game.removePlayer(pname);
        if (game.getPlayers().isEmpty()) {
            model.removeGame(gameId);
        }
        String xml = Message.responseHeader(request.id()) + "<exitGameResponse gameId='" + gameId + "' /> </response>";
        String boardResponseXml = BoardResponseHandler.getBoardResponse(game, gameId, request.id());
        Message boardResponseMsg = new Message(boardResponseXml);
        for (String id : game.getClients()) {
            if (!id.equals(client.id()))
                Server.getState(id).sendMessage(boardResponseMsg);
        }

        return new Message(xml);
    }
}
