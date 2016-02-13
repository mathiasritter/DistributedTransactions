package at.mritter.dezsys06.net;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * This class represents a network client.
 * The communication is ensued over sockets.
 *
 * @author Mathias Ritter
 * @version 1.0
 */
public class SocketClient extends SocketBase {

    private Socket socket;

    private String host = "";
    private int port = 0;

    /**
     * Sets the host and the port of the server.
     * These settings are used when calling the connect method.
     *
     * @param host The host of the server
     * @param port The port of the server
     */
    public SocketClient(String host, int port) {
        super();
        this.host = host;
        this.port = port;
    }

    /**
     * @see SocketBase#connect()
     */
    @Override
    public void connect() {
        try {
            // connect to server socket, get streams to read/write
            Socket socket = new Socket(host, port);

            // set io streams
            SocketReaderWriter socketReaderWriter = new SocketReaderWriter(
                    this,
                    new DataInputStream(socket.getInputStream()),
                    new DataOutputStream(socket.getOutputStream())
            );

            super.addSocketWriter(socketReaderWriter);

            // start listening for incoming messages
            new Thread(socketReaderWriter).start();

            // send initial message
            Message message = new Message("New client successfully connected", MessageType.CLIENT_CONNECTED);
            socketReaderWriter.write(message);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            System.exit(-1);
        }

    }

}
