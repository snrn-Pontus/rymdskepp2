package se.snrn.rymdskepp.server;

import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.HeadlessConsole;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Console implements Runnable {

    private final GameState gameState;
    private CommandExecutor commandExec;
    private HeadlessConsole console;
    private Scanner scanner;
    private BufferedReader br;


    private static Console ourInstance = new Console();

    public static Console getInstance() {
        return ourInstance;
    }


    private Console() {
        console = new HeadlessConsole();
        commandExec = new CommandExecutor() {
            public void players() {
                for (Player player : gameState.getPlayers()) {
                    console.log(String.valueOf(player));
                }
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


    @Override
    public void run() {
        while (scanner.hasNext()) {
            console.execCommand(scanner.nextLine());
        }
    }

}

