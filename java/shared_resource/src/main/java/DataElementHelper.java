package src.main.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

public final class DataElementHelper {

    private static final Logger logger;

    private DataElementHelper() {}

    static {
        logger = MyLogHelper.getLogger(DataElementHelper.class.getName());
    }

    public static IDataElement fromByteArray(byte[] serializedData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(serializedData);
        ObjectInput in = null;
        Object object = null;
        try {
            in = new ObjectInputStream(bais);
            object = in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            logger.warning(String.format("Ignore close exception: %s", ex));
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                logger.warning(String.format("Ignore close exception: %s", ex));
            }
        }
        return (IDataElement) object;
    }

    public static byte[] toByteArray(IDataElement dataElement) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutput objOutput = null;
        byte[] objBytes = null;

        try {
            objOutput = new ObjectOutputStream(baos);
            objOutput.writeObject(dataElement);
            objOutput.flush();
            objBytes = baos.toByteArray();

        } catch (IOException objOutIoEx) {
            logger.warning(String.format("Ignore close exception: %s", objOutIoEx));
        } finally {
            try {
                baos.close();
            } catch (IOException baosIoEx) {
                logger.warning(String.format("Ignore close exception: %s", baosIoEx));
            }
        }
        return objBytes;
    }
}
