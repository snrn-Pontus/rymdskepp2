package se.snrn.rymdskepp.server;

import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.HeadlessConsole;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConsoleLogger implements Runnable {

    private CommandExecutor commandExec;
    private HeadlessConsole serverConsole;
    private Scanner scanner;
    private BufferedReader br;


    private static ConsoleLogger ourInstance = new ConsoleLogger();

    public static ConsoleLogger getInstance() {
        return ourInstance;
    }



    private ConsoleLogger() {
        serverConsole = new HeadlessConsole();
        commandExec = new CommandExecutor() {
            public void test(String str) {

                console.log(str);
            }
        };

        serverConsole.setCommandExecutor(commandExec);

        serverConsole.log("test");
        br = new BufferedReader(new InputStreamReader(System.in));
        scanner = new Scanner(br);
    }

    public void log(String message){
        serverConsole.log(message);
    }

    public void log(Object x) {
        String s = String.valueOf(x);
        synchronized (this) {
            serverConsole.log(s);
        }
    }


    @Override
    public void run() {
        if(scanner.hasNext()) {
            serverConsole.log(scanner.next());
        }
    }

}
