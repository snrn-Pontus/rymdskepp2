package se.snrn.rymdskepp.server;

import org.java_websocket.server.WebSocketServer;
import se.snrn.rymdskepp.server.ashley.AshleyStarter;

import java.net.InetSocketAddress;

/**
 * Launches the server application.
 */
public class ServerLauncher {
    public static void main(final String... args) throws Exception {
        GameState gameState = new GameState();
//        Server server = new Server();

        String host = "localhost";
        int port = 8000;


        SimpleServer server = new SimpleServer(new InetSocketAddress(host, port));
        Thread serverThread = new Thread(server);
        serverThread.run();
        AshleyStarter ashleyStarter = new AshleyStarter(server);

        Thread thread = new Thread(ashleyStarter);
        thread.run();

    }
}