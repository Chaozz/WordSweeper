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

        Node joinRequest = request.contents.getFirstChild();
        NamedNodeMap map = joinRequest.getAttributes();

        String pname = map.getNamedItem("name").getNodeValue();
        String gameId = map.getNamedItem("gameId").getNodeValue();

        // If fail to join the Game
        if (!model.joinGame(pname, client, gameId)) {
            String xmlString = Message.responseHeader(request.id(), "Game is locked!") +
                    "<joinGameResponse gameId='" + gameId + "'/>" +
                    "</response>";
            return new Message(xmlString);
        }
        // otherwise
        Game game = model.getGame(gameId);
        game.resetGame();
        Board board = game.getBoard();
        Position multiplier = board.getMultiplier();
        List<Player> players = game.getPlayers();
        String otherPlayers = "";
        // Merge addClient step to joinGame
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            otherPlayers += "<player name='" + p.getName() + "' score='" + p.getScore() + "' position='" + p.getOrigin()
                    .getCol() + "," + p.getOrigin().getRow() + "' board='" + board.getLocalBoardContent(p.getOrigin()
            ) + "'/>";
        }

        // Construct message reflecting state
        String xmlString = Message.responseHeader(request.id()) +
                "<boardResponse gameId='" + gameId + "' managingUser='" + game.getManagingPlayerName() + "' bonus='"
                + multiplier.getCol() + "," + multiplier.getRow() + "' contents='" + board.getBoardContent() + "'>" +
                otherPlayers +
                "</boardResponse>" +
                "</response>";

        Message message = new Message(xmlString);

        // all other players on game (excepting this particular client) need to be told of this
        // same response. Note this is inefficient and should be replaced by more elegant functioning
        // hint: rely on your game to store player names...
        for (String id : game.getClients()) {
            if (!id.equals(client.id())) {
                Server.getState(id).sendMessage(message);
            }
        }

        // send this response back to the client which sent us the request.
        return message;
    }
}
