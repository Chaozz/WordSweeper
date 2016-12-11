package server.view;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.Component;


/**
 * Created by lyu on 2016/12/10.
 */
public class ServerApplication extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ServerApplication frame = new ServerApplication();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ServerApplication() {
        setTitle("WordSweeper Server Client");
        setFont(new Font("Source Code Pro", Font.PLAIN, 12));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 690, 350);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);


        /**
         * Create Server State Table.
         */
        String[] header = {"Game ID", "Player", "Position", "Score"};
        Object[][] data = new Object[][]{
                // replace with game information
                {1, "Alpha", "C0, R0", 0},
                {1, "Beta", "C2, R2", 0}//,
        };
        JTable table = new JTable(data, header);
        table.setFont(new Font("Source Code Pro", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Source Code Pro", Font.BOLD, 12));
        table.setPreferredScrollableViewportSize(new Dimension(500, 500));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        table.setFillsViewportHeight(true);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        /**
         * Create Server State Table End.
         */

        /**
         * Create Game Global Board.
         */

        String[] boardHeader = {"C0", "C1", "C2", "C3", "C4", "C5", "C6", "C7"}; // replace number with actual size

        Object[][] boardData = new Object[][]{  // replace boardData with actual data
                {"W", "G", "V", "A", "B", "K", "W", "G"},
                {"U", "I", "S", "A", "H", "A", "R", "H"},
                {"D", "R", "T", "F", "E", "B", "M", "G"},
                {"Y", "J", "C", "E", "I", "S", "A", "H"},
                {"P", "N", "G", "U", "M", "D", "A", "C"},
                {"E", "A", "C", "T", "I", "A", "G", "I"},
                {"S", "U", "V", "F", "J", "K", "S", "C"},
                {"A", "X", "S", "O", "E", "F", "G", "H"}
        };
        JTable boardTable = new JTable(boardData, boardHeader);
        boardTable.setCellSelectionEnabled(true);
        boardTable.setColumnSelectionAllowed(true);
        boardTable.setFont(new Font("Source Code Pro", Font.PLAIN, 14));
        boardTable.getTableHeader().setFont(new Font("Source Code Pro", Font.BOLD, 12));
        boardTable.setRowHeight(40);


        DefaultTableCellRenderer render = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                                     boolean isSelected, boolean hasFocus, int row, int column) {
        if ((column < 4 && row < 2) || (column < 2 && row > 1 && row < 4))
            setBackground(new java.awt.Color(229, 229, 229));
        else if (column > 1 && column < 4 && row > 1 && row < 4)
            setBackground(new Color(153, 153, 153));
        else if ((column > 3 && column < 6 && row > 1 && row < 6) || (column > 1 && column < 4 && row > 3 && row < 6))
            setBackground(new Color(170, 212, 255));
        else
            setBackground(new Color(255, 255, 255));
        return super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);
    }
};



        // adjust color with position, number of players
        // e.g 3 players, position = (2,2) (3,3) (4,4), global board size = 8, player board size = 4
//        int playerNum = 3,
//                globalBoardSize = 8,
//                playerBoardSize = 4;
//        int pos[][] = {{2,2},{3,3},{4,4}};
//        int[][] colorBoard = new int[8][8];
//
//        for (int i=0; i < 8; i++){
//            for (int j=0; j < 8; j++){
//                colorBoard[i][j] = 0;
//            }
//        }
//
//        for (int i = 0; i < playerNum; i++) {
//            int col = pos[i][0], row = pos[i][1];
//            for (int j = 0; j < playerBoardSize; j++) {
//                for (int k = 0; k < playerBoardSize; k++) {
//                    colorBoard[col][row] += 1;
//                    col++;
//                }
//                row++;
//            }
//        }
//
//        DefaultTableCellRenderer render = new DefaultTableCellRenderer() {
//            public Component getTableCellRendererComponent(JTable table, Object value,
//                                                           boolean isSelected, boolean hasFocus, int row, int column) {
//                Component c = super.getTableCellRendererComponent(
//                        table, value, isSelected, hasFocus, row, column);
//                for (int i = 0; i < globalBoardSize; i++){
//                    for (int j = 0; j < globalBoardSize; j++){
//                        int red = 255 - 15*colorBoard[j][i], green = 255 - 9*colorBoard[j][i], blue = 255;
//                        c.setBackground(new java.awt.Color(red, green, blue));
//                    }
//
//                }
//                return super.getTableCellRendererComponent(table, value,
//                        isSelected, hasFocus, row, column);
//            }
//        };

//        render.setHorizontalAlignment(SwingConstants.CENTER);

        // replace i with actual global board size
        TableColumn column = null;
        for (int i = 0; i < 8; i++) {
            column = boardTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(40);
            column.setCellRenderer(render);
        }

        boardTable.setPreferredScrollableViewportSize(new Dimension(500, 500));
        boardTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        boardTable.setFillsViewportHeight(true);
        boardTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        /**
         * Create Game Global Board End.
         */

        JScrollPane scrollPane_serverState = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane scrollPane_gameState = new JScrollPane(boardTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane_gameState, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addGap(20)
                                .addComponent(scrollPane_serverState, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(100, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(scrollPane_serverState, GroupLayout.PREFERRED_SIZE, 280, Short.MAX_VALUE)
                                        .addComponent(scrollPane_gameState, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        setResizable(false);
        contentPane.setLayout(gl_contentPane);
    }


}
