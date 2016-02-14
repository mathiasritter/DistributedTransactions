package at.mritter.dezsys06.net;

import at.mritter.dezsys06.io.InputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Handles both user input and incoming messages (from sockets)
 *
 * @author Mathias Ritter
 * @version 1.0
 */
public class MessageHandler implements MessageCallback, InputProcessor {


    public static final Logger LOG = LogManager.getLogger(MessageHandler.class);
    private SocketBase socket;

    private int successResponses;
    private int failResponses;
    private int requiredResponses;

    private boolean preparePhase;
    private boolean inProgress;

    private long timeout;
    private int maxTime;

    public MessageHandler(SocketBase socket) {
        this.socket = socket;
        this.preparePhase = true;
        this.inProgress = false;
        this.maxTime = 1000;
        this.checkForTimeout();
    }

    /**
     * @see MessageCallback#handleMessage(Message)
     */
    @Override
    public void handleMessage(Message message) {
        LOG.info("Received " + message.getType());

        switch (message.getType()) {
            case RESULT:
                if (message.getContentAsString().equalsIgnoreCase("YES")) {
                    LOG.info("Result is YES");
                    this.successResponses++;
                } else if (message.getContentAsString().equalsIgnoreCase("NO")) {
                    LOG.info("Result is NO");
                    this.failResponses++;
                } else if (message.getContentAsString().equalsIgnoreCase("ACK")) {
                    LOG.info("Result is ACK");
                    this.successResponses++;
                } else if (message.getContentAsString().equalsIgnoreCase("NCK")) {
                    LOG.info("Result is NCK");
                    this.failResponses++;
                }

                if (this.requiredResponses == this.successResponses + this.failResponses) {
                    this.checkResponses();
                }
                break;
        }

    }

    /**
     * Checks if timeout occurs
     */
    private void checkForTimeout() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                    if (this.inProgress && System.currentTimeMillis() >= this.timeout) {
                        this.checkResponses();
                    }
                } catch (InterruptedException e) {
                    LOG.error(e.getMessage());
                    break;
                }
            }
        }).start();
    }

    /**
     * Check all received responses
     */
    private void checkResponses() {
        int timeoutResponses = this.requiredResponses - this.successResponses - this.failResponses;
        if (this.preparePhase) {
            this.preparePhase = false;
            LOG.info("YES: " + this.successResponses + ", NO: " + this.failResponses + ", TIMEOUT: " + timeoutResponses);
            this.timeout = System.currentTimeMillis() + maxTime;
            if (this.failResponses > 0 || timeoutResponses > 0) {
                this.socket.write(new Message("", MessageType.DO_ABORT));
            } else {
                this.socket.write(new Message("", MessageType.DO_COMMIT));
            }
        } else {
            this.inProgress = false;
            LOG.info("ACK: " + this.successResponses + ", NCK: " + this.failResponses + ", TIMEOUT: " + timeoutResponses);
        }
        this.successResponses = 0;
        this.failResponses = 0;
    }

    /**
     * @see InputProcessor#processInput(String)
     */
    @Override
    public void processInput(String input) {

        if (!this.inProgress) {
            this.inProgress = true;
            this.preparePhase = true;
            this.requiredResponses = socket.getSocketWriterCount();
            this.timeout = System.currentTimeMillis() + maxTime;
            this.socket.write(new Message(input, MessageType.SQL_QUERY));
        }

    }

    /**
     * @see InputProcessor#exitApplication()
     */
    @Override
    public void exitApplication() {
        System.exit(0);
    }
}
