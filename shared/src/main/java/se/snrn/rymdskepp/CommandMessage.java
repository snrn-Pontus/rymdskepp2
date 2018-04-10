package se.snrn.rymdskepp;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class CommandMessage implements Transferable<CommandMessage> {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    private Command command;

    public CommandMessage(long id, Command command) {
        this.id = id;
        this.command = command;
    }

    public CommandMessage() {

    }


    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeLong(id).serializeEnum(command);
    }

    @Override
    public CommandMessage deserialize(Deserializer deserializer) throws SerializationException {
        return new CommandMessage(deserializer.deserializeLong(), deserializer.deserializeEnum(Command.values()));
    }
}
