package se.snrn.rymdskepp.server;

import java.util.HashSet;

public class GameState {

    private final HashSet<Player> players;

    public GameState() {
        players = new HashSet<>();

    }

    public HashSet<Player> getPlayers() {
        return players;
    }


}
