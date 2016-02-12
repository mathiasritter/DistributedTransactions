package at.mritter.dezsys06.io;

import at.mritter.dezsys06.io.InputProcessor;
import at.mritter.dezsys06.net.Message;
import at.mritter.dezsys06.net.MessageType;
import at.mritter.dezsys06.net.SocketBase;

public class InputHandler implements InputProcessor {

    private SocketBase socket;

    public InputHandler(SocketBase socket) {
        this.socket = socket;
    }

    @Override
    public void processInput(String input) {
        socket.write(new Message(input, MessageType.NONE));
    }

    @Override
    public void exitApplication() {
        System.exit(0);
    }
}
