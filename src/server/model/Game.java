// TODO: map id and name, modify managingplayerID when managerPlayer leaves the Game,
// TODO: verify managingPlayer by id
package server.model;

import server.ClientState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Entity class of game.
 */
public class Game {
    Board board;
    ArrayList<Player> players = new ArrayList<Player>();
    boolean isLocked;
    String gameID;
    //using name to represent the managing player
    String managingPlayerName;
    String managingPlayerID;
    ArrayList<String> clients = new ArrayList<>();

    /**
     * constructor
     *
     * @param gameID
     */
    public Game(String gameID) {
        this.gameID = gameID;
        isLocked = false;
        board = new Board(7);
    }

    /**
     * Add a new player to the game
     *
     * @param player
     * @return success or not
     */
    public boolean addPlayer(Player player, ClientState c) {
        if (isLocked) return false;
        if (players.size() == 0) {
            managingPlayerName = player.getName();
            managingPlayerID = c.id();
        }
        players.add(player);
        addClient(c);
        return true;
    }

    /**
     * Remove a player from the game, if managing player leaves, change the managing player.
     *
     * @param playerName player's name
     * @return success or not
     */
    public boolean removePlayer(String playerName) {
        Iterator<Player> it = players.iterator();
        while (it.hasNext()) {
            Player player = it.next();
            if (playerName.equals(player.getName())) {
                it.remove();
                if (players.size() == 0) {
                    managingPlayerName = null;
                } else if (playerName.equals(managingPlayerName)) {
                    managingPlayerName = players.get(0).getName();
                }
                return true;
            }
        }
        return false;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public String getGameID() {
        return gameID;
    }

    public String getManagingPlayerName() {
        return managingPlayerName;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer(String pname) {
        Iterator<Player> it = players.iterator();
        while (it.hasNext()) {
            Player p = it.next();
            if (p.getName().equals(pname))
                return p;
        }
        return null;

    }

    public void resetGame() {
        board.resetBoard();
        board.resetMultiplier();
        for (Player p : players) {
            p.setPlayer();
        }
    }

    public void addClient(ClientState c) {
        clients.add(c.id());
    }

    public void removeClient(ClientState c) {
        clients.remove(c.id());
    }

    public Collection<String> getClients() {
        return clients;
    }
}
