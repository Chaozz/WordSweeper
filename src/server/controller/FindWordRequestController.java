package server.controller;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import server.ClientState;
import server.IProtocolHandler;
import server.Server;
import server.model.*;
import util.WordTable;
import xml.Message;

import java.util.Hashtable;

/**
 * Created by tianhao on 11/14/16.
 */
public class FindWordRequestController implements IProtocolHandler {
    ServerModel model;

    public FindWordRequestController(ServerModel model) {
        this.model = model;
    }

    @Override
    public Message process(ClientState client, Message request) {
        Node findRequest = request.contents.getFirstChild();
        NamedNodeMap map = findRequest.getAttributes();
        String gameId = map.getNamedItem("gameId").getNodeValue();
        String pname = map.getNamedItem("name").getNodeName();
        String word = map.getNamedItem("word").getNodeName();
        Game game = model.getGame(gameId);
        if (WordTable.isWord(word)) {
            Board board = game.getBoard();
            Hashtable<Position, Cell> currentCells = board.getCells();

            NodeList cellSequence = findRequest.getChildNodes();
            Hashtable<Position, Cell> foundCells = new Hashtable();
            for (int i = 0; i < cellSequence.getLength(); i++) {
                Node node = cellSequence.item(i);
                NamedNodeMap cellMap = node.getAttributes();
                String[] commaSeparatedPair = cellMap.getNamedItem("position").getNodeValue().split(",");
                Position position = new Position(Integer.valueOf(commaSeparatedPair[0]), Integer.valueOf
                        (commaSeparatedPair[1]));
                foundCells.put(position, currentCells.get(position));
            }

            //todo calculate score, change Game object
            int score = 0;


            String xmlString = Message.responseHeader(request.id()) + "<findWordResponse> gameId = " +
                    "'" + gameId + "' name=" + pname + "' score = " + score + " /> </response>";
            String boardResponseXml = BoardResponseHandler.getBoardResponse(game, gameId, request.id());
            Message boardResponseMsg = new Message(boardResponseXml);
            for (String id : game.getClients()) {
                Server.getState(id).sendMessage(boardResponseMsg);
            }
            return new Message(xmlString);
        } else {
            String xmlString = Message.responseHeader(request.id(), "Wrong Word!") + "<findWordResponse> gameId = " +
                    "'" + gameId + "' name=" + pname + "' score = 0 /> </response>";
            String boardResponseXml = BoardResponseHandler.getBoardResponse(game, gameId, request.id());
            Message boardResponseMsg = new Message(boardResponseXml);
            for (String id : game.getClients()) {
                Server.getState(id).sendMessage(boardResponseMsg);
            }
            return new Message(xmlString);

        }
    }
}
