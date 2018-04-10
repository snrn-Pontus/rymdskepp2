package se.snrn.rymdskepp;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class NewPlayerConnected implements Transferable<NewPlayerConnected> {

    private long id;
    private String name;
    private ShipType shipType;

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

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public NewPlayerConnected(long id, String name, ShipType shipType) {

        this.id = id;
        this.name = name;
        this.shipType = shipType;
    }

    public NewPlayerConnected() {

    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeLong(id).serializeString(name).serializeEnum(shipType);
    }

    @Override
    public NewPlayerConnected deserialize(Deserializer deserializer) throws SerializationException {
        return new NewPlayerConnected(deserializer.deserializeLong(), deserializer.deserializeString(), deserializer.deserializeEnum(ShipType.values()));
    }
}
