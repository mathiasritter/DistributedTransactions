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
public class SocketClient extends NetworkingBase {

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
    public SocketClient(MessageCallback messageCallback, String host, int port) {
        super(messageCallback);
        this.host = host;
        this.port = port;
    }

    /**
     * @see NetworkingBase#connect()
     */
    @Override
    public void connect() {
        try {
            // connect to server socket, get streams to read/write
            this.socket = new Socket(host, port);
            super.setIn(new DataInputStream(this.socket.getInputStream()));
            super.setOut(new DataOutputStream(this.socket.getOutputStream()));

            // start listening for incoming messages
            new Thread(this).start();

            // send initial message
            Message message = new Message("I am connected and ready to receive messages", MessageType.CLIENT_CONNECTED);
            super.write(message);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            System.exit(-1);
        }

    }

    /**
     * @see NetworkingBase#disconnect()
     */
    @Override
    public void disconnect() {
        super.disconnect();
        try {
            socket.close();
        } catch (IOException e) {
            LOG.error(e.getMessage());
            System.exit(-1);
        }
    }





}
