package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketHandler;
import com.github.czyzby.websocket.WebSocketHandler.Handler;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.data.WebSocketCloseCode;
import com.github.czyzby.websocket.data.WebSocketException;
import com.github.czyzby.websocket.net.ExtendedNet;
import com.github.czyzby.websocket.serialization.impl.ManualSerializer;
import se.snrn.rymdskepp.components.PlayerComponent;
import se.snrn.rymdskepp.systems.ClientNetworkSystem;

public class WebSocketClient {


    private Engine engine;
    private Rymdskepp rymdskepp;
    private WebSocket socket;
    private ClientNetworkSystem clientNetworkSystem;


    public WebSocketClient(Engine engine, Rymdskepp rymdskepp, String serverAddress, int serverPort) {
        this.clientNetworkSystem = engine.getSystem(ClientNetworkSystem.class);
        this.rymdskepp = rymdskepp;
        this.engine = engine;
        // Note: you can also use WebSockets.newSocket() and WebSocket.toWebSocketUrl() methods.
        socket = ExtendedNet.getNet().newWebSocket(serverAddress, serverPort);
//        socket = ExtendedNet.getNet().newSecureWebSocket(serverAddress, serverPort);
        socket.addListener(getListener());

        // Creating a new ManualSerializer - this replaces the default JsonSerializer and allows to use the
        // serialization mechanism from gdx-websocket-serialization library.
        final ManualSerializer serializer = new ManualSerializer();
        socket.setSerializer(serializer);
        // Registering all expected packets:
        MyPackets.register(serializer);

        // Connecting with the server.
        try {
            socket.connect();
        } catch (WebSocketException e) {
            e.printStackTrace();
            rymdskepp.lobbyScreen.connectionError(e);
//            firstScreen.connectionError();
        }

    }

    private WebSocketListener getListener() {
        // WebSocketHandler is an implementation of WebSocketListener that uses the current Serializer (ManualSerializer
        // in this case) to create objects from received raw data. Instead of forcing you to work with Object and do
        // manual casting, this listener allows to register handlers for each expected packet class.
        final WebSocketHandler handler = new WebSocketHandler() {
            @Override
            public boolean onError(WebSocket webSocket, Throwable error) {
                return super.onError(webSocket, error);
            }
        };



        handler.registerHandler(NetworkObject.class, (Handler<NetworkObject>) (webSocket, packet) -> {
//            System.out.println("Received NetworkObject: " + packet.getObjectType() + "!");
            clientNetworkSystem.handle(packet);
            return true;
        });
        handler.registerHandler(Coordinates.class, (Handler<Coordinates>) (webSocket, packet) -> {
//            System.out.println("Received Coordinates: " + packet.getX() + "!");
            return true;
        });
        handler.registerHandler(NewPlayerConnected.class, (Handler<NewPlayerConnected>) (webSocket, packet) -> {
            if (rymdskepp.gameScreen != null && rymdskepp.gameScreen.getConsole() != null) {
                rymdskepp.gameScreen.getConsole().log("Received NewPlayerConnected: " + packet.getName() + "!");
            }
            rymdskepp.getPlayersToSpawn().add(packet);
            return true;
        });
        handler.registerHandler(ServerWelcomeMessage.class, (Handler<ServerWelcomeMessage>) (webSocket, packet) -> {
            System.out.println(packet.getMessage());
            rymdskepp.lobbyScreen.connectionEstablished();
            return true;
        });
        handler.registerHandler(ServerMessage.class, (Handler<ServerMessage>) (webSocket, packet) -> {
            if (rymdskepp.gameScreen != null && rymdskepp.gameScreen.getConsole() != null) {
                rymdskepp.gameScreen.getConsole().log("Server: " + packet.getMessage());
            }
            return true;
        });
        handler.registerHandler(ExplosionMessage.class, (Handler<ExplosionMessage>) (webSocket, packet) -> {
            if (rymdskepp.gameScreen != null) {
                GameScreen.soundSignal.dispatch(SoundEnum.EXPLODE);

                System.out.println("Create explosion");
                rymdskepp.gameScreen.getExplosionsToSpawn().add(packet);
            }
            return true;
        });
        handler.registerHandler(DisconnectMessage.class, (Handler<DisconnectMessage>) (webSocket, packet) -> {
            if (rymdskepp.gameScreen != null) {
                rymdskepp.gameScreen.getPlayersToRemove().add(packet.getPlayerId());
            }
            return true;
        });
        handler.registerHandler(GameStatusMessage.class, (Handler<GameStatusMessage>) (webSocket, packet) -> {
            if (rymdskepp.gameScreen != null) {
                for (int i = 0; i < packet.getIds().length; i++) {
                    if (packet.getIds()[i] != 0) {
                        Entity player = rymdskepp.getPlayers().get(packet.getIds()[i]);


                        PlayerComponent playerComponent = Mappers.playerMapper.get(player);
                        playerComponent.setId(packet.getIds()[i]);
                        playerComponent.setScore(packet.getScores()[i]);
                    }
                }
                rymdskepp.gameScreen.getPlayerStatusUI().updatePlayers();
            }
            return true;
        });
        // Side note: this would be a LOT cleaner with Java 8 lambdas (or using another JVM language, like Kotlin).
        return handler;
    }

    public void joinGame(String name, int selectedShip) {
        NewPlayerConnected newPlayerConnected = new NewPlayerConnected();
        newPlayerConnected.setName(name);
        newPlayerConnected.setId(0);
        newPlayerConnected.setShipType(selectedShip);
        socket.send(newPlayerConnected);
    }

    public void sendCommand(Command command) {
        final CommandMessage commandMessage = new CommandMessage();
        commandMessage.setCommand(command);
        commandMessage.setId(0);
        socket.send(commandMessage);
    }

    public void sendServerCommand(String command) {
        final ServerCommand commandMessage = new ServerCommand();
        commandMessage.setCommand(command);
        socket.send(commandMessage);
    }

    public void disconnect() {
        socket.close();
    }
}