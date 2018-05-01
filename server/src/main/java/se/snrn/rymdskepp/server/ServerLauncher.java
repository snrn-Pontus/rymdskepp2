package se.snrn.rymdskepp.server;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

/**
 * Launches the server application.
 */
public class ServerLauncher {
    public static void main(final String... args) {
        GameState gameState = GameState.getInstance();

        WebSocketServer webSocketServer = WebSocketServer.getInstance();
        webSocketServer.launch();

        Thread loggerThread = new Thread(Console.getInstance());
        loggerThread.start();

        WebServer webServer = new WebServer();
        webServer.launch();

        HeadlessGame headlessGame = new HeadlessGame(WebSocketServer.getInstance(), gameState);

        HeadlessApplicationConfiguration headlessApplicationConfiguration = new HeadlessApplicationConfiguration();

//        headlessApplicationConfiguration.renderInterval = 0.01f;

        HeadlessApplication headlessApplication = new HeadlessApplication(headlessGame,headlessApplicationConfiguration);

    }
}