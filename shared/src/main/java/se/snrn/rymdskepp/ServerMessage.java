package se.snrn.rymdskepp;


import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class ServerMessage implements Transferable<ServerMessage> {

    private String message;



    public ServerMessage() {

    }

    public ServerMessage(String message) {
        this.message = message;
    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeString(message);
    }

    @Override
    public ServerMessage deserialize(Deserializer deserializer) throws SerializationException {
        return new ServerMessage(deserializer.deserializeString());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
