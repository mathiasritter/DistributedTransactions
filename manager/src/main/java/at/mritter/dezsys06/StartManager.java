package at.mritter.dezsys06;

import at.mritter.dezsys06.io.ConsoleReader;
import at.mritter.dezsys06.io.Reader;
import at.mritter.dezsys06.net.MessageCallback;
import at.mritter.dezsys06.net.MessageHandler;
import at.mritter.dezsys06.net.SocketBase;
import at.mritter.dezsys06.net.SocketServer;

public class StartManager {

    public static void main(String[] args) {
        SocketBase socket = new SocketServer(35786);
        MessageHandler messageHandler = new MessageHandler(socket);
        socket.addMessageCallback(messageHandler);
        Reader reader = new ConsoleReader(messageHandler);
        reader.read();
        socket.connect();

    }

}
