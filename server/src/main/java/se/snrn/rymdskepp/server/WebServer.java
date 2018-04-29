package se.snrn.rymdskepp.server;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import io.vertx.core.Vertx;

import java.io.StringWriter;

public class WebServer {

    private Json json;

    public WebServer() {
        json = new Json();

        json.setWriter(new JsonWriter(new StringWriter()));

    }

    public void launch() {

        Vertx vertx = Vertx.vertx();

        vertx.createHttpServer().requestHandler(
                request -> request.response()
                        .end(getResponse(request.path()))
        ).listen(8080);

    }

    private String getAllPlayers() {
        return new JsonValue("{\"players\":" + GameState.getInstance().getPlayers().size() + "}").toString();
    }

    private String getResponse(String path) {
        switch (path) {
            case "/players": {
                return getAllPlayers();
            }
        }
        return path;
    }
}
