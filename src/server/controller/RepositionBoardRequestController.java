package server.controller;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import server.ClientState;
import server.IProtocolHandler;
import server.Server;
import server.model.Game;
import server.model.Player;
import server.model.Position;
import server.model.ServerModel;
import xml.Message;

/**
 * Controller on server to handle the reposition board request and return board response to client.
 */
public class RepositionBoardRequestController implements IProtocolHandler {
    ServerModel model;

    public RepositionBoardRequestController(ServerModel model) {
        this.model = model;
    }

    @Override
    public Message process(ClientState state, Message request) {
        Node repositionRequest = request.contents.getFirstChild();
        NamedNodeMap map = repositionRequest.getAttributes();
        String pname = map.getNamedItem("name").getNodeValue();
        String gameId = map.getNamedItem("gameId").getNodeValue();
        int rowChange = Integer.parseInt(map.getNamedItem("rowChange").getNodeValue());
        int colChange = Integer.parseInt(map.getNamedItem("colChange").getNodeValue());

        Game game = model.getGame(gameId);
        Player player = game.getPlayer(pname);
        int size = game.getBoard().getSize();
        int newRow = player.getOrigin().getRow() + rowChange;
        int newCol = player.getOrigin().getCol() + colChange;
        if (newRow >= 0 && newRow <= size - 4 && newCol >= 0 && newCol <= size - 4) {
            player.setOrigin(new Position(newCol, newRow));
            String boardResponseXml = BoardResponseHandler.getBoardResponse(game, gameId, request.id());
            Message boardResponseMsg = new Message(boardResponseXml);
            for (String id : game.getClients()) {
                if (!id.equals(state.id()))
                    Server.getState(id).sendMessage(boardResponseMsg);
            }
            System.out.println(boardResponseXml);
            return boardResponseMsg;
        }
        return null;
    }
}
