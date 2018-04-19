package se.snrn.rymdskepp;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class ServerCommand implements Transferable<ServerCommand> {

    private String command;

    public ServerCommand() {

    }
    public ServerCommand(String command) {
        this.command = command;
    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeString(command);
    }

    @Override
    public ServerCommand deserialize(Deserializer deserializer) throws SerializationException {
        return new ServerCommand(deserializer.deserializeString());
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
