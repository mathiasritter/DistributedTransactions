package at.mritter.dezsys06.net;

import at.mritter.dezsys06.db.SQLExecutor;
import at.mritter.dezsys06.net.Message;
import at.mritter.dezsys06.net.MessageCallback;
import at.mritter.dezsys06.net.SocketBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MessageHandler implements MessageCallback {

    public static final Logger LOG = LogManager.getLogger(MessageHandler.class);

    private SocketBase socket;
    private SQLExecutor sqlExecutor;

    public MessageHandler(SocketBase socket, SQLExecutor sqlExecutor) {
        this.socket = socket;
        this.sqlExecutor = sqlExecutor;
    }

    @Override
    public void handleMessage(Message message) {

        LOG.info("Received " + message.getType());

        switch (message.getType()) {

            case SQL_QUERY:
                if (sqlExecutor.executeSQL(message.getContentAsString())) {
                    LOG.info("Successfully ecexuted statement.");
                    this.socket.write(new Message("YES", MessageType.RESULT));
                } else {
                    LOG.error("Failed executing statement.");
                    this.socket.write(new Message("NO", MessageType.RESULT));
                }
                break;

            case DO_COMMIT:
                if (sqlExecutor.endTransaction(true)) {
                    LOG.info("Successfully ended transaction with commit.");
                    this.socket.write(new Message("ACK", MessageType.RESULT));
                } else {
                    LOG.error("Failed ending transaction with commit.");
                    this.socket.write(new Message("NCK", MessageType.RESULT));
                }
                break;

            case DO_ABORT:
                if (sqlExecutor.endTransaction(false)) {
                    LOG.info("Successfully ended transaction with rollback.");
                    this.socket.write(new Message("ACK", MessageType.RESULT));
                } else {
                    LOG.error("Failed ending transaction with rollback.");
                    this.socket.write(new Message("NCK", MessageType.RESULT));
                }
                break;

        }

    }
}
