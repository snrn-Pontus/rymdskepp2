package se.snrn.rymdskepp;

import com.badlogic.gdx.backends.iosmoe.IOSApplication;
import com.badlogic.gdx.backends.iosmoe.IOSApplicationConfiguration;
import org.moe.natj.general.Pointer;
import com.github.czyzby.websocket.CommonWebSockets;
import se.snrn.rymdskepp.Rymdskepp;

import apple.uikit.c.UIKit;

public class IOSMoeLauncher extends IOSApplication.Delegate {

    protected IOSMoeLauncher(Pointer peer) {
        super(peer);
    }

    @Override
    protected IOSApplication createApplication() {
        // Initiating web sockets module:
        CommonWebSockets.initiate();
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.useAccelerometer = false;
        return new IOSApplication(new Rymdskepp("localhost"), config);
    }

    public static void main(String[] argv) {
        UIKit.UIApplicationMain(0, null, null, IOSMoeLauncher.class.getName());
    }
}