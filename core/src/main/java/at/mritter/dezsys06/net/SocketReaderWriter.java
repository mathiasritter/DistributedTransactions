package at.mritter.dezsys06.net;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class SocketReaderWriter implements Runnable {

    private DataOutputStream out;
    private DataInputStream in;
    private SocketBase socket;

    private volatile boolean running = true;

    public static final Logger LOG = LogManager.getLogger(SocketReaderWriter.class);

    public SocketReaderWriter(SocketBase socket, DataInputStream in, DataOutputStream out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
    }

    /**
     * Receives new messages while thread is running
     */
    @Override
    public void run() {

        if (this.in == null)
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

                for(MessageCallback callback : this.socket.getMessageCallbacks()) {
                    callback.handleMessage(message);
                }

            } catch (Exception e) {
                if (!running)
                    System.exit(0);
                else {
                    this.running = false;
                    LOG.warn("Client disconnected");
                    this.socket.removeSocketWriter(this);
                }
            }
        }
    }

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

    public void stop() {
        this.running = false;
        try {
            this.in.close();
            this.out.close();
        } catch (IOException e) {
            LOG.error(e.getMessage());
            System.exit(-1);
        }
    }

}
