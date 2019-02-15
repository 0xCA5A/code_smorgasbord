package ch.casa;

import com.google.inject.Inject;

import java.util.logging.Logger;


public class Communication {

    private static final Logger logger = Logger.getLogger(Communication.class.getName());

    private final Communicator communicator;

    private final boolean keepRecords;

    @Inject
    public Communication(Communicator communicator) {
        this(communicator, true);
    }

    public Communication(Communicator communicator, Boolean keepRecords) {
        this.communicator = communicator;
        this.keepRecords = keepRecords;

        if (keepRecords) {
            logger.info("Message logging enabled");
        }
    }

    public boolean sendMessage(String message) {
        return communicator.sendMessage(message);
    }

}
