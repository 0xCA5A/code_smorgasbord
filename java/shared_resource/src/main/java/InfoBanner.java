package main.java;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;

class InfoBanner {
    private static final String MAGIC_CHAR = "#";

    private Map<String, String> getPublicFields(Logger clientLogger, Object object) {

        Map<String, String> hashMap = new TreeMap<>();
        Class myClass = object.getClass();
        Field[] fields = myClass.getFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            String[] parts = fieldName.split("\\.");
            Object value;
            try {
                value = field.get(object);
            } catch (IllegalAccessException ex) {
                value = 0;
                clientLogger.warning("");
            }
            hashMap.put(parts.length == 0 ? fieldName : parts[parts.length - 1], value.toString());
        }

        return hashMap;
    }

    InfoBanner(Logger logger, String header, Map<String, String> hashMap) {
        printBanner(logger, header, hashMap);
    }

    InfoBanner(Logger logger, String header, Object object) {
        Map<String, String> hashMap = getPublicFields(logger, object);
        printBanner(logger, header, hashMap);
    }

    InfoBanner(Logger logger, Object object) {
        String header = object.getClass().toString();
        Map<String, String> hashMap = getPublicFields(logger, object);
        printBanner(logger, header, hashMap);
    }

    private void printBanner(Logger clientLogger, String header, Map<String, String> hashMap) {
        assert clientLogger != null;
        assert hashMap != null;

        String separator = new String(new char[80]).replace("\0", MAGIC_CHAR);
        clientLogger.info(separator);
        clientLogger.info(String.format("# %s", header));
        clientLogger.info(separator);

        for (SortedMap.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            clientLogger.info(String.format("%s * %s:\t\t%s", MAGIC_CHAR, key, value));
        }

        clientLogger.info(separator);
    }
}
