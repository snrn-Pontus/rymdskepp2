package se.snrn.rymdskepp.server;

import com.badlogic.ashley.core.Engine;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.ManualSerializer;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocketFrame;
import se.snrn.rymdskepp.CommandMessage;
import se.snrn.rymdskepp.MyPackets;
import se.snrn.rymdskepp.Ping;
import se.snrn.rymdskepp.Pong;
import se.snrn.rymdskepp.server.ashley.systems.ControlledSystem;

import java.util.HashMap;

import static se.snrn.rymdskepp.Shared.PORT;

// Note that server web socket implementation is not provided by gdx-websocket. This class uses an external library: Vert.x.
public class WebSocketServer {
    private final Vertx vertx = Vertx.vertx();
    private final ManualSerializer serializer;

    private HashMap<Integer, ServerWebSocket> players;
    private Engine engine;

    public WebSocketServer() {
        serializer = new ManualSerializer();
        MyPackets.register(serializer);
        players = new HashMap<>();
    }

    public void launch() {
        System.out.println("Launching web socket server...");
        HttpServer server = vertx.createHttpServer();
        server.connectionHandler(System.out::println);
        server.websocketHandler(webSocket -> {
            // Printing received packets to console, sending response:
            webSocket.frameHandler(frame -> handleFrame(webSocket, frame));
            players.put(webSocket.remoteAddress().port(), webSocket);
        }).listen(PORT);
    }

    public void send(Transferable transferable) {
        if (!players.isEmpty()) {

            players.forEach((integer, webSocket) -> {
                webSocket.writeFinalBinaryFrame(Buffer.buffer(serializer.serialize(transferable)));
            });

        } else {

        }
    }

    private void handleFrame(final ServerWebSocket webSocket, final WebSocketFrame frame) {
        // Deserializing received message:
        final Object request = serializer.deserialize(frame.binaryData().getBytes());
        System.out.println("Received packet: " + request);
        // Sending a response - Ping to Pong, Pong to Ping.
        if (request instanceof Ping) {
            final Pong response = new Pong();
            response.setValue(((Ping) request).getValue() / 2f);
            response.setServer(true);
            send(response);
        } else if (request instanceof Pong) {
            final Ping response = new Ping();
            response.setValue((int) ((Pong) request).getValue() * 2);
            response.setClient(false);
            send(response);
        } else if (request instanceof CommandMessage){
            if(this.engine != null){
                ControlledSystem system = engine.getSystem(ControlledSystem.class);
                CommandMessage commandMessage = ((CommandMessage) request);
                switch (commandMessage.getCommand()) {
                    case LEFT:
                        system.setXVelocity(-0.25f);
                        break;
                    case RIGHT:
                        system.setXVelocity(0.25f);
                        break;
                }
            }
        }
    }


    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Engine getEngine() {
        return engine;
    }
}