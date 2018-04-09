package se.snrn.rymdskepp;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketAdapter;
import com.github.czyzby.websocket.data.WebSocketCloseCode;
import com.github.czyzby.websocket.net.ExtendedNet;
import com.github.czyzby.websocket.serialization.impl.ManualSerializer;
import se.snrn.rymdskepp.components.TransformComponent;

public class WebsocketManager {
    private static WebSocket socket;
    private ManualSerializer serializer;
    private Entity otherShip;
    private ComponentMapper<TransformComponent> transformMapper;

    public WebsocketManager() {
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        serializer = new ManualSerializer();
        serializer.register(new Coordinates());
        serializer.register(new NewPlayer());
        socket = ExtendedNet.getNet().newWebSocket("localhost", 8000);
        socket.addListener(getListener(this));
//        try {
            socket.connect();
            socket.setSerializer(serializer);

//        }
//        catch (Exception e){
//            System.out.println(e);
//        }
//        finally {
//            socket.close();
//        }

    }

    public void send(Coordinates coordinates) {
        socket.send(coordinates);
    }


    private void readBinary(byte[] packet) {
        Object deserialize = serializer.deserialize(packet);
        if (deserialize instanceof Coordinates) {
            Coordinates coordinates = (Coordinates) deserialize;
            TransformComponent component = transformMapper.get(otherShip);
            component.pos.x = coordinates.getX();
            component.pos.y = coordinates.getY();
            component.rotation = coordinates.getRotation();
        }
        if(deserialize instanceof NewPlayer){
            NewPlayer newPlayer = (NewPlayer) deserialize;

            System.out.println(newPlayer.getId());
        }
    }

    public WebSocketAdapter getListener(final WebsocketManager websocketManager) {

        return new WebSocketAdapter() {

            @Override
            public boolean onOpen(final WebSocket webSocket) {
                Gdx.app.log("WS", "Connected!");
                webSocket.send("Jek!");
                return FULLY_HANDLED;
            }

            @Override
            public boolean onClose(final WebSocket webSocket, final WebSocketCloseCode code, final String reason) {
                Gdx.app.log("WS", "Disconnected - status: " + code + ", reason: " + reason);
                return FULLY_HANDLED;
            }

            @Override
            public boolean onMessage(final WebSocket webSocket, final String packet) {
                Gdx.app.log("WS", "Got message: " + packet);
                return FULLY_HANDLED;
            }

            @Override
            public boolean onMessage(WebSocket webSocket, byte[] packet) {
                readBinary(packet);
                return FULLY_HANDLED;
            }
        };
    }

    public void setOtherShip(Entity otherShip) {
        this.otherShip = otherShip;

    }
}
