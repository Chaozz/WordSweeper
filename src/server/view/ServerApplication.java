package server.view;

import server.model.Game;
import server.model.ServerModel;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Created by lyu on 2016/12/10.
 */
public class ServerApplication extends JFrame {
    private ServerModel model;
    private ArrayList<Game> games;
    private JPanel contentPane;
    private JTable briefTable;
    private JTable gameStateTable;
    private JTable boardTable;
    private JScrollPane scrollPane_gameState;
    private JScrollPane scrollPane_boardState;
    private JScrollPane scrollPane_gameBrief;
    private JButton updateListBtn;
    private JButton updateGameBtn;
    private String[] briefHeader = {"Game ID", "PlayerNum"};
    private String[] gameStateHeader = {"Player", "Position", "Score"};
    private DefaultTableModel briefModel;
    private DefaultTableModel gameStateModel;


    /**
     * Launch the application.
     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    ServerApplication frame = new ServerApplication();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

    /**
     * Create the frame.
     */
    public ServerApplication(ServerModel model) {
        this.model = model;
        setTitle("WordSweeper Server Client");
        setFont(new Font("Source Code Pro", Font.PLAIN, 12));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 400);
        setResizable(false);

        contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);
        updateListBtn = new JButton("UpdateGameList");
        updateListBtn.setBounds(5, 5, 150, 20);
        updateGameBtn = new JButton("UpdateBoard");
        updateGameBtn.setBounds(200, 5, 100, 20);
        briefModel = new DefaultTableModel(briefHeader,0);
        gameStateModel = new DefaultTableModel(gameStateHeader,0);
        briefTable = new JTable(briefModel);
        boardTable = new JTable();
        gameStateTable = new JTable(gameStateModel);
        scrollPane_gameBrief = new JScrollPane(briefTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane_gameBrief.setBounds(5,50,180,280);
        scrollPane_boardState = new JScrollPane(boardTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane
                .HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane_boardState.setBounds(200,50,300,280);
        scrollPane_gameState = new JScrollPane(gameStateTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane_gameState.setBounds(510,50,300,280);
        contentPane.add(updateListBtn);
        contentPane.add(updateGameBtn);
        contentPane.add(scrollPane_boardState);
        contentPane.add(scrollPane_gameBrief);
        contentPane.add(scrollPane_gameState);

        updateListBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGameBriefTable();
            }
        });

        updateGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBoardTable();
                updateGameStateTable();
            }
        });


        games = model.getGames();
        if (games == null||games.size()==0) {
        }else {
            updateGameBriefTable();
            updateBoardTable();
            updateGameStateTable();
        }

    }
    private void updateGameBriefTable() {
        games = model.getGames();
        int num = games.size();
        Object[][] briefData = new Object[num][2];
        for (int i = 0; i < num; i++) {
            Game g = games.get(i);
            briefData[i][0] = g.getGameID();
            briefData[i][1] = g.getPlayers().size();
        }
        briefModel.setDataVector(briefData,briefHeader);
        briefTable.setModel(briefModel);

    }

    private void updateBoardTable() {

    }


    private void updateGameStateTable() {

    }


}
