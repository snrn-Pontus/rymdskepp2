package se.snrn.rymdskepp;


import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class NetworkObject implements Transferable<NetworkObject> {

    private long id;
    private ObjectType objectType;

    public long getId() {
        return id;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public NetworkObject() {
    }

    public NetworkObject(long id, ObjectType objectType) {

        this.id = id;
        this.objectType = objectType;
    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeLong(id).serializeEnum(objectType);
    }

    @Override
    public NetworkObject deserialize(Deserializer deserializer) throws SerializationException {
        return new NetworkObject(deserializer.deserializeLong(), deserializer.deserializeEnum(ObjectType.values()));
    }
}
