package se.snrn.rymdskepp.server;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.utils.Array;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.ManualSerializer;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocketFrame;
import se.snrn.rymdskepp.*;
import se.snrn.rymdskepp.server.ashley.systems.ControlledSystem;

import static se.snrn.rymdskepp.Shared.PORT;

public class WebSocketServer {
    private final Vertx vertx = Vertx.vertx();
    private final ManualSerializer serializer;

    private Engine engine;
    private GameState gameState;
    private ConsoleLogger consoleLogger;

    public WebSocketServer(GameState gameState) {
        this.gameState = gameState;
        serializer = new ManualSerializer();
        MyPackets.register(serializer);
        consoleLogger = ConsoleLogger.getInstance();
    }

    public void launch() {
        consoleLogger.log("Launching web socket server...");
        HttpServerOptions httpServerOptions = new HttpServerOptions();

        HttpServer server = vertx.createHttpServer(httpServerOptions);

        server.connectionHandler(consoleLogger::log);

        server.websocketHandler(webSocket -> {
            consoleLogger.log("Client connecting from " + webSocket.remoteAddress());
            sendWelcomeMessage(webSocket);
            webSocket.frameHandler(frame -> handleFrame(webSocket, frame));
            webSocket.closeHandler(consoleLogger::log);
        }).listen(PORT);
    }

    private void sendWelcomeMessage(ServerWebSocket webSocket) {
        ServerWelcomeMessage serverWelcomeMessage = new ServerWelcomeMessage();
        serverWelcomeMessage.setMessage("Welcome to the server!");
        webSocket.writeFinalBinaryFrame(Buffer.buffer(serializer.serialize(serverWelcomeMessage)));
    }

    public void playerConnected(ServerWebSocket webSocket, NewPlayerConnected newPlayerConnected) {
        int port = webSocket.remoteAddress().port();
        consoleLogger.log(newPlayerConnected.getName() + " joined");
        gameState.getPlayers().add(new Player(webSocket, port, newPlayerConnected.getName()));
        sendAllPlayersToNewPlayer(webSocket);
        sendNewPlayerToAllPlayers(newPlayerConnected,webSocket);

    }

    public void playerDisconnected(int playerId) {
        Player playerToRemove = null;

        for (Player player : gameState.getPlayers()) {
            if (player.getId() == playerId) {
                playerToRemove = player;
                break;
            }
        }
        if (playerToRemove != null) {
            gameState.getPlayers().remove(playerToRemove);
        }
    }

    private void sendNewPlayerToAllPlayers(NewPlayerConnected newPlayerConnected, ServerWebSocket webSocket) {
        newPlayerConnected.setId(webSocket.remoteAddress().port());
        if (!gameState.getPlayers().isEmpty()) {
            for (Player player : gameState.getPlayers()) {
                if (player.getPort() != newPlayerConnected.getId()) {
                    player.getWebSocket().writeFinalBinaryFrame(Buffer.buffer(serializer.serialize(newPlayerConnected)));
                    consoleLogger.log("sent " + newPlayerConnected.getName() + " to " + player.getName());
                }
            }
        }
    }

    private void sendAllPlayersToNewPlayer(ServerWebSocket webSocket) {
        for (Player player : gameState.getPlayers()) {
//            if (player.getPort() != webSocket.remoteAddress().port()) {
                NewPlayerConnected newPlayerConnected = new NewPlayerConnected();
                newPlayerConnected.setId(player.getPort());
                newPlayerConnected.setName(player.getName());
                newPlayerConnected.setShipType(ShipType.BLUE);
                webSocket.writeFinalBinaryFrame(Buffer.buffer(serializer.serialize(newPlayerConnected)));
                consoleLogger.log("sent " + player.getId() + " to " + webSocket.remoteAddress().port());
//            }
        }
    }

    public void sendToAllPlayers(Transferable transferable) {
        if (!gameState.getPlayers().isEmpty()) {
            for (Player player : gameState.getPlayers()) {
                player.getWebSocket().writeFinalBinaryFrame(Buffer.buffer(serializer.serialize(transferable)));
            }
        }
    }

    private void handleFrame(final ServerWebSocket webSocket, final WebSocketFrame frame) {
        final Object request = serializer.deserialize(frame.binaryData().getBytes());
        consoleLogger.log("Received packet: " + request);
        if (request instanceof NewPlayerConnected) {
            NewPlayerConnected newPlayerConnected = (NewPlayerConnected) request;
            playerConnected(webSocket, newPlayerConnected);
        }
        if (request instanceof CommandMessage) {
            if (this.engine != null) {
                ControlledSystem controlledSystem = engine.getSystem(ControlledSystem.class);
                CommandMessage commandMessage = (CommandMessage) request;
                commandMessage.setId(webSocket.remoteAddress().port());
                controlledSystem.handleCommand(commandMessage);
            }
        }
    }


    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void sendBulletToAllPlayers(Coordinates coordinates) {
        if (!gameState.getPlayers().isEmpty()) {
            for (Player player : gameState.getPlayers()) {
                player.getWebSocket().writeFinalBinaryFrame(Buffer.buffer(serializer.serialize(coordinates)));
            }
        }
    }
}