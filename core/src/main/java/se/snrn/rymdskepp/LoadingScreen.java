package se.snrn.rymdskepp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import sun.reflect.generics.tree.BaseType;


public class LoadingScreen extends ScreenAdapter {

    private FitViewport viewport;
    private InputMultiplexer inputMultiplexer;
    private Stage stage;
    private Camera camera;
    public static int WIDTH = Gdx.graphics.getWidth();
    public static int HEIGHT = Gdx.graphics.getHeight();
    private ProgressBar progressBar;
    private Batch batch;

    public LoadingScreen(Batch batch) {
        super();
        this.batch = batch;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        viewport.apply();
        stage = new Stage(viewport, batch);


        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        stage.setDebugAll(true);

        Label label = new Label("RYMDSPEL",(Skin) Rymdskepp.manager.get(Assets.skin));
        table.add(label);

        table.row();
        progressBar = new ProgressBar(
                0, 1, 0.001f, false,
                Rymdskepp.manager.<Skin>get(Assets.skin)
        );

        table.add(progressBar);
        inputMultiplexer = new InputMultiplexer();


        inputMultiplexer.addProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        camera.update();
        System.out.println(Rymdskepp.manager.getProgress());
        progressBar.setValue(Rymdskepp.manager.getProgress());
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.setScreenSize(width, height);
    }

    @Override
    public void show() {
        super.show();
        camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
        camera.update();
        viewport.apply();
        Gdx.input.setInputProcessor(inputMultiplexer);
    }
}
