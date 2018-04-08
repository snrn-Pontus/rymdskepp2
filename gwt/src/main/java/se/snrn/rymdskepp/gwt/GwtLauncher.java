package se.snrn.rymdskepp.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.github.czyzby.websocket.GwtWebSockets;
import se.snrn.rymdskepp.Rymdskepp;

/** Launches the GWT application. */
public class GwtLauncher extends GwtApplication {
    @Override
    public GwtApplicationConfiguration getConfig() {
        GwtApplicationConfiguration configuration = new GwtApplicationConfiguration(Rymdskepp.WIDTH, Rymdskepp.HEIGHT);
        return configuration;
    }

    @Override
    public ApplicationListener createApplicationListener() {
        // Initiating GWT web sockets module:
        GwtWebSockets.initiate();
        return new Rymdskepp();
    }
}