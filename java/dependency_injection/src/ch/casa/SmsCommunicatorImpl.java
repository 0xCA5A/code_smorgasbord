package ch.casa;

import java.util.logging.Logger;

public class SmsCommunicatorImpl implements Communicator {

    private static final Logger logger = Logger.getLogger(SmsCommunicatorImpl.class.getName());

    public SmsCommunicatorImpl() {
        logger.info("Construct " + this.getClass().getName() + " object");
    }

    @Override
    public boolean sendMessage(String message) {
        logger.info("Sending message using " + this.getClass().getName());
        return true;
    }
}
