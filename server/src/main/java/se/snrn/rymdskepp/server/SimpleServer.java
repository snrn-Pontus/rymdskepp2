package se.snrn.rymdskepp.server;

import com.github.czyzby.websocket.serialization.impl.ManualSerializer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import se.snrn.rymdskepp.Coordinates;
import se.snrn.rymdskepp.NewPlayer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class SimpleServer extends WebSocketServer {
    private ManualSerializer serializer;
    private static long id = 0;

    public SimpleServer(InetSocketAddress address) {
        super(address);
        serializer = new ManualSerializer();
        serializer.register(new Coordinates());
        serializer.register(new NewPlayer());
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Welcome to the server!"); //This method sends a message to the new client
        broadcast("new connection: " + handshake.getResourceDescriptor()); //This method sends a message to all clients connected
        System.out.println("new connection to " + conn.getRemoteSocketAddress());
        NewPlayer newPlayer = new NewPlayer();
        newPlayer.setId(id);
        id++;
        sendNewPlayer(newPlayer);
    }

    private void readByteBuffer(ByteBuffer message) {
        broadcast(message.array());
    }

    public void sendObject(Coordinates coordinates){
        byte[] serialize = serializer.serialize(coordinates);
        broadcast(serialize);
    }
    private void sendNewPlayer(NewPlayer newPlayer){
        byte[] serialize = serializer.serialize(newPlayer);
        broadcast(serialize);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("received message from " + conn.getRemoteSocketAddress() + ": " + message);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        System.out.println("received ByteBuffer from " + conn.getRemoteSocketAddress());
        readByteBuffer(message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println(ex);
        System.err.println("an error occured on connection " + conn.getRemoteSocketAddress() + ":" + ex);
    }

    @Override
    public void onStart() {
        System.out.println("server started successfully");
    }

}