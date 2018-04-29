package se.snrn.rymdskepp;

import com.github.czyzby.websocket.serialization.SerializationException;
import com.github.czyzby.websocket.serialization.Transferable;
import com.github.czyzby.websocket.serialization.impl.Deserializer;
import com.github.czyzby.websocket.serialization.impl.Serializer;

public class GameStatusMessage implements Transferable<GameStatusMessage> {


    private long[] ids;
    private int[] scores;

    public GameStatusMessage(long[] ids, int[] scores) {
        this.ids = ids;
        this.scores = scores;
    }

    public GameStatusMessage() {

    }

    @Override
    public void serialize(Serializer serializer) throws SerializationException {
        serializer.serializeLongArray(ids).serializeIntArray(scores);
    }

    @Override
    public GameStatusMessage deserialize(Deserializer deserializer) throws SerializationException {
        return new GameStatusMessage(deserializer.deserializeLongArray(),deserializer.deserializeIntArray());
    }

    public long[] getIds() {
        return ids;
    }

    public void setIds(long[] ids) {
        this.ids = ids;
    }

    public int[] getScores() {
        return scores;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }
}
