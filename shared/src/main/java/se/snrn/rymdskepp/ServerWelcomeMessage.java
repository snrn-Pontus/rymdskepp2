package se.snrn.rymdskepp;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class ServerWelcomeMessage implements Transferable<ServerWelcomeMessage> {

    private String message;

    public ServerWelcomeMessage(String message) {
        this.message = message;
    }

    public ServerWelcomeMessage() {

    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeString(message);
    }

    @Override
    public ServerWelcomeMessage deserialize(Deserializer deserializer) throws SerializationException {
        return new ServerWelcomeMessage(deserializer.deserializeString());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
