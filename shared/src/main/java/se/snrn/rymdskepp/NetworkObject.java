package se.snrn.rymdskepp;


import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class NetworkObject implements Transferable<NetworkObject> {

    private long id;
    private ObjectType objectType;
    private Coordinates coordinates;
    private boolean remove;
    private int shipType = 0;



    private State state = State.DEFAULT;


    public NetworkObject() {
    }

    public NetworkObject(long id, ObjectType objectType, Coordinates coordinates, boolean remove, State state, int shipType) {

        this.shipType = shipType;
        this.id = id;
        this.objectType = objectType;
        this.coordinates = coordinates;
        this.remove = remove;
        this.state = state;
    }

    public int getShipType() {
        return shipType;
    }

    public void setShipType(int shipType) {
        this.shipType = shipType;
    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeLong(id).serializeEnum(objectType).serializeTransferable(coordinates).serializeBoolean(remove).serializeEnum(state).serializeInt(shipType);
    }

    @Override
    public NetworkObject deserialize(Deserializer deserializer) throws SerializationException {
        return new NetworkObject(deserializer.deserializeLong(), deserializer.deserializeEnum(ObjectType.values()), deserializer.deserializeTransferable(new Coordinates()), deserializer.deserializeBoolean(), deserializer.deserializeEnum(State.values()),deserializer.deserializeInt());
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public long getId() {
        return id;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
