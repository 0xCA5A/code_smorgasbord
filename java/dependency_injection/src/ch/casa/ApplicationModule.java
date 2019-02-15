package ch.casa;

import com.google.inject.AbstractModule;

import java.time.Instant;
import java.util.logging.Logger;

public class ApplicationModule extends AbstractModule {
    private static final Logger logger = Logger.getLogger(ApplicationModule.class.getName());

    @Override
    protected void configure() {
        logger.info("Configure " + this.getClass().getName());

        long now = Instant.now().toEpochMilli();
        if (now % 2 == 0) {
            bind(Communicator.class).to(EmailCommunicatorImpl.class);
        } else {
            bind(Communicator.class).to(SmsCommunicatorImpl.class);
        }
    }
}