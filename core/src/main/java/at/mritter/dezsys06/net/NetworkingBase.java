package at.mritter.dezsys06.net;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This class represents a network resource that is able to read (receive) and write (send) messages
 * over the network.
 *
 * @author Mathias Ritter
 * @version 1.0
 */
public abstract class NetworkingBase implements Runnable {

    public static final Logger LOG = LogManager.getLogger(NetworkingBase.class);

    private DataInputStream in;
    private DataOutputStream out;

    private volatile boolean running = true;

    private MessageCallback messageCallback;

    /**
     * Sets the messageCallback.
     * The messageCallback is the service or the client that is able to handle incoming messages.
     *
     * @param messageCallback service or client
     */
    public NetworkingBase(MessageCallback messageCallback) {
        this.messageCallback = messageCallback;
    }


    /**
     * Connects to another network resource or waits for incoming connections. <br>
     * After the connection has been established, the thread is started. This means
     * that the network resource waits for incoming messages.
     */
    public abstract void connect();

    /**
     * Sends a new message to the connected network resource.
     *
     * @param message The message that should be sent
     */
    public void write(Message message) {
        try {
            // write type of message, length and content
            out.writeChar(message.getType().getValue());
            out.writeInt(message.getLength());
            out.write(message.getContent());
        } catch (IOException e) {
            LOG.error(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * Disconnects from the connected network resource.
     * Closes io streams.
     */
    public void disconnect() {
        // set running to false so the thread stops
        this.running = false;
        try {
            // close io streams
            this.in.close();
            this.out.close();
        } catch (IOException e) {
            LOG.error(e.getMessage());
            System.exit(-1);
        }
    }


    /**
     * Receives new messages while thread is running
     */
    @Override
    public void run() {

        if (in == null || out == null)
            throw new IllegalStateException("Unable to listen for incoming messages: Connection has not been established yet.");

        // while running is true listen for new messages
        while (running){
            try {

                // read message type (char), message length (int) and then the content of the message
                MessageType messageType = MessageType.valueOf(in.readChar());
                int length = in.readInt();
                byte[] messageContent = new byte[length];
                in.readFully(messageContent, 0, messageContent.length);

                // create new message object, call the handler of the messageCallback
                Message message = new Message(messageContent, messageType);
                messageCallback.handleMessage(message);

            } catch (Exception e) {
                if (!running)
                    System.exit(0);
                else {
                    LOG.error(e.getMessage());
                    System.exit(-1);
                }
            }
        }
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }
}
