package se.snrn.rymdskepp.server;

import se.snrn.rymdskepp.server.ashley.AshleyStarter;

/**
 * Launches the server application.
 */
public class ServerLauncher {
    public static void main(final String... args) throws Exception {
        GameState gameState = new GameState();

        WebSocketServer webSocketServer = new WebSocketServer();
        webSocketServer.launch();
        AshleyStarter ashleyStarter = new AshleyStarter(webSocketServer);

        Thread thread = new Thread(ashleyStarter);
        thread.run();
    }
}