package se.snrn.rymdskepp.server;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
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

        HeadlessApplicationConfiguration headlessApplicationConfiguration = new HeadlessApplicationConfiguration();

        headlessApplicationConfiguration.renderInterval = 0.01f;
        AshleyStarter ashleyStarter = new AshleyStarter(webSocketServer, gameState);


        HeadlessApplication headlessApplication = new HeadlessApplication(ashleyStarter,headlessApplicationConfiguration);


//
//        Thread thread = new Thread(ashleyStarter);
//        thread.start();

    }
}