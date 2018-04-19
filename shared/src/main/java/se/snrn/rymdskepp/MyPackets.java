package se.snrn.rymdskepp;

import com.github.czyzby.websocket.serialization.impl.ManualSerializer;

/** Utility class. Allows to easily register packets in the same order on both client and server.
 *
 * @author MJ */
public class MyPackets {
    private MyPackets() {
    }

    public static void register(final ManualSerializer serializer) {
        // Note that the packets use simple, primitive data, but nothing stops you from using more complex types like
        // strings, arrays or even other transferables Both Serializer and Deserializer APIs are well documented: make
        // sure to check them out.
        serializer.register(new Coordinates());
        serializer.register(new NetworkObject());
        serializer.register(new NewPlayerConnected());
        serializer.register(new CommandMessage());
        serializer.register(new ServerCommand());
        serializer.register(new ServerWelcomeMessage());
        serializer.register(new ServerMessage());
    }
}