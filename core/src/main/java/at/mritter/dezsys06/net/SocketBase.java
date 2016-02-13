package at.mritter.dezsys06.net;

import com.sun.javafx.UnmodifiableArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a network resource that is able to read (receive) and write (send) messages
 * over the network.
 *
 * @author Mathias Ritter
 * @version 1.0
 */
public abstract class SocketBase {

    public static final Logger LOG = LogManager.getLogger(SocketBase.class);

    private List<MessageCallback> callbacks;
    private List<SocketReaderWriter> writers;

    public SocketBase() {
        this.callbacks = new ArrayList<>();
        this.writers = new ArrayList<>();
    }

    /**
     * Connects to another network resource or waits for incoming connections. <br>
     * After the connection has been established, the thread is started. This means
     * that the network resource waits for incoming messages.
     */
    public abstract void connect();

    /**
     * Disconnects from the connected network resource.
     * Closes io streams.
     */
    public void disconnect() {
        for (SocketReaderWriter socket : this.writers) {
            socket.stop();
        }
    }

    public void write(Message message) {
        for (SocketReaderWriter writer : writers) {
            writer.write(message);
        }
    }

    public void addMessageCallback(MessageCallback callback) {
        this.callbacks.add(callback);
    }

    public List<MessageCallback> getMessageCallbacks() {
        return Collections.unmodifiableList(this.callbacks);
    }

    public void addSocketWriter(SocketReaderWriter writer) {
        this.writers.add(writer);
    }

    public void removeSocketWriter(SocketReaderWriter writer) {
        this.writers.remove(writer);
    }

    public int getSocketWriterCount() {
        return this.writers.size();
    }


}
