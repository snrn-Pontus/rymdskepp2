package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import se.snrn.rymdskepp.systems.ControlledSystem;

public class MyInputProcessor implements InputProcessor {

    private ControlledSystem controlledSystem;
    private Entity entity;

    public MyInputProcessor(ControlledSystem controlledSystem, Entity entity) {

        this.controlledSystem = controlledSystem;
        this.entity = entity;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            controlledSystem.shoot(entity);
        }


        if (keycode == Input.Keys.W) {
            controlledSystem.setYVelocity(10.0f);
        }
        if (keycode == Input.Keys.S) {
            controlledSystem.setYVelocity(-10.0f);
        }
        if (keycode == Input.Keys.A) {
            controlledSystem.setTurning(0.10f);
        }
        if (keycode == Input.Keys.D) {
            controlledSystem.setTurning(-0.10f);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Input.Keys.W) {
            controlledSystem.setYVelocity(0.0f);
        }
        if (keycode == Input.Keys.S) {
            controlledSystem.setYVelocity(0.0f);
        }
        if (keycode == Input.Keys.A) {
            controlledSystem.setTurning(0);
        }
        if (keycode == Input.Keys.D) {
            controlledSystem.setTurning(0);
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
