package server.controller;

import server.model.Board;
import server.model.Game;
import server.model.Player;
import server.model.Position;
import xml.Message;

import java.util.List;

/**
 * Given Game object, GameID, RequestID, return boardResponse String
 */
public class BoardResponseHandler {
    public static String getBoardResponse(Game game, String gameId, String requestId) {
        Board board = game.getBoard();
        Position multiplier = board.getMultiplier();
        List<Player> players = game.getPlayers();
        String otherPlayers = "";
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            otherPlayers += "<player name='" + p.getName() + "' score='" + p.getScore() + "' position='" + p.getOrigin()
                    .getCol() + "," + p.getOrigin().getRow() + "' board='" + board.getLocalBoardContent(p.getOrigin()
            ) + "'/>";
        }

        String xmlString = Message.responseHeader(requestId) +
                "<boardResponse gameId='" + gameId + "' managingUser='" + game.getManagingPlayerName() + "' bonus='"
                + multiplier.getCol() + "," + multiplier.getRow() + "' contents='" + board.getBoardContent() + "'>" +
                otherPlayers +
                "</boardResponse>" +
                "</response>";
        return xmlString;
    }
}
