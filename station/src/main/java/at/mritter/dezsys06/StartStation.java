package at.mritter.dezsys06;

import at.mritter.dezsys06.net.MessageCallback;
import at.mritter.dezsys06.net.MessageHandler;
import at.mritter.dezsys06.net.SocketBase;
import at.mritter.dezsys06.net.SocketClient;

public class StartStation {

    public static void main(String[] args) {
        SocketBase socket = new SocketClient("127.0.0.1", 35786);
        MessageCallback messageCallback = new MessageHandler(socket);
        socket.addMessageCallback(messageCallback);
        socket.connect();
    }

}
