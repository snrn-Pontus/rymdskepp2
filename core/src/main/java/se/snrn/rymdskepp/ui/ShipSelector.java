package se.snrn.rymdskepp.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import se.snrn.rymdskepp.ships.JsonShipFactory;
import se.snrn.rymdskepp.ships.Ship;

import java.util.ArrayList;

public class ShipSelector extends Table {
    private ArrayList<Ship> ships;
    private Ship selectedShip;
    private Image shipImage;
    private Label shipNameLabel;
    private Label shipCreatorLabel;
    private Image bulletImage;

    public ShipSelector(Skin skin, JsonShipFactory jsonShipFactory) {
        super(skin);

        ships = jsonShipFactory.getShips();


        TextButton left = new TextButton("<", skin);
        ClickListener leftListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectPreviousShip();
            }
        };
        left.addListener(leftListener);
        add(left);


        shipImage = new Image();
        bulletImage = new Image();

        shipNameLabel = new Label("", skin);
        shipCreatorLabel = new Label("", skin);
        selectedShip = ships.get(0);

        selectShip(selectedShip);


        Table table = new Table(skin);
        table.setBackground("select-box-pressed-c");
        table.add(shipImage);
        table.add(bulletImage).pad(8);

        add(table).padRight(10).padLeft(10);


        TextButton right = new TextButton(">", skin);
        ClickListener rightListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectNextShip();
            }
        };
        right.addListener(rightListener);
        add(right);
        row();
        add(shipNameLabel).colspan(3);
        row();
        add(shipCreatorLabel).colspan(3);

        row();

        SelectBox<Color> selectBox = new SelectBox<Color>(skin){
            @Override
            protected GlyphLayout drawItem(Batch batch, BitmapFont font, Color item, float x, float y, float width) {
                font.setColor(item);
                return super.drawItem(batch, font, item, x, y, width);
            }
        };


        selectBox.setItems(new Color(Color.BLUE),new Color(Color.RED),new Color(Color.GREEN),new Color(Color.YELLOW));

        add(selectBox);
    }

    private void selectNextShip() {
        int selectedShipPosition = ships.indexOf(selectedShip);
        int newShipPosition;
        if (selectedShipPosition == ships.size() - 1) {
            newShipPosition = 0;
        } else {
            newShipPosition = selectedShipPosition + 1;
        }
        selectedShip = ships.get(newShipPosition);
        selectShip(selectedShip);
    }

    private void selectPreviousShip() {
        int selectedShipPosition = ships.indexOf(selectedShip);
        int newShipPosition;
        if (selectedShipPosition == 0) {
            newShipPosition = ships.size() - 1;
        } else {
            newShipPosition = selectedShipPosition - 1;
        }
        selectedShip = ships.get(newShipPosition);
        selectShip(selectedShip);
    }

    private void selectShip(Ship selectedShip) {
        shipImage.setDrawable(new SpriteDrawable(new Sprite(new Texture("ships/" + selectedShip.getSprite()))));
        shipNameLabel.setText("Ship name: " + selectedShip.getName());
        shipCreatorLabel.setText("Ship creator: " + selectedShip.getCreator());
        bulletImage.setDrawable(new SpriteDrawable(new Sprite(new Texture("ships/" + selectedShip.getBullets()))));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public Ship getSelectedShip() {
        return ships.get(ships.indexOf(selectedShip));
    }
}
