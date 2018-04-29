package se.snrn.rymdskepp.server.systems;

import com.badlogic.ashley.systems.IntervalSystem;
import se.snrn.rymdskepp.GameStatusMessage;
import se.snrn.rymdskepp.server.Player;
import se.snrn.rymdskepp.server.WebSocketServer;

import java.util.ArrayList;
import java.util.HashSet;

public class GameStateSender extends IntervalSystem {
    private WebSocketServer webSocketServer;
    private HashSet<Player> players;
    private long[] ids;
    private int[] scores;

    public GameStateSender(WebSocketServer webSocketServer, HashSet<Player> players) {
        super(1);
        this.webSocketServer = webSocketServer;
        this.players = players;
        ids = new long[10];
        scores = new int[10];
    }

    @Override
    protected void updateInterval() {
        GameStatusMessage gameStatusMessage = new GameStatusMessage();
        int i = 0;
        for (Player player : players) {

            ids[i] = player.getId();
            scores[i] = player.getScore();
            i++;
        }

        gameStatusMessage.setIds(ids);
        gameStatusMessage.setScores(scores);


        webSocketServer.sendToAllPlayers(gameStatusMessage);
    }
}
