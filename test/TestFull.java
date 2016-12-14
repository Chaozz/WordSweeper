import client.IMessageHandler;
import client.ServerAccess;
import junit.framework.TestCase;
import server.ClientState;
import server.IShutdownHandler;
import server.Server;
import xml.Message;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Test case tries to do a full test of the vanilla client/server.
 *  
 * @author heineman
 */
public class TestFull extends TestCase {

	/** Access to the server for a client. */
	ServerAccess sa;

	/** Server object (the real thing). */
	Server server;

	/** Is server up and running yet. */
	boolean activeStatus = false;

	/** Server request handler. */
	ServerRequestHandler serverRequestHandler;
	
	/** Client response handler. */
	ClientResponseHandler clientResponseHandler;
	
	/** Helper method for setUp routine. */
	private boolean isActive() {
		return activeStatus;
	}

	/** Utility function to wait a second. */
	private void waitASecond() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			
		}
	}
	
	// Inner class useful for describing how server processes requests.
	class ServerRequestHandler implements IShutdownHandler {

		// requests are stored here.
		ArrayList<Message> requests = new ArrayList<Message>();

		// record ID of the client thread on the server.
		String id;
		
		boolean connected = true;

		@Override
		public Message process(ClientState state, Message request) {
			this.id = state.id();
			requests.add(request);

			// can't really do any processing, so just return connectResponse in all cases.
			String xmlString = Message.responseHeader(request.id()) + "<connectResponse id='" + serverRequestHandler.id + "'/></response>";
			Message response = new Message(xmlString);
			
			return response;
		}

		@Override
		public void logout(ClientState state) {
			connected = false;
		}		
	}

	// Inner class useful for describing how client processes responses.
	class ClientResponseHandler implements IMessageHandler {

		// responses are stored here.
		ArrayList<Message> responses = new ArrayList<Message>();

		@Override
		public void process(Message response) {
			responses.add(response);
		}

	}

	@Override
	protected void setUp() {
		// FIRST thing to do is register the protocol being used. There will be a single class protocol
		// that will be defined and which everyone will use. For now, demonstrate with skeleton protocol.
		if (!Message.configure("skeleton.xsd")) {
			fail ("unable to configure skeleton");
		}

		// server gets started in its own thread.
		// Start server and have ProtocolHandler be responsible for all XML messages.
		serverRequestHandler = new ServerRequestHandler();
		server = new Server(serverRequestHandler, 9371);

		try {
			server.bind();
		} catch (IOException ioe) {
			fail ("unable to launch server");
		}

		// Get server processing in its own thread (must be separate)
		new Thread() {
			@Override
			public void run() {
				try {
					activeStatus = true;
					server.process();
				} catch (IOException e) {
					// socket connection happens on server when stopping 
					// suddenly so we do nothing here...
				}
			}
		}.start();


		// this is to be a client connection. Wait until active
		while (!isActive()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				fail ("Can't start up server for some reason.");
			}
		}

		// ready to go!
		sa = new ServerAccess("localhost");
		clientResponseHandler = new ClientResponseHandler();
		sa.connect(clientResponseHandler);
	}

	protected void tearDown() {

		// disconnect client FIRST
		sa.disconnect();

		try {
			server.shutdown();
		} catch (IOException e) {
			System.out.println("Error shutting down server.");
		}
	}

	public void testEverything() {
		// send connect message to server through client's ServerAccess
		String xmlString = Message.requestHeader() + "<connectRequest/></request>";
		Message req = new Message(xmlString);
		assertTrue(sa.sendRequest(req));

		// obvious tests
		assertEquals ("localhost", sa.getHost());
		assertTrue (sa.toString() != null); // all that we care about is it isn't null.
		
		// wait for server to get request and process it, returning response to our 
		// clientResponseHandler
		
		waitASecond();
		
		// retrieve from client ResponseHandler
		Message m = clientResponseHandler.responses.remove(0);
		
		assertEquals ("connectResponse", m.contents.getFirstChild().getNodeName());
		
		// just to validate that we can send additional requests, do this again.
		assertTrue(sa.sendRequest(req));
		waitASecond(); 
		
		// note that this request is to be picked up by the ServerRequestHandler
		// and is not actually processed...
		m = serverRequestHandler.requests.remove(0);
		assertEquals ("connectRequest", m.contents.getFirstChild().getNodeName());
		
		// ... but we can do the manual processing on our own
		ClientState state = Server.getState(serverRequestHandler.id);
		xmlString = Message.responseHeader(req.id()) + "<connectResponse id='" + serverRequestHandler.id + "'/></response>";
		Message response = new Message(xmlString);
		state.sendMessage(response);
		
		Message clientResp = clientResponseHandler.responses.remove(0);
		assertEquals ("connectResponse", clientResp.contents.getFirstChild().getNodeName());
	}

	
}
