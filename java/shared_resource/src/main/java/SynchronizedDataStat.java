package src.main.java;

import java.util.concurrent.TimeUnit;
import java.util.Date;

public class SynchronizedDataStat extends MyLogger {

    private static int statObjCnt = 0;
    private long timeDiffMs;
    private SynchronizedData dataStore;

    SynchronizedDataStat(Date startTimestamp, SynchronizedData dataStore) {
        this.dataStore = dataStore;
        statObjCnt++;
        timeDiffMs = getDateDiff(startTimestamp, new Date(), TimeUnit.MILLISECONDS);
    }

    public void show() {
        LOGGER.info("====================================================================");
        LOGGER.info(String.format("=== STATUS REPORT #%d (%s) ", statObjCnt, this));
        LOGGER.info(String.format(" * data process time:\t\t\t%sms", timeDiffMs));
        LOGGER.info(String.format(" * total data store access count:\t%d", dataStore.getAccessCnt()));
        LOGGER.info(String.format(" * data store read access count:\t%d (%.2f%%)", dataStore.getReadAccessCnt(), dataStore.getReadAccessPercent()));
        LOGGER.info(String.format(" * data store write access count:\t%d (%.2f%%)", dataStore.getWriteAccessCnt(), dataStore.getWriteAccessPercent()));
        LOGGER.info(String.format(" * nr of data elements in data store:\t%d", dataStore.getNrOfDataElements()));
        LOGGER.info("====================================================================");
    }

    private long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}