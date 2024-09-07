package crypto.chat;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageTransmitter extends Thread {

    String message, hostname;
    int port;

    public MessageTransmitter() {
        this(null, null, 666);
    }

    public MessageTransmitter(int port) {
        this(null, null, port);
    }

    public MessageTransmitter(String hostname, int port) {
        this(null, hostname, port);
    }

    public MessageTransmitter(String message, String hostname, int port) {
        this.message = message;
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            try (Socket s = new Socket(hostname, port)) {
                s.getOutputStream().write(message.getBytes());
            }
        } catch (IOException ex) {
            Logger.getLogger(MessageTransmitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
