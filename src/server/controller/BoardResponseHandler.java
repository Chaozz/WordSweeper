package server.controller;

import server.model.Board;
import server.model.Game;
import server.model.Player;
import server.model.Position;
import xml.Message;

import java.util.List;

/**
 * Created by tianhao on 12/9/16.
 */
public class BoardResponseHandler {
    public static String getBoardResponse(Game game, String gameId, String requestId){
        Board board = game.getBoard();
        Position multiplier = board.getMultiplier();
        List<Player> players = game.getPlayers();
        String otherPlayers = "";
        // Merge addClient step to joinGame
//        game.addClient(client);
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            otherPlayers += "<player name='player" + i + "' score='" + p.getScore() + "' position='" + p.getOrigin()
                    .getRow() + "," + p.getOrigin().getCol() + "' board='" + board.getLocalBoardContent(p.getOrigin()
            ) + "'/>";
        }

        // Construct message reflecting state
        String xmlString = Message.responseHeader(requestId) +
                "<boardResponse gameId='" + gameId + "' managingUser='" + game.getManagingPlayerName() + "' bonus='"
                + multiplier.getRow() + "," + multiplier.getCol() + "' contents='" + board.getBoardContent() + "'>" +
                otherPlayers +
                "</boardResponse>" +
                "</response>";
        return xmlString;
    }
}
