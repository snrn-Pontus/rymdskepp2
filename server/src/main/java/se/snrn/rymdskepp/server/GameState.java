package se.snrn.rymdskepp.server;

import java.util.ArrayList;

public class GameState {

    private final ArrayList<Player> players;

    public GameState() {
        players = new ArrayList<>();

    }

    public ArrayList<Player> getPlayers() {
        return players;
    }


}
