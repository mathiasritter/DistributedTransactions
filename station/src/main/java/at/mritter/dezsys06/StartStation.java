package at.mritter.dezsys06;

import at.mritter.dezsys06.db.DBConnectionCreator;
import at.mritter.dezsys06.db.MySQLConnectionCreator;
import at.mritter.dezsys06.db.SQLExecutor;
import at.mritter.dezsys06.net.MessageCallback;
import at.mritter.dezsys06.net.MessageHandler;
import at.mritter.dezsys06.net.SocketBase;
import at.mritter.dezsys06.net.SocketClient;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

public class StartStation {

    public static void main(String[] args) {

        CommandLine line = null;
        try {
            line = CommandParser.parse(args);
        } catch (ParseException e) {
            CommandParser.printHelp();
            System.exit(-1);
        }

        int port = 35786;
        if (line.getOptionValue('P') != null)
            try {
                port = Integer.parseInt(line.getOptionValue('P'));
            } catch (NumberFormatException e) {
                CommandParser.printHelp();
                System.exit(-1);
            }

        String ip = "127.0.0.1";
        if (line.getOptionValue('h') != null)
            ip = line.getOptionValue('h');

        SocketBase socket = new SocketClient(ip, port);

        DBConnectionCreator connectionCreator = new MySQLConnectionCreator()
                .setDatabase(line.getOptionValue('d'))
                .setUser(line.getOptionValue('u'))
                .setPassword(line.getOptionValue('p'));

        MessageCallback messageCallback = new MessageHandler(socket, new SQLExecutor(connectionCreator.getConnection()));
        socket.addMessageCallback(messageCallback);
        socket.connect();
    }

}
