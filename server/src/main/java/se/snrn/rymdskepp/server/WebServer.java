package se.snrn.rymdskepp.server;

import io.vertx.core.Vertx;

public class WebServer {

    public WebServer() {


    }

    public void launch() {

        Vertx vertx = Vertx.vertx();

        vertx.createHttpServer().requestHandler(request -> request.response().end(
                "Players: " + GameState.getInstance().getPlayers().size())).listen(8080);
    }
}
