package server.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

/**
 * HACK: Replace with actual functionality!
 */

public class ServerModel {
    /* -------------------- */
    int numPlayers = 0;

    public void joinGame() {
        numPlayers++;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    /* -------------------- */
    boolean isEmpty = true;

    ArrayList<Game> games = new ArrayList<Game>();

    public ArrayList<Game> getGames() {
        return games;
    }

    /**
     * Create a new game, have fun.
     *
     * @param playerName
     * @return the game ID
     */
    public String createGame(String playerName) {
        Player creator = new Player(playerName);
        String gameID;
        if (isEmpty) {
            gameID = "b6b9e90b-6ff2-4e58-9940-f1ae38c03608";
            isEmpty = false;
        } else {
            gameID = UUID.randomUUID().toString();
        }
        Game game = new Game(gameID);
        game.addPlayer(creator);
        games.add(game);
        return gameID;
    }

    /**
     * Join an existing game.
     *
     * @param playerName
     * @param gameID
     * @return success or not
     */
    public boolean joinGame(String playerName, String gameID) {
        Player player = new Player(playerName);
        Game game = getGame(gameID);
        if (game == null) return false;
        if (game.isLocked()) return false;
        game.addPlayer(player);
        return true;
    }

    /**
     * Get a game object by game ID.
     *
     * @param gameID
     * @return
     */
    public Game getGame(String gameID) {
        for (Game game : games) {
            if (game.getGameID().equals(gameID)) {
                return game;
            }
        }
        return null;
    }

    /**
     * Remove a game by game ID.
     *
     * @param gameID
     * @return
     */
    public boolean removeGame(String gameID) {
        Iterator<Game> it = games.iterator();
        while (it.hasNext()) {
            Game game = it.next();
            if (game.getGameID().equals(gameID)) {
                it.remove();
                return true;
            }
        }
        return false;
    }
}
