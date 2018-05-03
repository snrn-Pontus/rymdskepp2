package se.snrn.rymdskepp.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import se.snrn.rymdskepp.Assets;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.Rymdskepp;
import se.snrn.rymdskepp.components.PlayerComponent;

import java.util.HashMap;
import java.util.Map;

public class PlayerStatusUI extends Table {
    private Skin skin;
    private final HashMap<Long, Entity> players;
    NinePatch ninePatch;
    NinePatchDrawable ninePatchDrawable;

    public PlayerStatusUI(Skin skin, HashMap<Long, Entity> players) {
        super(skin);
        this.skin = skin;
        this.players = players;

        setFillParent(true);

        ninePatch = new NinePatch((Texture) Rymdskepp.manager.get(Assets.window), 8, 8, 8, 8);

        ninePatchDrawable = new NinePatchDrawable(ninePatch);






//        add(playerTable2).expand().align(Align.topRight).fill().width(Value.percentWidth(0.25f)).height(Value.percentHeight(0.25f));
//        row();
//        add(playerTable3).expand().align(Align.bottomLeft).fill().width(Value.percentWidth(0.25f)).height(Value.percentHeight(0.25f));
//        add(playerTable4).expand().align(Align.bottomRight).fill().width(Value.percentWidth(0.25f)).height(Value.percentHeight(0.25f));


    }
    private void addPlayerTable(PlayerComponent playerComponent){
        PlayerTable playerTable = new PlayerTable(skin,ninePatchDrawable,playerComponent, Align.topLeft);
        add(playerTable).width(Value.percentWidth(0.25f)).height(Value.percentHeight(0.25f)).expand().fill().uniform();
        add().expand().fill().uniform().colspan(5);

    }

    public void updatePlayers() {
        clearChildren();

        for (Map.Entry<Long, Entity> longEntityEntry : players.entrySet()) {
            Long key = longEntityEntry.getKey();
            Entity player = longEntityEntry.getValue();
            PlayerComponent playerComponent = Mappers.playerMapper.get(player);
            addPlayerTable(playerComponent);
            row();
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
