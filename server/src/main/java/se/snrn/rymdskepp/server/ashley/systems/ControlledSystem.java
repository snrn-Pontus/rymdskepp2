package se.snrn.rymdskepp.server.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import se.snrn.rymdskepp.CommandMessage;
import se.snrn.rymdskepp.server.ashley.components.ControlledComponent;
import se.snrn.rymdskepp.server.ashley.components.MovementComponent;

import java.util.HashMap;
import java.util.HashSet;

public class ControlledSystem extends EntitySystem {

    private final HashMap<Long, MovementComponent> playerHash;
    private final HashMap<Long, Entity> playerMap;
    private ImmutableArray<Entity> entities;
    private HashSet<se.snrn.rymdskepp.server.Player> players;


    public HashMap<Long, MovementComponent> getPlayerHash() {
        return playerHash;
    }

    public ControlledSystem(HashSet<se.snrn.rymdskepp.server.Player> players) {
        this.players = players;
        playerHash = new HashMap<Long,MovementComponent>();
        playerMap = new HashMap<Long,Entity>();

    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ControlledComponent.class, MovementComponent.class).get());

    }

    public void setYVelocity(float v,long id) {

        playerHash.get(id).accel.y = v;
    }

    public void setXVelocity(float v,long id) {
        playerHash.get(id).velocity.x = v;
    }

    public void setTurning(float angle,long id) {

        playerHash.get(id).rotation = angle;
    }

    public void shoot(Entity entity) {
        getEngine().getSystem(WeaponSystem.class).shoot(entity);
    }

    public void handleCommand(CommandMessage commandMessage) {
        long id = commandMessage.getId();
        switch (commandMessage.getCommand()) {
            case LEFT_DOWN:
                setTurning(0.005f, id);
                break;
            case LEFT_UP:
                setTurning(0,id);
                break;
            case RIGHT_DOWN:
                setTurning(-0.005f,id);
                break;
            case RIGHT_UP:
                setTurning(0,id);
                break;
            case ACCELERATE_DOWN:
                setYVelocity(0.025f,id);
                break;
            case ACCELERATE_UP:
                setYVelocity(0,id);
                break;
            case SHOOT:
                System.out.println("PEW PEW");
                shoot(playerMap.get(id));
                break;
        }
    }

    public HashMap<Long,Entity> getPlayerMap() {
        return playerMap;
    }
}
