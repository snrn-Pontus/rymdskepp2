package se.snrn.rymdskepp.server;

/**
 * Launches the server application.
 */
public class ServerLauncher {
    public static void main(final String... args) {
        GameState gameState = GameState.getInstance();


        Thread loggerThread = new Thread(Console.getInstance());
        loggerThread.start();

        WebSocketServer webSocketServer = new WebSocketServer();
        webSocketServer.launch();


        AshleyStarter ashleyStarter = new AshleyStarter(webSocketServer, gameState);
        Thread thread = new Thread(ashleyStarter);
        thread.start();

    }
}