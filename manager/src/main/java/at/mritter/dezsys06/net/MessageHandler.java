package at.mritter.dezsys06.net;

import at.mritter.dezsys06.net.Message;
import at.mritter.dezsys06.net.MessageCallback;
import at.mritter.dezsys06.net.SocketBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MessageHandler implements MessageCallback {


    public static final Logger LOG = LogManager.getLogger(MessageHandler.class);

    @Override
    public void handleMessage(Message message) {
        LOG.info("Recieved Message: " + message.getContentAsString());
    }
}
