package se.snrn.rymdskepp;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Rymdskepp extends Game {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private Batch batch;

    @Override
    public void create() {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        batch = new SpriteBatch();

        setScreen(new StartScreen(batch,this));
    }

    public void connect(String serverAddress,int serverPort){
        setScreen(new FirstScreen(batch,serverAddress,serverPort));
    }
}