package se.snrn.rymdskepp.server.ships;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.headless.HeadlessFileHandle;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

public class JsonShipFactory {
    private ArrayList<Ship> ships;

    public JsonShipFactory() {
        ships = new ArrayList<>();
        JsonReader jsonReader = new JsonReader();


        FileHandle fileHandle = new HeadlessFileHandle("ships.json",Files.FileType.Internal);
        JsonValue shipsJson = jsonReader.parse(fileHandle);
        for (JsonValue ship : shipsJson) {
            ships.add(new Ship(ship));
        }
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }
}
