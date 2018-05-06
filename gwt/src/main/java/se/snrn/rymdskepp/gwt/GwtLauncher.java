package se.snrn.rymdskepp.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.github.czyzby.websocket.GwtWebSockets;
import com.google.gwt.dom.client.Document;
import se.snrn.rymdskepp.Rymdskepp;

/**
 * Launches the GWT application.
 */
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
        String url = Document.get().getURL();
        url = url.replace("http://", "");
        if (url.charAt(url.length() - 1) == '/') {
            url = url.substring(0, url.length() - 1);
        }
        return new Rymdskepp(url);
    }
}