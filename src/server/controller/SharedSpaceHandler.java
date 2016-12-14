package server.controller;

import server.model.Game;
import server.model.Player;
import server.model.Position;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by tianhao on 12/9/16.
 */
public class SharedSpaceHandler {
    public static int numOfSharedPlayers(Game game, Position position) {
        Hashtable<Position, Integer> sharedNum = new Hashtable<>();
        ArrayList<Player> players = game.getPlayers();
        for (Player p : players) {
            Position origin = p.getOrigin();
            for (int i = origin.getRow(); i < origin.getRow() + 4; i++) {
                for (int j = origin.getCol(); j < origin.getCol() + 4; j++) {
                    Position temp = new Position(j, i);
                    if (sharedNum.containsKey(temp)) {
                        sharedNum.put(temp, sharedNum.get(temp) + 1);
                    } else {
                        sharedNum.put(temp, 1);
                    }
                }
            }
        }
        if(sharedNum.containsKey(position)) {
            return sharedNum.get(position);
        }
        return 0;
    }

}
