package se.snrn.rymdskepp;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class NewPlayerConnected implements Transferable<NewPlayerConnected> {

    private long id;
    private String name;
    private int shipType;
    private boolean you = false;


    public NewPlayerConnected(long id, String name, int shipType, boolean you) {
        this.id = id;
        this.name = name;
        this.shipType = shipType;
        this.you = you;
    }

    public NewPlayerConnected() { }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeLong(id).serializeString(name).serializeInt(shipType).serializeBoolean(you);
    }

    @Override
    public NewPlayerConnected deserialize(Deserializer deserializer) throws SerializationException {
        return new NewPlayerConnected(deserializer.deserializeLong(), deserializer.deserializeString(), deserializer.deserializeInt(), deserializer.deserializeBoolean());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShipType() {
        return shipType;
    }

    public void setShipType(int shipType) {
        this.shipType = shipType;
    }

    public void setYou(boolean you) {
        this.you = you;
    }

    public boolean getYou() {
        return you;
    }
}
