package se.snrn.rymdskepp.server.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import se.snrn.rymdskepp.CommandMessage;
import se.snrn.rymdskepp.State;
import se.snrn.rymdskepp.components.ControlledComponent;
import se.snrn.rymdskepp.server.Mappers;
import se.snrn.rymdskepp.server.Player;
import se.snrn.rymdskepp.server.components.MovementComponent;

import java.util.HashMap;
import java.util.HashSet;

public class ControlledSystem extends EntitySystem {

    private final HashMap<Long, MovementComponent> playerHash;
    private final HashMap<Long, Entity> playerMap;
    private ImmutableArray<Entity> entities;
    private HashSet<Player> players;


    public HashMap<Long, MovementComponent> getPlayerHash() {
        return playerHash;
    }

    public ControlledSystem(HashSet<Player> players) {
        this.players = players;
        playerHash = new HashMap<>();
        playerMap = new HashMap<>();

    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ControlledComponent.class, MovementComponent.class).get());

    }

    public void setYVelocity(float v, long id) {

        playerHash.get(id).acceleration.y = v;

    }

    public void setXVelocity(float v, long id) {
        playerHash.get(id).velocity.x = v;
    }

    public void setTurning(float angle, long id) {
        playerHash.get(id).rotation = angle;
    }

    public void shoot(Entity entity) {
        getEngine().getSystem(WeaponSystem.class).shoot(entity);
    }

    public void handleCommand(CommandMessage commandMessage) {
        long id = commandMessage.getId();
        switch (commandMessage.getCommand()) {
            case LEFT_DOWN:
                setTurning(3, id);
                break;
            case LEFT_UP:
                setTurning(0, id);
                break;
            case RIGHT_DOWN:
                setTurning(-3, id);
                break;
            case RIGHT_UP:
                setTurning(0, id);
                break;
            case ACCELERATE_DOWN:
                Mappers.stateMapper.get(playerMap.get(id)).set(State.ACCELERATING);
                setYVelocity(10.0f, id);
                break;
            case ACCELERATE_UP:
                Mappers.stateMapper.get(playerMap.get(id)).set(State.DEFAULT);
                setYVelocity(0, id);
                break;
            case SHOOT:
                Mappers.stateMapper.get(playerMap.get(id)).set(State.SHOOT);
                shoot(playerMap.get(id));
                break;
        }
    }

    public HashMap<Long, Entity> getPlayerMap() {
        return playerMap;
    }
}
