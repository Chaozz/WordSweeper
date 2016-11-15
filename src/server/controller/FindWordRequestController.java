package server.controller;

import server.ClientState;
import server.IProtocolHandler;
import server.model.ServerModel;
import xml.Message;

/**
 * Created by tianhao on 11/14/16.
 */
public class FindWordRequestController implements IProtocolHandler {
    ServerModel model;

    public FindWordRequestController(ServerModel model) {
        this.model = model;
    }

    @Override
    public Message process(ClientState state, Message request) {

        return null;
    }
}
