package at.mritter.dezsys06.net;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a network server.
 * The communication is ensued over sockets.
 *
 * @author Mathias Ritter
 * @version 1.0
 */
public class SocketServer extends SocketBase {

    public static final Logger LOG = LogManager.getLogger(SocketServer.class);

    private ServerSocket serverSocket;
    private volatile boolean running = true;

    /**
     * Creates a new server socket on the given port.
     *
     * @param port Port of the server socket that is created.
     */
    public SocketServer(int port) {
        super();
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * @see SocketBase#connect()
     */
    @Override
    public void connect() {

        new Thread(() -> {

            while(running) {

                try {
                    // accept new client connection, get streams to read/write
                    Socket clientSocket = serverSocket.accept();

                    // set io streams
                    SocketReaderWriter socketReaderWriter = new SocketReaderWriter(
                            this,
                            new DataInputStream(clientSocket.getInputStream()),
                            new DataOutputStream(clientSocket.getOutputStream())
                    );

                    super.addSocketWriter(socketReaderWriter);

                    // start listening for incoming messages
                    new Thread(socketReaderWriter).start();
                } catch (Exception e) {
                    LOG.error(e.getMessage());
                    System.exit(-1);
                }
            }

        }).start();

    }

    /**
     * @see SocketBase#disconnect()
     */
    @Override
    public void disconnect() {
        this.running = false;
        super.disconnect();
        try {
            serverSocket.close();
        } catch (IOException e) {
            LOG.error(e.getMessage());
            System.exit(-1);
        }
    }

}
