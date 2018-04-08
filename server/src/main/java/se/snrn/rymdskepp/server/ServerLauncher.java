package se.snrn.rymdskepp.server;

import org.java_websocket.server.WebSocketServer;

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

        WebSocketServer server = new SimpleServer(new InetSocketAddress(host, port));
        server.run();
    }
}