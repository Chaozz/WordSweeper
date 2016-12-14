package server;
import java.io.*;

import server.controller.ProtocolHandler;
import server.model.ServerModel;
import server.view.ServerApplication;
import xml.Message;

/** Code to launch Server  from the command line. */
public class ServerLauncher {

	/** Execute the Server using the default port. */
	public static void main(String[] args) {
		// FIRST thing to do is register the protocol being used. There will be a single class protocol
		// that will be defined and which everyone will use. For now, demonstrate with skeleton protocol.
		if (!Message.configure("wordsweeper.xsd")) {
			System.exit(0);
		}

		// Server-side model contains everything you need on the server.
		ServerModel serverModel = new ServerModel();

		// Start server and have ProtocolHandler be responsible for all XML messages.
		Server server = new Server(new ProtocolHandler(serverModel), 11425);

		try {
			server.bind();
            ServerApplication application = new ServerApplication(serverModel);
            application.setVisible(true);
		} catch (IOException ioe) {
			System.err.println("Unable to launch server:" + ioe.getMessage());
			System.exit(-1);
		}

		// process all requests and exit.
		System.out.println("Server awaiting client connections");
		try {
			server.process();
			System.out.println("Server shutting down.");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
