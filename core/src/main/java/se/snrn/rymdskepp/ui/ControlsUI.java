package se.snrn.rymdskepp.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import se.snrn.rymdskepp.Command;
import se.snrn.rymdskepp.WebSocketClient;

public class ControlsUI extends Table {
    private WebSocketClient webSocketClient;

    public ControlsUI(Skin skin, WebSocketClient webSocketClient) {
        super(skin);
        this.webSocketClient = webSocketClient;

        TextButton left = new TextButton("<", skin);

        left.addListener(new ClickListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                webSocketClient.sendCommand(Command.LEFT_DOWN);
                return super.touchDown(event, x, y, pointer, button);

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                webSocketClient.sendCommand(Command.LEFT_UP);
                super.touchUp(event, x, y, pointer, button);
            }
        });

        TextButton right = new TextButton(">", skin);
        right.addListener(new ClickListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                webSocketClient.sendCommand(Command.RIGHT_DOWN);
                return super.touchDown(event, x, y, pointer, button);

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                webSocketClient.sendCommand(Command.RIGHT_UP);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        TextButton accelerate = new TextButton("^", skin);
        accelerate.addListener(new ClickListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                webSocketClient.sendCommand(Command.ACCELERATE_DOWN);
                return super.touchDown(event, x, y, pointer, button);

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                webSocketClient.sendCommand(Command.ACCELERATE_UP);
                super.touchUp(event, x, y, pointer, button);
            }
        });

        TextButton shoot = new TextButton("Shoot", skin);
        shoot.addListener(new ClickListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                webSocketClient.sendCommand(Command.SHOOT);
                return super.touchDown(event, x, y, pointer, button);

            }

        });

        setFillParent(true);

        align(Align.bottom);
        add(left);
        add(accelerate);
        add(right);
        add(shoot).align(Align.right);
    }
}
