package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import se.snrn.rymdskepp.systems.ControlledSystem;

public class MyInputProcessor implements InputProcessor {

    private ControlledSystem controlledSystem;
    private Entity entity;
    private WebSocketClient webSocketClient;

    public MyInputProcessor(ControlledSystem controlledSystem, Entity entity, WebSocketClient webSocketClient) {

        this.controlledSystem = controlledSystem;
        this.entity = entity;
        this.webSocketClient = webSocketClient;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            webSocketClient.sendCommand(Command.SHOOT);

        }

        if (keycode == Input.Keys.O) {
            webSocketClient.sendPongPacket(1);
        }

        if (keycode == Input.Keys.I) {
            webSocketClient.sendPingPacket(1);
        }

        if (keycode == Input.Keys.W) {
            webSocketClient.sendCommand(Command.ACCELERATE_DOWN);
        }

        if (keycode == Input.Keys.A) {
//            controlledSystem.setTurning(0.10f);
            webSocketClient.sendCommand(Command.LEFT_DOWN);
        }
        if (keycode == Input.Keys.D) {
//            controlledSystem.setTurning(-0.10f);
            webSocketClient.sendCommand(Command.RIGHT_DOWN);

        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Input.Keys.W) {
            webSocketClient.sendCommand(Command.ACCELERATE_UP);

        }

        if (keycode == Input.Keys.A) {
            webSocketClient.sendCommand(Command.LEFT_UP);

        }
        if (keycode == Input.Keys.D) {
            webSocketClient.sendCommand(Command.RIGHT_UP);

        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
