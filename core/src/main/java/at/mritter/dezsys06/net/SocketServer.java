package at.mritter.dezsys06.net;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class represents a network server.
 * The communication is ensued over sockets.
 *
 * @author Mathias Ritter
 * @version 1.0
 */
public class SocketServer extends NetworkingBase {

    public static final Logger LOG = LogManager.getLogger(SocketServer.class);

    private ServerSocket serverSocket;
    private Socket clientSocket;

    /**
     * Creates a new server socket on the given port.
     *
     * @param port Port of the server socket that is created.
     */
    public SocketServer(MessageCallback messageCallback, int port) {
        super(messageCallback);
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * @see NetworkingBase#connect()
     */
    @Override
    public void connect() {
        try {
            // accept new client connection, get streams to read/write
            clientSocket = serverSocket.accept();

            // set io streams
            super.setIn(new DataInputStream(clientSocket.getInputStream()));
            super.setOut(new DataOutputStream(clientSocket.getOutputStream()));

            // start listening for incoming messages
            new Thread(this).start();
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
            // close client and server socket
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            LOG.error(e.getMessage());
            System.exit(-1);
        }
    }


}
