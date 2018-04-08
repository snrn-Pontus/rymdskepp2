package se.snrn.rymdskepp.components;

import com.badlogic.ashley.core.Component;
import se.snrn.rymdskepp.WebsocketManager;

public class NetworkedComponent implements Component {
    public WebsocketManager websocketManager;
    public long id;
}
