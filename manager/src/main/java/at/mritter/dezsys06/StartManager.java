package at.mritter.dezsys06;

import at.mritter.dezsys06.io.ConsoleReader;
import at.mritter.dezsys06.io.Reader;
import at.mritter.dezsys06.net.MessageHandler;
import at.mritter.dezsys06.net.SocketBase;
import at.mritter.dezsys06.net.SocketServer;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

public class StartManager {

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

        SocketBase socket = new SocketServer(port);
        MessageHandler messageHandler = new MessageHandler(socket);
        socket.addMessageCallback(messageHandler);
        Reader reader = new ConsoleReader(messageHandler);
        reader.read();
        socket.connect();

    }

}
