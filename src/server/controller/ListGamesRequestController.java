package server.controller;

import server.ClientState;
import server.IProtocolHandler;
import server.model.Game;
import server.model.ServerModel;
import xml.Message;

import java.util.ArrayList;

/**
 * Created by tianhao on 12/9/16.
 */
public class ListGamesRequestController implements IProtocolHandler {
    ServerModel model;

    public ListGamesRequestController(ServerModel model) {
        this.model = model;
    }

    @Override
    public Message process(ClientState state, Message request) {
        ArrayList<Game> games = model.getGames();
        String gameBriefList = "";
        for (Game g : games) {
            gameBriefList += "<gameBrief gameId = '" + g.getGameID() + "' players='" + g.getPlayers().size() + "'" +
                    " />";
        }
        String xml = Message.responseHeader(request.id()) + "<listGamesResponse>" + gameBriefList +
                "</listGamesResponse> " +
                "</response>";

        return new Message(xml);
    }
}
