package server.view;

import server.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;


/**
 * Created by lyu on 2016/12/10.
 */
public class ServerApplication extends JFrame {
    private ServerModel model;
    private ArrayList<Game> games;
    private Board board;
    private Game selectedGame;
    private ArrayList<Player> players;
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
    private DefaultTableModel boardModel;
    private DefaultTableModel gameStateModel;


    public ServerApplication(ServerModel model) {
        this.model = model;
        setTitle("WordSweeper Server Client");
        setFont(new Font("Source Code Pro", Font.PLAIN, 12));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 400);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        updateListBtn = new JButton("UpdateGameList");
        updateListBtn.setBounds(5, 5, 150, 20);
        updateGameBtn = new JButton("UpdateBoard");
        updateGameBtn.setBounds(200, 5, 100, 20);
        briefModel = new DefaultTableModel(briefHeader, 0);
        gameStateModel = new DefaultTableModel(gameStateHeader, 0);
        briefTable = new JTable(briefModel);
        boardModel = new DefaultTableModel();
        boardTable = new JTable(boardModel);
        gameStateTable = new JTable(gameStateModel);
        scrollPane_gameBrief = new JScrollPane(briefTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane_gameBrief.setBounds(5, 50, 180, 280);
        scrollPane_boardState = new JScrollPane(boardTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane
                .HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane_boardState.setBounds(200, 50, 300, 280);
        scrollPane_gameState = new JScrollPane(gameStateTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane_gameState.setBounds(510, 50, 300, 280);

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
                int row = briefTable.getSelectedRow();
                if (row != -1) {
                    String gameId = (String) briefTable.getValueAt(row, 0);
                    if (selectedGame == null || gameId != selectedGame.getGameID())
                        selectedGame = model.getGame(gameId);
                    players = selectedGame.getPlayers();
                    updateBoardTable();
                    updateGameStateTable();
                }
            }
        });


        games = model.getGames();
        if (games == null || games.size() == 0) {
        } else {
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
        briefModel.setDataVector(briefData, briefHeader);

    }

    private void updateBoardTable() {
        board = selectedGame.getBoard();
        int size = board.getSize();
        String[] boardHeader = new String[size];
        for (int i = 0; i < size; i++) {
            boardHeader[i] = "C" + i;
        }
        Object[][] boardData = new Object[size][size];
        Hashtable<Position, Cell> cells = board.getCells();
        Position multi = board.getMultiplier();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardData[j][i] = cells.get(new Position(i, j)).getLetter().getCharacter();
            }
        }
        boardModel.setDataVector(boardData, boardHeader);

        int playerNum = players.size();
        int[][] pos = new int[playerNum][2];
        for (int i = 0; i < playerNum; i++) {
            pos[i][0] = players.get(i).getOrigin().getRow();
            pos[i][1] = players.get(i).getOrigin().getCol();
        }
        int[][] colorBoard = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                colorBoard[i][j] = 0;
            }
        }

        for (int i = 0; i < playerNum; i++) {
            int col = pos[i][0], row = pos[i][1];
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    colorBoard[col][row] += 1;
//                    col++;
                }
//                row++;
            }
        }

        DefaultTableCellRenderer cr = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int
                                                                   column) {
                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        int red = 255 - 15 * colorBoard[j][i], green = 255 - 9 * colorBoard[j][i], blue = 255;
                        c.setBackground(new java.awt.Color(red, green, blue));
                    }

                }
                return super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
            }
        };
        DefaultTableCellRenderer multiCr = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean
                    hasFocus, int row, int column) {
                if (row == multi.getCol())
                    setBackground(Color.GREEN);
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        TableColumn tc = null;
        for (int i = 0; i < size; i++) {
            if (multi.getRow() == i) {
                tc = boardTable.getColumnModel().getColumn(i);
                tc.setCellRenderer(multiCr);
            }
        }

    }


    private void updateGameStateTable() {
        int num = players.size();
        Object[][] gameStateData = new Object[num][3];
        for (int i = 0; i < num; i++) {
            Player p = players.get(i);
            gameStateData[i][0] = p.getName();
            gameStateData[i][1] = p.getOrigin().toString();
            gameStateData[i][2] = p.getScore();
        }
        gameStateModel.setDataVector(gameStateData, gameStateHeader);


    }


}
