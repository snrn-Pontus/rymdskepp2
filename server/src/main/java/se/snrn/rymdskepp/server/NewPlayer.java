package se.snrn.rymdskepp.server;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class NewPlayer implements Transferable<NewPlayer> {
    private float x;
    private float y;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NewPlayer(float x, float y, long id) {
        this.x = x;
        this.y = y;
        this.id = id;

    }

    public NewPlayer() {
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeFloat(x).serializeFloat(y).serializeLong(id);
    }

    @Override
    public NewPlayer deserialize(Deserializer deserializer) throws SerializationException {
        return new NewPlayer(deserializer.deserializeFloat(),
                deserializer.deserializeFloat(),deserializer.deserializeLong());
    }
}
