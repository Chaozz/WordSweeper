package server.controller;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import server.ClientState;
import server.IProtocolHandler;
import server.model.*;
import xml.Message;

/**
 * Controller on server to package up the current state of the model
 * as an updateResponse message and send it back to the client.
 */
public class CreateGameRequestController implements IProtocolHandler {

    ServerModel model;

    public CreateGameRequestController(ServerModel model) {
        this.model = model;
    }

    public String getGlobalBoardContent(Game game){
        Board board = game.getBoard();
        StringBuffer content=new StringBuffer();
        content = new StringBuffer();
        for(int i=0;i<board.getSize();i++){
            for(int j=0;j<board.getSize();j++){
                Position p = new Position(i,j);
                content.append(board.getCells().get(p).getLetter().getCharacter());
                content.append(",");
            }
        }
        content.deleteCharAt(board.getSize()-1);
        return content.toString();
    }

    public Message process(ClientState client, Message request) {

//        model.joinGame();  // HACK.

        // note you can retrieve information from the request...
        Node createRequest = request.contents.getFirstChild();
        NamedNodeMap map = createRequest.getAttributes();

        String pname = map.getNamedItem("name").getNodeValue();
        String gameID = model.createGame(pname);
        Game game = model.getGame(gameID);
        Player me = game.getPlayer(pname);
        Position origin = me.getOrigin();
        Board board = game.getBoard();
        Position multiplier = board.getMultiplier();


        // Construct message reflecting state
        String xmlString = Message.responseHeader(request.id()) +
                "<boardResponse gameId='" + gameID + "' managingUser='" + pname + "' bonus='" + multiplier.getRow() +
                "," + multiplier.getCol() + "' " +
                "contents='" + board.getBoardContent() + "'>" +
                "<player name='" + pname + "' score='" + me.getScore() + "' position='" + origin.getRow() + "," +
                origin.getCol() + "' " +
                "board='" + board.getLocalBoardContent(origin) + "'/>" +
                "</boardResponse>" +
                "</response>";

        // send this response back to the client which sent us the request.
        return new Message(xmlString);
    }
}
