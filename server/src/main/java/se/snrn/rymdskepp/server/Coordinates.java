package se.snrn.rymdskepp.server;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class Coordinates implements Transferable<Coordinates> {
    private float x;
    private float y;
    private long id;
    private float rotation;

    public Coordinates(float x, float y, long id,float rotation) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Coordinates() {
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
        serializer.serializeFloat(x).serializeFloat(y).serializeLong(id).serializeFloat(rotation);
    }

    @Override
    public Coordinates deserialize(Deserializer deserializer) throws SerializationException {
        return new Coordinates(deserializer.deserializeFloat(),
                deserializer.deserializeFloat(),deserializer.deserializeLong(),deserializer.deserializeFloat());
    }
}
