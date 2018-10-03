package src.main.java;

import java.lang.reflect.Field;
import java.util.TreeMap;
import java.util.SortedMap;
import java.util.logging.Logger;

class InfoBanner {
    private static final String MAGIC_CHAR = "#";
    private TreeMap<String, String> hashMap;

    private TreeMap<String, String> getPublicFields(Logger logger, Object object) {

        TreeMap<String, String> hashMap = new TreeMap<String, String>();
        Class myClass = object.getClass();
        Field[] fields = myClass.getFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            String[] parts = fieldName.split("\\.");
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException ex) {
                value = 0;
                logger.warning("");
            }
            hashMap.put(
                    (parts.length != 0) ? parts[parts.length - 1] : fieldName, value.toString());
        }

        return hashMap;
    }

    InfoBanner(Logger logger, String header, TreeMap<String, String> hashMap) {
        printBanner(logger, header, hashMap);
    }

    InfoBanner(Logger logger, String header, Object object) {
        TreeMap<String, String> hashMap = getPublicFields(logger, object);
        printBanner(logger, header, hashMap);
    }

    InfoBanner(Logger logger, Object object) {
        String header = object.getClass().toString();
        TreeMap<String, String> hashMap = getPublicFields(logger, object);
        printBanner(logger, header, hashMap);
    }

    private void printBanner(Logger logger, String header, TreeMap<String, String> hashMap) {
        assert (logger != null);
        assert (hashMap != null);

        String separator = new String(new char[80]).replace("\0", MAGIC_CHAR);
        logger.info(separator);
        logger.info(String.format("# %s", header));
        logger.info(separator);

        for (SortedMap.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            logger.info(String.format("%s * %s:\t\t%s", MAGIC_CHAR, key, value));
        }

        logger.info(separator);
    }
}
