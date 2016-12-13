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
 * Created by tianhao on 11/14/16.
 */
public class ResetGameRequestController implements IProtocolHandler {
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
        if(!game.getManagingPlayerID().equals(state.id())){
            return null;
        }
        game.resetGame();
        Board board = game.getBoard();
        Position multiplier = board.getMultiplier();
        List<Player> players = game.getPlayers();
        String otherPlayers = "";
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            otherPlayers += "<player name='"+p.getName() + "' score='" + p.getScore() + "' position='" + p.getOrigin()
                    .getCol() + "," + p.getOrigin().getRow() + "' board='" + board.getLocalBoardContent(p.getOrigin()
            ) + "'/>";
        }
        String xmlStringReset = Message.responseHeader(request.id()) +
                "<resetGameResponse gameId='" + gameId + "'/>" +
                "</response>";
        Message resetResponse = new Message(xmlStringReset);
        String xmlStringBoardResponse = Message.responseHeader(request.id()) +
                "<boardResponse gameId='" + gameId + "' managingUser='" + game.getManagingPlayerName() + "' bonus='"
                + multiplier.getCol() + "," + multiplier.getRow() + "' contents='" + board.getBoardContent() + "'>" +
                otherPlayers +
                "</boardResponse>" +
                "</response>";
        Message boardResponse = new Message(xmlStringBoardResponse);
        for (String id : game.getClients()) {
            Server.getState(id).sendMessage(boardResponse);
        }
        return resetResponse;
    }
}
