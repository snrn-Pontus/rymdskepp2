package se.snrn.rymdskepp.server;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

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


        HeadlessGame headlessGame = new HeadlessGame(webSocketServer, gameState);

        HeadlessApplicationConfiguration headlessApplicationConfiguration = new HeadlessApplicationConfiguration();

        headlessApplicationConfiguration.renderInterval = 0.01f;

        HeadlessApplication headlessApplication = new HeadlessApplication(headlessGame,headlessApplicationConfiguration);

    }
}