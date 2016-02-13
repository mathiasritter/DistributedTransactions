package at.mritter.dezsys06;

import at.mritter.dezsys06.db.DBConnectionCreator;
import at.mritter.dezsys06.db.MySQLConnectionCreator;
import at.mritter.dezsys06.db.SQLExecutor;
import at.mritter.dezsys06.net.MessageCallback;
import at.mritter.dezsys06.net.MessageHandler;
import at.mritter.dezsys06.net.SocketBase;
import at.mritter.dezsys06.net.SocketClient;

public class StartStation {

    public static void main(String[] args) {
        SocketBase socket = new SocketClient("127.0.0.1", 35786);

        DBConnectionCreator connectionCreator = new MySQLConnectionCreator()
                .setDatabase("dezsys06")
                .setUser("dezsys06")
                .setPassword("dezsys06");

        MessageCallback messageCallback = new MessageHandler(socket, new SQLExecutor(connectionCreator.getConnection()));
        socket.addMessageCallback(messageCallback);
        socket.connect();
    }

}
