package se.snrn.rymdskepp;

import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.Console;

class MyCommandExecutor extends CommandExecutor {

    private WebSocketClient webSocketClient;

    public MyCommandExecutor(WebSocketClient webSocketClient) {

        this.webSocketClient = webSocketClient;
    }

    
    public void status(){
        console.log("Status");
    }

    public void server(String command){
        webSocketClient.sendServerCommand(command);
    }

    @Override
    protected void setConsole(Console c) {
        super.setConsole(c);
    }
}