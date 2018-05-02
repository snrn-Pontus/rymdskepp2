package se.snrn.rymdskepp.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.czyzby.websocket.CommonWebSockets;
import se.snrn.rymdskepp.Rymdskepp;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Launches the desktop (LWJGL) application.
 */
public class DesktopLauncher {
    public static void main(final String[] args) {
        // Initiating web sockets module:
        CommonWebSockets.initiate();
        createApplication();
    }

    private static LwjglApplication createApplication() {
        String preFilledUrl = "";
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            preFilledUrl = localHost.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

//        return new LwjglApplication(new Test(), getDefaultConfiguration());
        return new LwjglApplication(new Rymdskepp(preFilledUrl), getDefaultConfiguration());
    }

    private static LwjglApplicationConfiguration getDefaultConfiguration() {
        final LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
        configuration.title = "Rymdskepp";
        configuration.width = Rymdskepp.WIDTH;
        configuration.height = Rymdskepp.HEIGHT;
        configuration.fullscreen = true;
//        configuration.samples = 4;
//        for (int size : new int[] { 128, 64, 32, 16 }) {
//            configuration.addIcon("libgdx" + size + ".png", FileType.Internal);
//        }
        return configuration;
    }
}