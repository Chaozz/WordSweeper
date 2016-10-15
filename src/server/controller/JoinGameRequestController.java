package server.controller;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import server.ClientState;
import server.IProtocolHandler;
import server.Server;
import server.model.*;
import xml.Message;

import java.util.List;

/**
 * Controller on server to package up the current state of the model
 * as an updateResponse message and send it back to the client.
 */
public class JoinGameRequestController implements IProtocolHandler {

    ServerModel model;

    public JoinGameRequestController(ServerModel model) {
        this.model = model;
    }

    public Message process(ClientState client, Message request) {

        //model.joinGame();

        Node createRequest = request.contents.getFirstChild();
        NamedNodeMap map = createRequest.getAttributes();

        String pname = map.getNamedItem("name").getNodeValue();
        String gameId = map.getNamedItem("gameId").getNodeValue();
        System.out.println(model.joinGame(pname, gameId));
        Game game = model.getGame(gameId);
        Board board = game.getBoard();
        Position multiplier = board.getMultiplier();
        List<Player> players = game.getPlayers();
        String otherPlayers = "";
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            otherPlayers += "<player name='player" + i + "' score='" + p.getScore() + "' position='" + p.getOrigin().getRow() + "," + p.getOrigin().getCol() + "' board='" + board.getLocalBoardContent(p.getOrigin()) + "'/>";
        }

        // Construct message reflecting state
        String xmlString = Message.responseHeader(request.id()) +
                "<boardResponse gameId='" + gameId + "' managingUser='" + game.getManagingPlayerName() + "' bonus='" + multiplier.getRow() + "," + multiplier.getCol() + "' contents='" + board.getBoardContent() + "'>" +
                otherPlayers +
                "</boardResponse>" +
                "</response>";

        Message message = new Message(xmlString);

        // all other players on game (excepting this particular client) need to be told of this
        // same response. Note this is inefficient and should be replaced by more elegant functioning
        // hint: rely on your game to store player names...
        for (String id : Server.ids()) {
            if (!id.equals(client.id())) {
                Server.getState(id).sendMessage(message);
            }
        }

        // send this response back to the client which sent us the request.
        return message;
    }
}
