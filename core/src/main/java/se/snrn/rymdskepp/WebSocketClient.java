package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Engine;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketHandler;
import com.github.czyzby.websocket.WebSocketHandler.Handler;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.net.ExtendedNet;
import com.github.czyzby.websocket.serialization.impl.ManualSerializer;
import se.snrn.rymdskepp.systems.NetworkSystem;

import static se.snrn.rymdskepp.Shared.PORT;

public class WebSocketClient {


    private WebSocket socket;
    private NetworkSystem networkSystem;
    private FirstScreen firstScreen;


    public WebSocketClient(Engine engine, FirstScreen firstScreen) {
        this.networkSystem = engine.getSystem(NetworkSystem.class);
        this.firstScreen = firstScreen;
        // Note: you can also use WebSockets.newSocket() and WebSocket.toWebSocketUrl() methods.
        socket = ExtendedNet.getNet().newWebSocket("192.168.88.247", PORT);
        socket.addListener(getListener());
        // Creating a new ManualSerializer - this replaces the default JsonSerializer and allows to use the
        // serialization mechanism from gdx-websocket-serialization library.
        final ManualSerializer serializer = new ManualSerializer();
        socket.setSerializer(serializer);
        // Registering all expected packets:
        MyPackets.register(serializer);

        // Connecting with the server.
        socket.connect();

    }

    private WebSocketListener getListener() {
        // WebSocketHandler is an implementation of WebSocketListener that uses the current Serializer (ManualSerializer
        // in this case) to create objects from received raw data. Instead of forcing you to work with Object and do
        // manual casting, this listener allows to register handlers for each expected packet class.
        final WebSocketHandler handler = new WebSocketHandler();


        // Registering Ping handler:
        handler.registerHandler(Ping.class, (Handler<Ping>) (webSocket, packet) -> {
            System.out.println("Received PING: " + packet.getValue() + "!");
            return true;
        });
        // Registering Pong handler:
        handler.registerHandler(Pong.class, (Handler<Pong>) (webSocket, packet) -> {
            System.out.println("Received PONG: " + packet.getValue() + "!");
            return true;
        });
        handler.registerHandler(NetworkObject.class, (Handler<NetworkObject>) (webSocket, packet) -> {
            System.out.println("Received NetworkObject: " + packet.getObjectType() + "!");
            return true;
        });
        handler.registerHandler(Coordinates.class, (Handler<Coordinates>) (webSocket, packet) -> {
//            System.out.println("Received Coordinates: " + packet.getX() + "!");
            networkSystem.handle(packet);
            return true;
        });
        handler.registerHandler(NewPlayerConnected.class, (Handler<NewPlayerConnected>) (webSocket, packet) -> {
            System.out.println("Received NewPlayerConnected: " + packet.getId() + "!");
            firstScreen.getPlayersToSpawn().add(packet.getId());
            return true;
        });
        // Side note: this would be a LOT cleaner with Java 8 lambdas (or using another JVM language, like Kotlin).
        return handler;
    }

    public void sendPingPacket(int pingValue) {
        final Ping ping = new Ping();
        ping.setValue(pingValue);
        ping.setClient(true);
        socket.send(ping);
    }

    public void sendPongPacket(float pongValue) {
        final Pong pong = new Pong();
        pong.setValue(pongValue);
        pong.setServer(false);
        socket.send(pong);
    }

    public void sendCommand(Command command) {
        final CommandMessage commandMessage = new CommandMessage();
        commandMessage.setCommand(command);
        commandMessage.setId(1);
        socket.send(commandMessage);
    }
}