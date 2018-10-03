package src.main.java;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class SynchronizedDataStat {

    private static long statObjCnt = 0;
    private long timeDiffMs;
    private SynchronizedData dataStore;
    private Logger logger;

    SynchronizedDataStat(Date startTimestamp, SynchronizedData dataStore) {
        this.dataStore = dataStore;
        logger = MyLogManager.getLogger(this.toString());

        incStatObjCnt();
        timeDiffMs = getDateDiff(startTimestamp, new Date(), TimeUnit.MILLISECONDS);
    }

    public static void incStatObjCnt() {
        statObjCnt++;
    }

    public void show() {
        logger.info("====================================================================");
        logger.info(String.format("=== STATUS REPORT #%d (%s) ", statObjCnt, this));
        logger.info(String.format(" * total data process time:\t\t\t%sms", timeDiffMs));
        logger.info(
                String.format(" * total data store access count:\t\t%d", dataStore.getAccessCnt()));
        logger.info(
                String.format(
                        " * data store read access count:\t\t%d (%.2f%%)",
                        dataStore.getReadAccessCnt(), dataStore.getReadAccessPercent()));
        logger.info(
                String.format(
                        " * data store write access count:\t\t%d (%.2f%%)",
                        dataStore.getWriteAccessCnt(), dataStore.getWriteAccessPercent()));
        logger.info(
                String.format(
                        " * nr of data elements in data store:\t%d",
                        dataStore.getNrOfDataElements()));
        logger.info("====================================================================");
    }

    private long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
