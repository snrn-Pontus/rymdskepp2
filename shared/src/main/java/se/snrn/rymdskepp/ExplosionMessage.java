package se.snrn.rymdskepp;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class ExplosionMessage implements Transferable<ExplosionMessage> {

    private Coordinates coordinates;

    public ExplosionMessage() {

    }

    public ExplosionMessage(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeTransferable(coordinates);
    }

    @Override
    public ExplosionMessage deserialize(Deserializer deserializer) throws SerializationException {
        return new ExplosionMessage(deserializer.deserializeTransferable(new Coordinates()));
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
