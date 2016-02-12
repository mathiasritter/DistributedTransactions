package at.mritter.dezsys06.net;

import java.nio.charset.StandardCharsets;

/**
 * This class represents a message that is used for the communication between client and service
 *
 * @author Mathias Ritter
 * @version 1.0
 */
public class Message {

    private byte[] content;
    private MessageType type;

    /**
     * create a new message with the given content (byte array) and type
     *
     * @param content message content
     * @param type type of the message
     */
    public Message(byte[] content, MessageType type) {
        this.content = content;
        this.type = type;
    }

    /**
     * create a new message with the given content (String) and type
     *
     * @param content message content
     * @param type type of the message
     */
    public Message(String content, MessageType type) {
        this(content.getBytes(StandardCharsets.UTF_8), type);
    }

    public byte[] getContent() {
        return content;
    }

    public MessageType getType() {
        return type;
    }

    public int getLength() {
        return content.length;
    }
}
