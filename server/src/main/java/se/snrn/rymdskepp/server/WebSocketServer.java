package se.snrn.rymdskepp.server;

import com.badlogic.ashley.core.Engine;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.ManualSerializer;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocketFrame;
import se.snrn.rymdskepp.*;
import se.snrn.rymdskepp.server.systems.ControlledSystem;
import se.snrn.rymdskepp.server.systems.GameStateSender;

import static se.snrn.rymdskepp.Shared.PORT;

public class WebSocketServer {
    private final Vertx vertx = Vertx.vertx();
    private ManualSerializer serializer;

    private Engine engine;
    private GameState gameState;
    private Console console;
    private static WebSocketServer ourInstance = new WebSocketServer();

    public static WebSocketServer getInstance() {
        return ourInstance;
    }

    public WebSocketServer() {

    }

    public void launch() {

        this.gameState = GameState.getInstance();
        serializer = new ManualSerializer();
        MyPackets.register(serializer);
        this.console = Console.getInstance();

        console.log("Launching web socket server...");
        HttpServerOptions httpServerOptions = new HttpServerOptions();

        HttpServer server = vertx.createHttpServer(httpServerOptions);
        httpServerOptions.setLogActivity(true);

        server.connectionHandler(console::log);

        server.websocketHandler(webSocket -> {
            console.log("Client connecting from " + webSocket.remoteAddress());
            sendWelcomeMessage(webSocket);
            webSocket.frameHandler(frame -> handleFrame(webSocket, frame));
            webSocket.closeHandler(myVoid -> {
                console.log("player disconnected");
                Player playerToRemove = null;
                for (Player player : gameState.getPlayers()) {
                    if (webSocket == player.getWebSocket()) {
                        playerToRemove = player;
                    }
                }
                if (playerToRemove != null) {
                    gameState.getPlayers().remove(playerToRemove);
                    sendToAllPlayers(new DisconnectMessage(playerToRemove.getId()));
                }
            });
        }).listen(PORT);
    }

    private void sendWelcomeMessage(ServerWebSocket webSocket) {
        ServerWelcomeMessage serverWelcomeMessage = new ServerWelcomeMessage();
        serverWelcomeMessage.setMessage("Welcome to the server!");
        webSocket.writeFinalBinaryFrame(Buffer.buffer(serializer.serialize(serverWelcomeMessage)));
    }

    public void playerConnected(ServerWebSocket webSocket, NewPlayerConnected newPlayerConnected) {
        int port = webSocket.remoteAddress().port();
        Console.getInstance().log(newPlayerConnected.getName() + " joined");
        Player player = new Player(webSocket, port, newPlayerConnected.getName(), newPlayerConnected.getShipType());
        player.setConnected(true);
        gameState.getPlayers().add(player);
        gameState.getUnSpawnedPlayers().add(player);
        engine.addEntity(player);
        sendAllPlayersToNewPlayer(webSocket);
        sendNewPlayerToAllPlayers(newPlayerConnected, webSocket);
        engine.getSystem(GameStateSender.class).setShouldSend(true);

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
        for (Player player : gameState.getPlayers()) {
            if (player.getPort() != newPlayerConnected.getId()) {
                player.getWebSocket().writeFinalBinaryFrame(Buffer.buffer(serializer.serialize(newPlayerConnected)));
                Console.getInstance().log("sent " + newPlayerConnected.getName() + " to " + player.getName());
            }
        }
    }

    private void sendAllPlayersToNewPlayer(ServerWebSocket webSocket) {
        for (Player player : gameState.getPlayers()) {
            NewPlayerConnected newPlayerConnected = new NewPlayerConnected();
            newPlayerConnected.setId(player.getPort());
            newPlayerConnected.setName(player.getName());
            newPlayerConnected.setShipType(player.getShipType());
            if (player.getPort() == webSocket.remoteAddress().port()) {
                newPlayerConnected.setYou(true);
            }
            webSocket.writeFinalBinaryFrame(Buffer.buffer(serializer.serialize(newPlayerConnected)));
            Console.getInstance().log("sent " + player.getId() + " to " + webSocket.remoteAddress().port());
//            }
        }
    }

    public void sendToAllPlayers(Transferable transferable) {
        if (!gameState.getPlayers().isEmpty()) {
            for (Player player : gameState.getPlayers()) {
                try {
                    player.getWebSocket().writeFinalBinaryFrame(Buffer.buffer(serializer.serialize(transferable)));
                } catch (IllegalStateException illegalStateException) {
                    System.out.println(illegalStateException.getMessage());
                }
            }
        }
    }

    private void handleFrame(final ServerWebSocket webSocket, final WebSocketFrame frame) {
        final Object request = serializer.deserialize(frame.binaryData().getBytes());
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
        if (request instanceof ServerCommand) {
            ServerCommand serverCommand = (ServerCommand) request;
            Console.getInstance().executeCommand(serverCommand.getCommand());
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