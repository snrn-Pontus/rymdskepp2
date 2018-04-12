package se.snrn.rymdskepp.server;

import se.snrn.rymdskepp.server.ashley.AshleyStarter;

/**
 * Launches the server application.
 */
public class ServerLauncher {
    public static void main(final String... args) throws Exception {
        GameState gameState = new GameState();


        Thread loggerThread = new Thread(ConsoleLogger.getInstance());
        loggerThread.start();

        WebSocketServer webSocketServer = new WebSocketServer(gameState);
        webSocketServer.launch();


        AshleyStarter ashleyStarter = new AshleyStarter(webSocketServer, gameState);
        Thread thread = new Thread(ashleyStarter);
        thread.start();

    }
}