package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import se.snrn.rymdskepp.components.AnimationComponent;
import se.snrn.rymdskepp.components.StateComponent;
import se.snrn.rymdskepp.components.TextureComponent;


public class AnimationSystem extends IteratingSystem {

    ComponentMapper<TextureComponent> tm;
    ComponentMapper<AnimationComponent> am;
    ComponentMapper<StateComponent> sm;

    public AnimationSystem(){
        super(Family.all(TextureComponent.class,
                AnimationComponent.class,
                StateComponent.class).get());

        tm = ComponentMapper.getFor(TextureComponent.class);
        am = ComponentMapper.getFor(AnimationComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        AnimationComponent ani = am.get(entity);
        StateComponent state = sm.get(entity);


        if(ani.animations.containsKey(state.get())){
            TextureComponent tex = tm.get(entity);
            tex.region = (TextureAtlas.AtlasRegion) ani.animations.get(state.get()).getKeyFrame(state.time, state.isLooping);
        }else if(ani.shouldClearOnBlankState){
            TextureComponent tex = tm.get(entity);
            tex.setRegion(null);
        }

        if(!ani.isPaused) {
            state.time += deltaTime;
        }
    }
}