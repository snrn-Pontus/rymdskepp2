package se.snrn.rymdskepp;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class DisconnectMessage implements Transferable<DisconnectMessage> {

    private long playerId;

    public DisconnectMessage(long playerId) {
        this.playerId = playerId;
    }

    public DisconnectMessage() {
    }


    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeLong(playerId);
    }

    @Override
    public DisconnectMessage deserialize(Deserializer deserializer) throws SerializationException {
        return new DisconnectMessage(deserializer.deserializeLong());
    }
}
