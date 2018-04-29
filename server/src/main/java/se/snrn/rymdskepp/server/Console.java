package se.snrn.rymdskepp.server;

import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.HeadlessConsole;
import se.snrn.rymdskepp.ServerMessage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Console implements Runnable {

    private final GameState gameState;
    private CommandExecutor commandExec;
    private HeadlessConsole console;
    private Scanner scanner;
    private BufferedReader br;
    private WebSocketServer webSocketServer;

    private static Console ourInstance = new Console();

    public static Console getInstance() {
        return ourInstance;
    }


    private Console() {
        webSocketServer = WebSocketServer.getInstance();
        console = new HeadlessConsole(){
            @Override
            public void log(String msg) {
                super.log(msg);
                ServerMessage serverMessage = new ServerMessage();
                serverMessage.setMessage(msg);
                if(webSocketServer != null) {
                    webSocketServer.sendToAllPlayers(serverMessage);
                }
            }
        };
        commandExec = new CommandExecutor() {
            public void players() {
                for (Player player : gameState.getPlayers()) {
                    console.log(String.valueOf(player));
                }
            }
            public void max_speed(float maxSpeed) {
                GameState.getInstance().setMaxSpeed(maxSpeed);
            }

            public void top(){
                for (Player player : gameState.getTopList()) {
                    console.log(player.getScore() +" - "+player.getName() );
                }
            }

            public void clear() {
                console.clear();
            }
        };

        console.setCommandExecutor(commandExec);


        console.log("test");
        br = new BufferedReader(new InputStreamReader(System.in));
        scanner = new Scanner(br);
        gameState = GameState.getInstance();

    }

    public void log(String message) {
        console.log(message);
    }

    public void log(Object x) {
        String s = String.valueOf(x);
        synchronized (this) {
            console.log(s);
        }
    }

    public void executeCommand(String command){
        System.out.println(command);
        console.execCommand(command);
    }

    @Override
    public void run() {
        while (scanner.hasNext()) {
            console.execCommand(scanner.nextLine());
        }
    }

}

