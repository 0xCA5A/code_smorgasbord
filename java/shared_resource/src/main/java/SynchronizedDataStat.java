package src.main.java;

import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class SynchronizedDataStat {

    private static long statObjCnt = 0;
    private long timeDiffMs;
    private SynchronizedData dataStore;
    private Logger logger;

    SynchronizedDataStat(Date startTimestamp, SynchronizedData dataStore) {
        this.dataStore = dataStore;
        logger = MyLogHelper.getLogger(this.toString());

        incStatObjCnt();
        timeDiffMs = getDateDiff(startTimestamp, new Date(), TimeUnit.MILLISECONDS);
    }

    public static void incStatObjCnt() {
        statObjCnt++;
    }

    public void show() {
        String header = String.format("STATUS REPORT #%d (%s) ", statObjCnt, this);
        TreeMap<String, String> hashMap = new TreeMap<String, String>();
        hashMap.put("total data process time", String.format("%dms", timeDiffMs));
        hashMap.put("total data store access count", String.format("%d", dataStore.getAccessCnt()));
        hashMap.put(
                "data elements in data store",
                String.format("%d", dataStore.getNrOfDataElements()));

        String readCntValue =
                String.format(
                        "%d (%.2f%%)",
                        dataStore.getReadAccessCnt(), dataStore.getReadAccessPercent());
        hashMap.put("data store read access count", readCntValue);
        String readMissValue =
                String.format(
                        "%d (%.2f%%)", dataStore.getReadMissCnt(), dataStore.getReadMissPercent());
        hashMap.put("data store read miss count", readMissValue);
        String writeCntValue =
                String.format(
                        "%d (%.2f%%)",
                        dataStore.getWriteAccessCnt(), dataStore.getWriteAccessPercent());
        hashMap.put("data store write access count", writeCntValue);

        float readsPerSecond = (float) dataStore.getReadAccessCnt() / (timeDiffMs / 1000);
        hashMap.put("data store reads per second", String.format("%.2f", readsPerSecond));
        float writesPerSecond = (float) dataStore.getWriteAccessCnt() / (timeDiffMs / 1000);
        hashMap.put("data store writes per second", String.format("%.2f", writesPerSecond));

        new InfoBanner(logger, header, hashMap);
    }

    private long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
