package client;

import server.ServerThread;
import xml.Message;
import xml.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;

/** 
 * Responsible for all communication to/from server.
 * <p>
 * To communicate with a remote server, construct a ServerAccess object (using 
 * the default port or one of your own specification) and invoke the 
 * {@link #connect(IMessageHandler)} method. Once connected, you can send a message
 * request to the server through the {@link #sendRequest(Message)} message. All
 * responses to these requests from the server are processed by a 
 * {@link IMessageHandler} object.
 * <p>
 * Should you want more control, then when sending a request, you can also register
 * a {@link IController} object to specifically be given the response from the server
 * to a given request.
 * <p>
 * Once done, call {@link #disconnect()} and the server communication will be 
 * terminated and the background {@link ServerThread} thread will cleanly shut down.
 */
public class ServerAccess {
	String host = null;            /* Server to whom we are connecting */
	Socket server;                 /* Connection to server */
	BufferedReader fromBuffer;     /* BufferedReader for input from server */
	PrintWriter toServer;          /* Write to server through this PrintWriter */
	final int serverPort;          /* Actual port to use. */

	boolean isActive = false;      /* Are we actively connected to server? */
	static final int defaultPort = 9371;  /* Default server port. */

	
	/* Waiting controllers. */
	Hashtable<String,Tuple> pending = new Hashtable<String,Tuple>();   

	class Tuple {
		IController controller;
		Message request;
		String       id;

		Tuple (IController c, Message r, String i) {
			controller = c;
			request = r;
			id = i;
		}
	}

	/** Construct ServerAccess object to use default port number. */
	public ServerAccess(String host) {
		this(host, defaultPort);
	}

	/**
	 * Construct ServerAccess object using specified port number. 
	 */
	public ServerAccess(String host, int port) {
		this.host = host;
		serverPort = port;
	}
	
	/** 
	 * Start up reader and talker threads. Returns false if unable to contact server.
	 * When server messages are received, they are handled by the given handler object. 
	 */
	public boolean connect(final IMessageHandler handler) {
		try {
			server = new Socket (host, serverPort);
			toServer = new PrintWriter (server.getOutputStream(), true);
		} catch (Exception e) {
			System.err.println("Unable to connect to server: " + e.getMessage());
			return false;
		}

		// start connection by starting reader thread and send proper LOGIN request.
		isActive = true;
		new FromServer(handler).start();
		return true;
	}

	/** Request to disconnect from server (internal or external). */
	public void disconnect() {
		if (!isActive) { return; } 
		isActive = false;

		pending.clear();  // eliminate any waiting controllers

		try {
			// Terminate connection to server and (by implication) stop FromServer thread
			server.close();
			toServer = null;
			server = null;
		} catch (IOException e) {
			System.err.println("Unable to closer server:" + e.getMessage());
		}
	}

	/** Send request to the server. Return success or failure */
	public synchronized boolean sendRequest(Message r) {
		if (!isActive) { return false; }

		toServer.println(r);
		return !toServer.checkError();
	}

	/**
	 * Send request to the server and register controller to process response
	 * only on success. Return success or failure of operation. 
	 * <p>
	 * The given IController object is going to be responsible for processing the
	 * response that comes back.
	 */
	public synchronized boolean sendRequest(IController c, Message m) {
		if (!isActive) { return false; }

		toServer.println(m);
		boolean success = !toServer.checkError();
		if (success) {
			pending.put(m.id(), new Tuple(c, m, m.id()));
		}
		return success;
	}

	/** Useful method for extracting key information in error messages. */
	public String toString () {
		return "Connected to:" + host; 
	}

	/**
	 * Thread to receive Responses back from Server. This inner class has access to attributes defined
	 * in the enclosing ServerAccess class.
	 */
	class FromServer extends Thread {
		/** Every message is handled by this message handler. */
		IMessageHandler handler;

		FromServer(IMessageHandler handler) {
			this.handler = handler;
		}

		public void run() {
			try {
				fromBuffer = new BufferedReader (new InputStreamReader(server.getInputStream()));

				while (isActive) {
					Message m = Parser.extractResponse(fromBuffer);
					if (m == null) {
						disconnect();
						break;
					}

					try {
						Tuple p = pending.remove(m.id()); 
						if (p != null) {
							p.controller.process(p.request, m);
						} else {
							handler.process (m);
						}
					} catch (Exception e) {
						System.err.println("Error processing:" + m.toString());
						e.printStackTrace();
					}
				}

			} catch (IOException ioe) {
				ioe.printStackTrace();
				isActive = false;
			}  

			fromBuffer = null;
		}
	}

	/** Determines whether there are any controllers waiting on a response. */
	public synchronized boolean isWaiting() {
		return (!pending.isEmpty());
	}

	/** Utility method to return host to which ServerAccess connected. */
	public String getHost() {
		return host;
	}

}
