package se.snrn.rymdskepp;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Rymdskepp extends Game {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    @Override
    public void create() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebsocketManager websocketManager = new WebsocketManager();
        Batch batch = new SpriteBatch();
//        setScreen(new FirstScreen(batch));
        setScreen(new FirstScreen(batch,websocketManager));
    }
}