package at.mritter.dezsys06.net;

/**
 * This interface contains of a callback method that is called when
 * a new message is received.
 *
 * @author Mathias Ritter
 * @version 1.0
 */
public interface MessageCallback {

    /**
     * This method is called by the socket server or socket client when a new message is received.
     *
     * @param message Message that has been received.
     */
    public void handleMessage(Message message);

}
