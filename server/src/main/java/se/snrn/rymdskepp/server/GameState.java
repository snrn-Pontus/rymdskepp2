package se.snrn.rymdskepp.server;

import java.util.*;

public class GameState {
    private static GameState ourInstance = new GameState();


    private final HashSet<Player> players;

    public static GameState getInstance() {
        return ourInstance;
    }

    private GameState() {
        players = new HashSet<>();
    }

    public HashSet<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getTopList() {
        ArrayList<Player> sortedList = new ArrayList<>(players);
        sortedList.sort(Comparator.comparingInt(Player::getScore));
        return sortedList;
    }
}
