package at.mritter.dezsys06;

import at.mritter.dezsys06.io.ConsoleReader;
import at.mritter.dezsys06.io.InputHandler;
import at.mritter.dezsys06.io.Reader;
import at.mritter.dezsys06.net.MessageCallback;
import at.mritter.dezsys06.net.MessageHandler;
import at.mritter.dezsys06.net.SocketBase;
import at.mritter.dezsys06.net.SocketServer;

public class StartManager {

    public static void main(String[] args) {
        SocketBase socket = new SocketServer(35786);
        MessageCallback callback = new MessageHandler();
        socket.addMessageCallback(callback);
        Reader reader = new ConsoleReader(new InputHandler(socket));
        reader.read();
        socket.connect();

    }

}
