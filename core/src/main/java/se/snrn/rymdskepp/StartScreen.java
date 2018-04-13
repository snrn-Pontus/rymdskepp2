package se.snrn.rymdskepp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static se.snrn.rymdskepp.Shared.HEIGTH;
import static se.snrn.rymdskepp.Shared.WIDTH;

public class StartScreen extends ScreenAdapter {
    private Viewport viewport;
    private Stage stage;
    private Batch batch;
    private Camera camera;


    public StartScreen(Batch batch) {
        super();
        this.batch = batch;
        camera = new OrthographicCamera();
        viewport = new FillViewport(WIDTH,HEIGTH);
        viewport.setCamera(camera);
        this.stage = new Stage(viewport);

        BitmapFont bitmapFont = new BitmapFont();

        Table table = new Table();
        table.setFillParent(true);
        table.add(new Label("ok",new Label.LabelStyle(bitmapFont, Color.WHITE)));
        table.row();
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle(
                bitmapFont,
                Color.WHITE,
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ship.png")))),
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ship.png")))),
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ship.png"))))
        );
        TextField textField = new TextField("test",textFieldStyle);
        table.add(textField);
        Button button = new Button(
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ship.png")))),
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ship2.png")))),
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ship.png"))))
        );

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("Clicky!");
                System.out.println(textField.getText());
            }
        });
        table.add(button);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
    }
}
