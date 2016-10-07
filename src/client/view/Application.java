package client.view;

import client.ServerAccess;
import client.controller.CreateGameController;
import client.controller.JoinGameController;
import client.model.Model;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Application extends JFrame {

	/** GUI application maintains reference to Model for ease of navigation. */
	public final Model model;
	
	JPanel contentPane;
	ServerAccess serverAccess;

	JTextArea requestArea;
	JTextArea responseArea;
	
	JButton btnJoinGame;
	
	JScrollPane clientRequests;
	JScrollPane serverOutput;
	
	/**
	 * Create the frame.
	 */
	public Application(Model model) {
		this.model = model;
		setTitle("Skeleton Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 473, 374);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnCreateGame = new JButton("Create Game");
		btnCreateGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CreateGameController(Application.this, model).process();
			}
		});
		
		btnJoinGame = new JButton("Join Game");
		btnJoinGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JoinGameController(Application.this, model).process();
			}
		});
		clientRequests = new JScrollPane();
		
		JLabel lblOutputFromServer = new JLabel("Output From Server");
		
		serverOutput = new JScrollPane();
		
		requestArea = new JTextArea(100, 20);
		clientRequests.setViewportView(requestArea);
		
		responseArea = new JTextArea();
		serverOutput.setViewportView(responseArea);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(clientRequests, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnCreateGame)
							.addPreferredGap(ComponentPlacement.RELATED, 235, Short.MAX_VALUE)
							.addComponent(btnJoinGame))
						.addComponent(lblOutputFromServer)
						.addComponent(serverOutput, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCreateGame)
						.addComponent(btnJoinGame))
					.addGap(18)
					.addComponent(clientRequests, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblOutputFromServer)
					.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
					.addComponent(serverOutput, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	/** Record the means to communicate with server. */
	public void setServerAccess(ServerAccess access) {
		this.serverAccess = access;
	}
	
	/** Get the server access object. */
	public ServerAccess getServerAccess() {
		return serverAccess;
	}
	
	/** Navigation access to actionable elements in the GUI. */
	public JTextArea getRequestArea() {
		return requestArea;
	}
	
	/** Navigation access to actionable elements in the GUI. */
	public JTextArea getResponseArea() {
		return responseArea;
	}
}
